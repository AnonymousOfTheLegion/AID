package com.aid.app;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Map;

public abstract class Downloader {
    private String dir;
    protected Map.Entry<String, String> cookieMap = null;

    @SuppressWarnings("WeakerAccess")
    public Downloader downloadTo(String dir) throws DownloaderException {
        this.dir = dir;

        File file = new File(dir);
        if (!(file.getName().lastIndexOf('.') == -1)) {
            throw new DownloaderException("\"" + file + "\" - It's a file, you need to choose the directory!");
        }
        if (!file.exists()) {
            boolean result = file.mkdirs();
            if (!result) {
                throw new DownloaderException("Error creating directory " + file);
            }
        }

        return this;
    }

    @SuppressWarnings("WeakerAccess")
    public void contents(HashSet<String> urls) throws IOException {

        File filename;
        URLConnection connection;
        InputStream is;
        OutputStream os;
        byte[] buffer = new byte[4096];
        int len;
        int counter = 1;

        for (String contentUrl : urls) {
            String shortFilename = contentUrl.substring(contentUrl.lastIndexOf('/') + 1, contentUrl.length());

            if (shortFilename.length() > 20) {
                shortFilename = "..." + shortFilename.substring(shortFilename.length() / 2);
            }

            System.out.print("Downloading (" + counter++ + "): " + shortFilename + "    \r");

            filename = new File(dir + File.separator + shortFilename);
            connection = new URL(contentUrl).openConnection();
            if (cookieMap != null) {
                connection.setRequestProperty("Cookie", cookieMap.getKey() + '=' + cookieMap.getValue());
            }
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

    /**
     * @return - concrete parser must be returned, implemented for each imageboard.
     */
    abstract public AbstractParser getParser();
}
