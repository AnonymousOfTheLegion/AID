package com.aid.app;

import java.util.HashSet;

abstract class AbstractParser {
    abstract public boolean isValid(String url);
    abstract public HashSet<String> parse(String url, String[] filetypes) throws ParseException;

    boolean isSecureProtocol(String proto) throws ParseException {
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
