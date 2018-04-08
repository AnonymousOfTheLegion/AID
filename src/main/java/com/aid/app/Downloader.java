package com.aid.app;

import java.io.IOException;
import java.util.HashSet;

/**
 * Interface usage:
 *
 * Implement methods to use Downloader like:
 * Downloader d = new ImageBoardDownloader().downloadTo("D:/Images").contents(Parser.parse());
 */
public interface Downloader {
    Downloader downloadTo(String dir) throws DownloaderException;
    void contents(HashSet<String> urls) throws IOException;
    AbstractParser getParser();
}
