package com.aid.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Validator2chTest {
    @DisplayName("2ch Validator test")
    @Test
    void isValid() {
        Validator2ch validator = new Validator2ch("2ch.hk", true);
        assertEquals(true, validator.isValid("https://2ch.hk/b/123456.html"));
    }
}