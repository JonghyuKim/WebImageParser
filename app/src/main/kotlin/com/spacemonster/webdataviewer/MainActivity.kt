package com.spacemonster.webdataviewer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import com.spacemonster.webdataviewer.content.data.WebImage
import com.spacemonster.webdataviewer.content.provider.IDataProvider
import com.spacemonster.webdataviewer.content.provider.InjectionDataProvider
import com.spacemonster.webdataviewer.view.AbstractDataListAdapter
import com.spacemonster.webdataviewer.view.webimage.WebImageListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val dataListAdapter: AbstractDataListAdapter<WebImage> = WebImageListAdapter()
    private var dataProvider: IDataProvider<String, WebImage>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(applicationContext)

        rv_image_list.layoutManager = layoutManager
        rv_image_list.adapter = dataListAdapter
    }

    private fun loadData(path: String) {

        if (pb_progressbar.visibility == View.VISIBLE) return

        dataListAdapter.dataList = dataProvider?.parseDatas

        pb_progressbar.visibility = View.VISIBLE

        Log.d(LOG_TAG, "dataCreate Start!")

        dataProvider?.createData(path, object : IDataProvider.ParserProcessListner<WebImage> {
            private var dataCount = 0
            override fun addData(data: WebImage) {
                //progress end
                dataListAdapter.notifyItemInserted(dataCount++)
            }

            override fun onError() {
                Log.e(LOG_TAG, "DataLoadError!")

                dataListAdapter.notifyDataSetChanged()
                pb_progressbar.visibility = View.INVISIBLE
                Toast.makeText(baseContext, "Load Error", Toast.LENGTH_SHORT).show()
            }

            override fun onFinish(list: List<WebImage>) {
                Log.d(LOG_TAG, "dataCreate finish! $list.size , $dataCount")
                dataListAdapter?.notifyDataSetChanged()
                pb_progressbar.visibility = View.INVISIBLE
                Toast.makeText(baseContext, "Load finish!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.load_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var dataPath = ""

        dataProvider?.release()
        dataListAdapter.notifyDataSetChanged()

        when (item.itemId) {
            R.id.menu_file -> {
                dataPath = WebImage.DATA_FILE_PATH
                dataProvider = InjectionDataProvider.fileToSimpleTag(baseContext, "<img src=\"")
            }
            R.id.menu_web -> {
                dataPath = WebImage.DATA_PAGE_URL
                dataProvider = InjectionDataProvider.webToSimpleTag(baseContext, "<img src=\"")
            }
            R.id.menu_jsoup -> {
                dataPath = WebImage.DATA_PAGE_URL
                dataProvider = InjectionDataProvider.webToJsoup(baseContext)
            }
        }

        loadData(dataPath)

        return true
    }

    override fun onDestroy() {

        dataProvider?.release()
        super.onDestroy()
    }

    companion object {
        val LOG_TAG = "hyuhyu"
    }
}
