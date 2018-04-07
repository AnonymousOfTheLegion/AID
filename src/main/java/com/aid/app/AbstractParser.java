package com.aid.app;

abstract class AbstractParser {
    abstract public boolean isValid(String url);

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
