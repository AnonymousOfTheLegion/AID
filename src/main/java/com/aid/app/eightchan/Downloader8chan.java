package com.aid.app.eightchan;

import com.aid.app.AbstractParser;
import com.aid.app.Downloader;

public class Downloader8chan extends Downloader {
    @Override
    public AbstractParser getParser() {
        return new Parser8chan();
    }
}
