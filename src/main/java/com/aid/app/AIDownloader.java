package com.aid.app;

import java.io.IOException;

/*
  @TODO: Create downloader class which extends interface 'downloader'
  @TODO: This class must load 2ch jks cert and set 2ch cookies, no matter what
  @TODO: imageboard contents are downloading
 */

public class AIDownloader {
    public static void main(String[] args) {
        try {
            Console.printTitle();
            Settings downloaderSettings = Console.parseConsoleArguments(args);
            Downloader d = downloaderSettings.getDownloader();
            d.downloadTo(downloaderSettings.getDir())
                    .contents(d.getParser().parse(downloaderSettings.getUrl(), downloaderSettings.getFiletypes()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DownloaderException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            if (e.getMessage() == "Args not specified") {
                Console.printHelp();
            } else {
                e.printStackTrace();
            }
        }

    }
}
