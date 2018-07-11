package com.spacemonster.webdataviewer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val dataListAdapter: AbstractDataListAdapter<WebImage> = WebImageListAdapter()

    private val subject = PublishSubject.create<MenuItem>()

    private val compositDisposable = CompositeDisposable()

    private val dataList: ArrayList<WebImage> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(applicationContext)

        rv_image_list.layoutManager = layoutManager
        rv_image_list.adapter = dataListAdapter
        dataListAdapter.dataList = dataList


        val dataProviderSource = subject.subscribeOn(Schedulers.io())
                .map (::createDataProvider)

        compositDisposable += dataProviderSource.observeOn(AndroidSchedulers.mainThread())
                .subscribe {
            pb_progressbar.visibility = View.VISIBLE
        }

        val createDataSource = dataProviderSource.doOnNext {
            Log.d(LOG_TAG, "dataCreate Start!1")
            dataList.clear()
            dataListAdapter.notifyDataSetChanged()
        }.observeOn(Schedulers.io())
         .flatMap {
             it.createData()
         }

        compositDisposable += createDataSource.observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { Log.d("hyuhyu", "complite call!!!!!") }
                .subscribe({
                    Log.d(LOG_TAG, "addData ${dataList.size}")
                    dataList.add(it!!)
                }
                ,{
            Log.e(LOG_TAG, "DataLoadError! ${Log.getStackTraceString(it)}")

            dataListAdapter.notifyDataSetChanged()
            pb_progressbar.visibility = View.INVISIBLE
            Toast.makeText(baseContext, "Load Error", Toast.LENGTH_SHORT).show()}
                ,{
            Log.d(LOG_TAG, "dataCreate finish! ${dataList.size}")
            dataListAdapter.notifyDataSetChanged()
            pb_progressbar.visibility = View.INVISIBLE
            Toast.makeText(baseContext, "Load finish!", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.load_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        subject.onNext(item)
        return true
    }

    private fun createDataProvider(item: MenuItem): IDataProvider<String, WebImage>{
         return when (item.itemId) {
            R.id.menu_file -> InjectionDataProvider.fileToSimpleTag(baseContext, "<img src=\"", WebImage.DATA_FILE_PATH)
            R.id.menu_web -> InjectionDataProvider.webToSimpleTag(baseContext, "<img src=\"", WebImage.DATA_PAGE_URL)
            R.id.menu_jsoup -> InjectionDataProvider.webToJsoup(baseContext, WebImage.DATA_PAGE_URL)
            else -> InjectionDataProvider.fileToSimpleTag(baseContext, "<img src=\"", WebImage.DATA_FILE_PATH)
        }
    }

    override fun onDestroy() {
        compositDisposable.clear()
        super.onDestroy()
    }

    companion object {
        val LOG_TAG = "hyuhyu"
    }
}
