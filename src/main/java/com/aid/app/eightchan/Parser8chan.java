package com.aid.app.eightchan;

import com.aid.app.AbstractParser;
import com.aid.app.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

import static com.aid.app.utils.Utils.getElementAndTrim;

public class Parser8chan extends AbstractParser {
    private final String eightChanOrigin = "8ch.net";
    private String origin, board, boardTag, threadNum;


    @Override
    public boolean isValid(String url) {
        final char DELIMITER = '/';

        if (url.indexOf("https://") == 0) {
            url = url.substring("https://".length());
        }
        if (url.charAt(url.length() - 1) != DELIMITER) {
            url = url.trim() + DELIMITER;
        }

        StringBuilder builder = new StringBuilder(url);

        try {
            origin = getElementAndTrim(builder);
            board  = getElementAndTrim(builder);
            boardTag = getElementAndTrim(builder);
            threadNum = getElementAndTrim(builder, ".");

            if (origin.contentEquals(eightChanOrigin)) {
                return true;
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            return false;
        }

        return false;
    }

    @Override
    public HashSet<String> parse(String url, String[] filetypes) throws ParseException {
        final String THUMBNAILS = "thumb";

        if (!isValid(url)) {
            throw new ParseException("Invalid URL: " + url);
        }

        HashSet<String> urls = new HashSet<>();

        try {
            Document website = Jsoup.connect(url).get();
            Elements contents = website.getElementsByTag("a");

            final String hrefAttr = "href";
            String contentUrl;

            for (Element e : contents) {
                contentUrl = e.attr(hrefAttr);

                for (String filetype : filetypes) {
                    if (contentUrl.lastIndexOf(filetype) != -1
                            && contentUrl.charAt(contentUrl.lastIndexOf(filetype) - 1) == '.'
                            && !urls.contains(contentUrl) && !contentUrl.contains(THUMBNAILS)) {
                        urls.add(contentUrl);
                    }
                }

            }

        } catch (IOException e) { // @TODO: make method exception signature and catch exception outside this method
            // @TODO: To show error message if connection was not successful.
            e.printStackTrace();
        }

        return urls;
    }
}
