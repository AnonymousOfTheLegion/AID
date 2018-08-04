package com.aid.app.futabachannel;

import com.aid.app.AbstractParser;
import com.aid.app.Downloader;

public class DownloaderFutabaChannel extends Downloader {
    @Override
    public AbstractParser getParser() {
        return new ParserFutabaChannel();
    }
}
