package com.aid.app;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashSet;
import java.util.Map;

public class Parser2ch extends AbstractParser {

    private String  origin = "2ch.hk";
    private String threadNum, board, name, protocol;

    public static Map.Entry<String, String> cookieMap = new Map.Entry<String, String>() {
        @Override
        public String getKey() {
            return "usercode_auth";
        }

        @Override
        public String getValue() {
            return "b75dbf9c599346810b8a4861ca4b9cf9";
        }

        @Override
        public String setValue(String value) {
            return getValue();
        }
    };

    @Override
    public boolean isValid(String url) {
        // https://2ch.hk/b/123456.html
        try {
            StringBuilder builder = new StringBuilder(url);
            threadNum = getElementAndTrim(builder);
            String res = getElementAndTrim(builder);
            board = getElementAndTrim(builder);
            name = getElementAndTrim(builder);
            protocol = getElementAndTrim(builder);

            if (name.contentEquals(this.origin) && isSecureProtocol(protocol)) {
                return true;
            }

        } catch (ParseException e) {
            System.err.println("Error parsing \"" + url + "\"");
        }

        return false;
    }

    static void setupJksCert() {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("cert/2ch.jks");
        try {
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(is, null);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: Test this method:
    @Override
    public HashSet<String> parse(String url, String[] filetypes) throws ParseException {

        if (isValid(url) == false) {
            throw new ParseException("URL is not valid: " + url);
        }

        setupJksCert();
        HashSet<String> urls = new HashSet<>();

        try {
            Document website = Jsoup.connect(url).cookie(cookieMap.getKey(), cookieMap.getValue()).get();
            Elements contents = website.getElementsByTag("a");

            final String hrefAttr = "href";
            String contentUrl = null;

            for (Element elem : contents) {
                contentUrl = elem.attr(hrefAttr);

                for (String filetype : filetypes) {
                    if (contentUrl.contains(filetype) && !urls.contains(contentUrl)) {
                        urls.add("https://" + origin +  contentUrl);
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return urls;
    }

    private String getElementAndTrim(StringBuilder url) throws ParseException {
        return getElementAndTrim(url, '/');
    }

    /**
     *
     * @param url - url to trim
     * @param delimiter - symbol-delimiter
     * @return Returns the last element after {@code delimiter}, the {@code url} string will be trimmed
     */
    private String getElementAndTrim(StringBuilder url, char delimiter) throws ParseException {
        final int NOT_FOUND = -1;

        int posIndex = url.toString().lastIndexOf(delimiter);
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

    public String getThreadNum() { return threadNum; }
}
