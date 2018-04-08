package com.aid.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Parser2ChTest {

    private  Parser2ch parser;

    Parser2ChTest() {
        parser = new Parser2ch();
    }

    @DisplayName("2ch url parser test")
    @Test
    void isValid() {
        assertTrue(parser.isValid("https://2ch.hk/b/res/123456.html"));
    }

    @Test
    void invalidUrl() {
        assertFalse( parser.isValid("absca"));
    }

    @Test
    void isSecureProtocolHttp() {
        try {
            assertFalse(parser.isSecureProtocol("http"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void isSecureProtocolHttps() {
        try {
            assertTrue(parser.isSecureProtocol("https"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void isSecureProtocolUnknownProtocolException() {
        Throwable exception = assertThrows(ParseException.class,
                () -> parser.isSecureProtocol("nonExistentProtocol"));

        String unknownProtoMsg = "Unknown protocol";
        assertEquals(unknownProtoMsg, exception.getMessage());
    }

    /*
    @Test
    void isValidThrowsException() {
        Parser2ch parser = new Parser2ch("2ch.hk", true);
        Throwable exception = assertThrows(ParseException.class,
                () -> { parser.isValid("shit.hk");
        });

        assertEquals("Error parsing \"shit.hk\"", exception.getMessage());

    }
    */
}