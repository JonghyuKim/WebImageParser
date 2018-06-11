package com.spacemonster.webdataviewer.content.data

/**
 * Created by GE62 on 2017-12-21.
 */

class WebImage(var imageUrl: String = "") {
    companion object {
        val MAIN_URL = "http://www.gettyimagesgallery.com/"
        val DATA_FILE_PATH = "temp.html"
        val DATA_PAGE_URL = "http://www.gettyimagesgallery.com/collections/archive/slim-aarons.aspx"
    }
}
