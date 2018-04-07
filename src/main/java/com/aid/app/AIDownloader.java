package com.aid.app;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class AIDownloader {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        final String url = "http://google.com";

        try {
            Document doc = Jsoup.connect(url).get();
            System.out.println(doc.title());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
