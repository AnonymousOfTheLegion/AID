package com.aid.app.dvach;

import com.aid.app.AbstractParser;
import com.aid.app.ParseException;
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

import static com.aid.app.utils.Utils.getElementAndTrim;

public class Parser2ch extends AbstractParser {

    private String  origin = "2ch.hk";
    private String threadNum, board, name, protocol;

    /**
     * Cookies is needed to give access to the 'hidden' boards, such like 'gg', 'e' etc.
     */
    static Map.Entry<String, String> cookieMap = new Map.Entry<String, String>() {
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

            protocol = getElementAndTrim(builder, ':');
            builder.delete(0, "//".length());
            name = getElementAndTrim(builder);
            board = getElementAndTrim(builder);
            String res = getElementAndTrim(builder);
            threadNum = getElementAndTrim(builder, '.');

            if (name.contentEquals(this.origin) && isSecureProtocol(protocol)) {
                return true;
            }

        } catch (ParseException e) {
            System.err.println("Error parsing \"" + url + "\": " + e.getMessage());
        }

        return false;
    }

    /**
     * Method loads JKS keystore to grant TLS connection with 2ch.hk
     * TODO: Generate JKS programmatically if connection exception was thrown;
     */
    static void setupJksCert() {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("cert/2ch.jks");
        try {
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(is, null);
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method fetch html code from given url, check file types and
     * loads files' URLs to the HashSet if they are equal to at least one of 'filetypes' array object
     *
     * @param url - URL to fetch
     * @param filetypes - String array of file types to upload
     * @return HashSet with URLs of media from imageboard
     * @throws ParseException if URL string isn't valid.
     * TODO: Test this method:
     */
    @Override
    public HashSet<String> parse(String url, String[] filetypes) throws ParseException {

        // this shit should be removed from here:
        if (!url.contains("http")) {
            url = "https://" + url;
        }

        if (!isValid(url)) {
            throw new ParseException("Invalid URL: " + url);
        }

        setupJksCert();
        HashSet<String> urls = new HashSet<>();

        try {
            Document website = Jsoup.connect(url).cookie(cookieMap.getKey(), cookieMap.getValue()).get();
            Elements contents = website.getElementsByTag("a");

            final String hrefAttr = "href";
            String contentUrl;

            for (Element elem : contents) {
                contentUrl = elem.attr(hrefAttr);

                for (String filetype : filetypes) {
                    if (contentUrl.contains(filetype) && !urls.contains(contentUrl)) {
                        urls.add("https://" + origin +  contentUrl);
                    }
                }

            }

        }  catch (IOException e) {
            System.err.println("Connection error! Check your internet connection status" +
                    " (or 2ch.hk IP may be blocked by your ISP. Use VPN to pass ISP restrictions).");
        }

        return urls;
    }

    public String getThreadNum() { return threadNum; }
}
