package com.example.beanandleaf;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ValidNumberTest {
    @Test
    public void checkValidInteger() {
        AddMenuItem amiObj = new AddMenuItem();

        String test1 = "6";
        String test2 = "3.45";
        String test3 = "jdnf";
        String test4 = "1 2";

        //Integer tests
        assertEquals(true, amiObj.isValidInteger(test1));
        assertEquals(false, amiObj.isValidInteger(test2));
        assertEquals(false, amiObj.isValidInteger(test3));
        assertEquals(false, amiObj.isValidInteger(test4));

    }

    @Test
    public void checkValidDouble() {
        AddMenuItem amiObj = new AddMenuItem();

        String test1 = "6";
        String test2 = "2.45";
        String test3 = "skdjfn";
        String test4 = "3.5.6";
        String test5 = "7.6 ";

        assertEquals(true, amiObj.isValidDouble(test1));
        assertEquals(true, amiObj.isValidDouble(test2));
        assertEquals(false, amiObj.isValidDouble(test3));
        assertEquals(false, amiObj.isValidDouble(test4));
        assertEquals(false, amiObj.isValidDouble(test5));

    }
}
