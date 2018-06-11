package com.spacemonster.webdataviewer.content.parser;

import com.spacemonster.webdataviewer.content.data.WebImage;

/**
 * Created by GE62 on 2017-12-23.
 */

public class SimpleTagWebImageParser implements IDataParser<String, WebImage>{

    private int startLine = 0;
    private int targetTagLength = 0;
    private String targetTag = null;

    private boolean isCommentContinue = false;

    public SimpleTagWebImageParser(String tag){
        targetTag = tag;
        targetTagLength = tag.length();
    }

    @Override
    public WebImage parse(String line) {

        if(checkComment(line) == true){
            return null;
        }

        if((startLine = line.indexOf(targetTag)) != -1){
            startLine += targetTagLength;
            return new WebImage(line.substring((startLine), line.indexOf("\"", startLine)));
        }

        return null;
    }

    public boolean checkComment(String line){
        if(isCommentContinue == false && line.indexOf("<!--") != -1){
            if(line.lastIndexOf("--!>") != -1) {
                isCommentContinue = false;
            }else{
                isCommentContinue = true;
            }
            return true;
        }

        if(isCommentContinue == true && line.lastIndexOf("-->") != -1){
            isCommentContinue = false;
            return true;
        }

        return false;
    }

    @Override
    public void release() {
    }
}
