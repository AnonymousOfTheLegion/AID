package com.aid.app;

public class Validator2ch extends AValidator {

    private final boolean isSecure;
    private final String  url;

    public Validator2ch(String url, boolean isSecure) {
        this.isSecure = isSecure;
        this.url = url;
    }

    @Override
    public boolean isValid(String url) {
        // https://2ch.hk/b/123456.html
        try {
            StringBuilder builder = new StringBuilder(url);
            String threadNum = getElementAndTrim(builder),
                    board = getElementAndTrim(builder),
                    name = getElementAndTrim(builder),
                    protocol = getElementAndTrim(builder);

            if (name.contentEquals(this.url) && isSecureProtocol(protocol)) {
                return true;
            }

        } catch (ParseException e) {
            System.err.println("Error parsing \"" + url + "\"");
        }

        return false;
    }

    private String getElementAndTrim(StringBuilder url) throws ParseException {
        return getElementAndTrim(url, '/');
    }

    /**
     *
     * @param url - url to trim
     * @param delimiter
     * @return Returns the last element after {@code delimiter}, the {@code url} string will be trimmed
     */
    private String getElementAndTrim(StringBuilder url, char delimiter) throws ParseException {
        final int NOT_FOUND = -1;

        int posIndex = url.toString().lastIndexOf('/');
        if (posIndex != NOT_FOUND) {
            String result =
                    (posIndex != url.toString().length() - 1
                            ? url.substring(posIndex + 1)
                            : url.substring(0, posIndex)
                    );
            url.delete(posIndex, url.length()); // remove trailing slash

            if (result.contains("http")) {
                result = result.substring(0, result.indexOf(':'));
            } else if (result.contains(".html")) {
                result = result.substring(0, result.indexOf(".html"));
            }
            return result;
        }

        throw new ParseException("Could not find delimiter");
    }
}