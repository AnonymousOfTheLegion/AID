package com.aid.app.fourchan;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Parser4chanTest {

    private Parser4chan parser = new Parser4chan();

    @Test
    void validUrl() {
        assertTrue(parser.isValid("http://boards.4chan.org/o/thread/16832611"));
        assertTrue(parser.isValid("http://boards.4chan.org/o/thread/16832611/need-advice-on-buying-a-good-cheap-used-car-look"));
        assertTrue(parser.isValid("http://boards.4chan.org/o/thread/16832611/"));
    }

    @Test
    void invalidUrl() {
        assertFalse(parser.isValid("http://boardz.4chan.org/o/thread/16832611"));
        assertFalse(parser.isValid("http://boardz.4chan.org/o/thread/"));

    }
}