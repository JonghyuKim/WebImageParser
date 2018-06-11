package com.spacemonster.webdataviewer;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ParserUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void parserTest(){
        IParser parser;
        List<String> datas;
        parser = new TagParser("<img src=\"");
//        parseFromHttp("http://www.gettyimagesgallery.com/collections/archive/slim-aarons.aspx", parser);
        parseFromFile("temp.html", parser);

        datas = parser.getDatas();

        for(String imgUrl : datas){
            System.out.println(imgUrl);
        }
    }

    private void parseFromFile(String path, IParser parser){
//        File file = getFileFromPath(this, "assets/temp.html");
        try {
            InputStream fis = openFile(path);
            BufferedReader rd = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = rd.readLine()) != null) {
                parser.parse(line);
            }
            rd.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private InputStream openFile(String filename) throws IOException {
        return getClass().getClassLoader().getResourceAsStream(filename);
    }

    private String parseFromHttp(String httpUrl, IParser parser) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(httpUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                parser.parse(line);
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    class TagParser implements IParser{

        private List<String> urlList = null;
        private int startLine = 0;
        private int targetTagLength = 0;
        private String targetTag = null;

        public TagParser(String tag){
            urlList = new ArrayList<>();
            targetTag = tag;
            targetTagLength = tag.length();
        }

        @Override
        public void parse(String line) {
            if((startLine = line.indexOf(targetTag)) != -1){
                startLine += targetTagLength;
                urlList.add(line.substring((startLine), line.indexOf("\"", startLine)));
            }
        }

        @Override
        public List getDatas() {
            return urlList;
        }
    }

    interface IParser {
        public void parse(String line);
        public List getDatas();
    }
}