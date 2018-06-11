package com.spacemonster.webdataviewer.content.data;

/**
 * Created by GE62 on 2017-12-21.
 */

public class WebImage {
    public static final String MAIN_URL = "http://www.gettyimagesgallery.com/";
    public static final String DATA_FILE_PATH = "temp.html";
    public static final String DATA_PAGE_URL = "http://www.gettyimagesgallery.com/collections/archive/slim-aarons.aspx";

    private String imageUrl;

    public WebImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
