package com.aid.app;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleTest {

    @Test
    void parseConsoleArgumentsCorrectTest() {
        String[] args = new String[] {
          "-u", "https://2ch.hk/gg/res/694216.html",
                "-d", "D:\\2ch",
                "-f", "webm"
        };

        try {
            Settings s = Console.parseConsoleArguments(args);
            assertEquals("D:\\\\2ch", s.getDir());
            assertTrue(Arrays.equals(new String[] { "webm" }, s.getFiletypes()));
            assertEquals("https://2ch.hk/gg/res/694216.html", s.getUrl());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    void parseConsoleArgumentsDirIsNotSpecified() {
        String[] args = new String[] {
                "-u", "https://2ch.hk/gg/res/694216.html",
                "-f", "webm"
        };

        try {
            Settings s = Console.parseConsoleArguments(args);
            assertEquals("D:\\2ch", s.getDir());
            assertTrue(Arrays.equals(new String[] { "webm" }, s.getFiletypes()));
            assertEquals("https://2ch.hk/gg/res/694216.html", s.getUrl());
        } catch (ParseException e) {
            assertEquals("Directory or url is not specified. Please, specify it.", e.getMessage());
        }
    }

    @Test
    void parseConsoleArgumentsGetKnownImageboards() {
        String[] knownImageBoards = new String[] {
                "2ch.hk",
                "4chan.org",
                "8ch.net"
        };

        String[] args = new String[] {
                "-u", "https://2ch.hk/gg/res/694216.html",
                "-d", "D:\\2ch",
                "-f", "webm"
        };

        try {
            Settings s = Console.parseConsoleArguments(args);
            assertTrue(Arrays.equals(knownImageBoards, s.getKnownImageBoards()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void parseConsoleArgumentsFiletypesNotSpecified() {
        String[] args = new String[] {
                "-u", "https://2ch.hk/gg/res/694216.html",
                "-d", "D:\\2ch",
        };

        String[] standardFiletypes = new String[] {
                "webm",
                "jpg",
                "jpeg",
                "png",
                "gif",
                "mp4",
                "avi", "mkv", "flv"
        };

        try {
            Settings s = Console.parseConsoleArguments(args);
            assertTrue(Arrays.equals(standardFiletypes, s.getFiletypes()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}