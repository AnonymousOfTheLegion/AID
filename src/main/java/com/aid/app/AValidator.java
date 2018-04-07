package com.aid.app;

public abstract class AValidator {
    abstract public boolean isValid(String url);

    public boolean isSecureProtocol(String proto) throws ParseException {
        switch (proto) {
            case "https":
                return true;
            case "http":
                return false;
            default:
                throw new ParseException("Wrong protocol");
        }
    }
}
