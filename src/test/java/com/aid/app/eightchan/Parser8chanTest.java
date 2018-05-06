package com.aid.app.eightchan;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Parser8chanTest {

    @Test
    void validUrlTest() {
        Parser8chan parser = new Parser8chan();

        assertTrue(parser.isValid("https://8ch.net/cafechan/res/15655.html"));
        assertTrue(parser.isValid("8ch.net/cafechan/res/15655.html"));
    }

    @Test
    void invalidUrlTest() {
        Parser8chan parser = new Parser8chan();

        assertFalse(parser.isValid("net/cafechan/res"));
    }
}