package com.aid.app;

import java.util.HashSet;

public abstract class AbstractParser {
    abstract public boolean isValid(String url);
    abstract public HashSet<String> parse(String url, String[] filetypes) throws ParseException;

    public boolean isSecureProtocol(String proto) throws ParseException {
        switch (proto) {
            case "https":
                return true;
            case "http":
                return false;
            default:
                throw new ParseException("Unknown protocol");
        }
    }
}
