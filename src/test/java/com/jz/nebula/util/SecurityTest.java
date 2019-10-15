package com.jz.nebula.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class SecurityTest {

    @Test
    public void generateHashTest() {
        String expectedHash = "098f6bcd4621d373cade4e832627b4f6";
        assertEquals(expectedHash, Security.generateHash("test"));
    }

    @Test
    public void getRandomHashTest() {
        assertTrue(Security.getRandomHash() != null);
    }
}
