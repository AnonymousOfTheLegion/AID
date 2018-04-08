package com.aid.app.dvach;

import com.aid.app.Downloader;

public class Downloader2ch extends Downloader {
    public Downloader2ch() {
        cookieMap = Parser2ch.cookieMap;
        Parser2ch.setupJksCert();
    }

    public Parser2ch getParser() {
        return new Parser2ch();
    }

}
