package com.jz.nebula.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LinkUtilTest {
    @Test
    public void createLinkHeaderTest() {
        String expectedHeader = "<hello>; rel=\"world\"";
        String uri = "hello";
        String rel = "world";
        assertEquals(expectedHeader, LinkUtil.createLinkHeader(uri, rel));
    }

}
