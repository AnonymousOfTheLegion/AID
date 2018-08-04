package com.aid.app.futabachannel;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserFutabaChannelTest {

    @Test
    void isValid() {
        ParserFutabaChannel parser = new ParserFutabaChannel();

        Assert.assertTrue(parser.isValid("http://dec.2chan.net/62/res/51470.htm"));
        Assert.assertTrue(parser.isValid("https://dec.2chan.net/62/res/51470.htm"));
        Assert.assertTrue(parser.isValid("dec.2chan.net/62/res/51470.htm"));

        Assert.assertFalse(parser.isValid("https://shit.2chan.net/62/res/51470.htm"));
        Assert.assertFalse(parser.isValid("wrongproto://dec.2chan.net/62/res/51470.htm"));
    }

}