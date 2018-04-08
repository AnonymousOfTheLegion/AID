package com.aid.app.fourchan;

import com.aid.app.AbstractParser;
import com.aid.app.Downloader;

public class Downloader4chan extends Downloader {
    @Override
    public AbstractParser getParser() {
        return new Parser4chan();
    }
}
