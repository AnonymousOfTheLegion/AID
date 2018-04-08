package com.aid.app.fourchan;

import com.aid.app.AbstractParser;
import com.aid.app.ParseException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class Parser4chan extends AbstractParser {
    private final String fourChanOrigin = "boards.4chan.org";
    private String origin, board, boardTag, threadNum;

    @Override
    public boolean isValid(String url) {

        if (url.indexOf("http://") == 0) {
            url = url.substring("http://".length());
        }
        if (url.charAt(url.length() - 1)!= '/') {
            url += '/';
        }

        StringBuilder builder = new StringBuilder(url);

        try {
            origin = getElementAndTrim(builder);
            board  = getElementAndTrim(builder);
            boardTag = getElementAndTrim(builder);
            threadNum = getElementAndTrim(builder);

            if (origin.contentEquals(fourChanOrigin)) {
                return true;
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            return false;
        }

        return false;
    }

    String getElementAndTrim(StringBuilder url) throws ParseException {
        return getElementAndTrim(url, '/');
    }

    String getElementAndTrim(StringBuilder url, char delimiter) throws ParseException {
            final int NOT_FOUND = -1;

            int posIndex = url.toString().indexOf(delimiter);
            if (posIndex != NOT_FOUND) {
                String result = url.substring(0, posIndex);
                url.delete(0, posIndex + 1);
                return result;
            }
            throw new ParseException("Could not find delimiter");
    }

    @Override
    public HashSet<String> parse(String url, String[] filetypes) throws ParseException {

        if (!isValid(url)) {
            throw new ParseException("Invalid URL: " + url);
        }

        HashSet<String> urls = new HashSet<>();

        try {
            Document website = Jsoup.connect(url).get();
            Elements contents = website.getElementsByTag("a");

            final String hrefAttr = "href";
            String contentUrl = null;

            for (Element e : contents) {
                contentUrl = e.attr(hrefAttr);

                for (String filetype : filetypes) {
                    int temp = contentUrl.lastIndexOf(filetype);
                    if (contentUrl.lastIndexOf(filetype) != -1
                            && contentUrl.charAt(contentUrl.lastIndexOf(filetype) - 1) == '.'
                            && !urls.contains(contentUrl) && !contentUrl.contains("s.")) {
                        urls.add("http:" + contentUrl);
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
