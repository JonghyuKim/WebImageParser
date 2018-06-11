package com.spacemonster.webdataviewer;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spacemonster.webdataviewer.content.data.WebImage;
import com.spacemonster.webdataviewer.content.provider.IDataProvider;
import com.spacemonster.webdataviewer.content.provider.InjectionDataProvider;
import com.spacemonster.webdataviewer.view.AbstractDataListAdapter;
import com.spacemonster.webdataviewer.view.webimage.WebImageListAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = "hyuhyu";

    private RecyclerView dataListView = null;
    private AbstractDataListAdapter dataListAdapter = null;
    private IDataProvider dataProvider = null;
    private boolean isDataLoadFinish = false;
    private ProgressBar progressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.pb_progressbar);

        dataListView = findViewById(R.id.rv_image_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        dataListView.setLayoutManager(layoutManager);
        dataListAdapter = new WebImageListAdapter();
        dataListView.setAdapter(dataListAdapter);
    }

    public void loadData(String path){

        if(progressBar.getVisibility() == View.VISIBLE) return;

        dataListAdapter.setDataList(dataProvider.getDatas());

        progressBar.setVisibility(View.VISIBLE);

        Log.d(LOG_TAG, "dataCreate Start!");

        dataProvider.createData(path, new IDataProvider.ParserProcessListner<WebImage>() {
            private int dataCount = 0;
            @Override
            public void addData(WebImage data) {
                //progress end
//                Log.d(LOG_TAG, "addData " + dataCount + " : " + data.getImageUrl());
                dataListAdapter.notifyItemInserted(dataCount++);
            }

            @Override
            public void onError() {
                Log.e(LOG_TAG, "DataLoadError!");

                dataListAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getBaseContext(), "Load Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(List list) {
                Log.d(LOG_TAG, "dataCreate finish!" + list.size() + " , " + dataCount);
                dataListAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getBaseContext(), "Load finish!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.load_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String dataPath = "";

        if(dataProvider != null){
            dataProvider.release();
            dataListAdapter.notifyDataSetChanged();
        }

        switch (item.getItemId()) {
            case R.id.menu_file:
                dataPath = WebImage.DATA_FILE_PATH;
                dataProvider = InjectionDataProvider.fileToSimpleTag(getBaseContext(), "<img src=\"");
                break;
            case R.id.menu_web:
                dataPath = WebImage.DATA_PAGE_URL;
                dataProvider = InjectionDataProvider.webToSimpleTag(getBaseContext(), "<img src=\"");
                break;
            case R.id.menu_jsoup:
                dataPath = WebImage.DATA_PAGE_URL;
                dataProvider = InjectionDataProvider.webToJsoup(getBaseContext());
                break;
        }

        loadData(dataPath);

        return true;
    }

    @Override
    protected void onDestroy() {
        if(dataProvider != null){
            dataProvider.release();
        }
        super.onDestroy();
    }
}
