package com.aid.app.futabachannel;

import com.aid.app.AbstractParser;
import com.aid.app.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

import static com.aid.app.utils.Utils.getElementAndTrim;

public class ParserFutabaChannel extends AbstractParser {

    private final String futabaOrigin = "dec.2chan.net";
    private String protocol, origin, board, res, threadNum;

    @Override
    public boolean isValid(String url) {

        if (url.indexOf(futabaOrigin) == 0) {
            url = "https://" + url;
        }

        StringBuilder builder = new StringBuilder(url);

        try {
            protocol  = getElementAndTrim(builder, "//");
            origin    = getElementAndTrim(builder);
            board     = getElementAndTrim(builder);
            res       = getElementAndTrim(builder);
            threadNum = getElementAndTrim(builder.append('/'));

            if (!protocol.contentEquals("http:") && !protocol.contentEquals("https:")) {
                return false;
            }

            if (origin.contentEquals(futabaOrigin) && res.contentEquals("res")) {
                return true;
            }

        } catch (ParseException e) {
            System.err.println("ParserFutabaChannel exception: " + e.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public HashSet<String> parse(String url, String[] filetypes) throws ParseException {

        if (!isValid(url)) {
            throw new ParseException("Url is not valid: " + url);
        }

        HashSet<String> urls = new HashSet<>();

        if (url.startsWith(futabaOrigin)) {
            url = "https://" + url;
        }

        try {
            Document website = Jsoup.connect(url).get();
            Elements contents = website.getElementsByTag("a");

            final String hrefAttr = "href";
            String contentUrl;

            for (Element e : contents) {
                contentUrl = e.attr(hrefAttr);

                for (String filetype : filetypes) {
                    if (contentUrl.contains(filetype) && !urls.contains(contentUrl)) {
                        urls.add("https://" + futabaOrigin + contentUrl);
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }

        return urls;
    }
}
