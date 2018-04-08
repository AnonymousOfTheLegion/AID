package com.aid.app;

import java.io.IOException;
import java.util.HashSet;

/*
  @TODO: Create downloader class which extends interface 'downloader'
  @TODO: This class must load 2ch jks cert and set 2ch cookies, no matter what
  @TODO: imageboard contents are downloading
 */

public class AIDownloader {
    public static void main(String[] args) {

        try {
            Downloader d = new Downloader2ch();
            Parser2ch parser = new Parser2ch();
            HashSet<String> content = parser.parse("https://2ch.hk/gg/res/694216.html", new String[] { "jpg" });
            d.downloadTo("D:/2ch/" + parser.getThreadNum()).contents(content);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DownloaderException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
