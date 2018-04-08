package com.aid.app;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;

public class Downloader2ch implements Downloader {

    private String dir;

    public Downloader2ch() {
        Parser2ch.setupJksCert();
    }

    @Override
    public Downloader downloadTo(String dir) throws DownloaderException {
        this.dir = dir;

        File file = new File(dir);
        if (!(file.getName().lastIndexOf('.') == -1)) {
            System.out.println(file);
            throw new DownloaderException("It's file, you need to choose a directory!");
        }
        if (!file.exists()) {
            file.mkdirs();
        }

        return this;
    }

    @Override
    public void contents(HashSet<String> urls) throws IOException {

        File filename = null;
        URLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        byte[] buffer = new byte[4096];
        int len;
        int counter = 1;

        for (String contentUrl : urls) {
            String shortFilename = contentUrl.substring(contentUrl.lastIndexOf('/') + 1, contentUrl.length());
            System.out.print("Downloading (" + counter++ + "): " + shortFilename + '\r');

            filename = new File(dir + File.separator + shortFilename);
            connection = new URL(contentUrl).openConnection();
            connection.setRequestProperty("Cookie", Parser2ch.cookieMap.getKey() + '=' + Parser2ch.cookieMap.getValue());
            is = connection.getInputStream();
            os = new FileOutputStream(filename);

            while ((len = is.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }

            os.close();
        }

        System.out.print("                                                            \r");
        System.out.println("Done. Downloaded " + (counter - 1) + " files.");
    }

    public Parser2ch getParser() {
        return new Parser2ch();
    }

}
