package com.example.beanandleaf;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EditMenuUnitTest {
    @Test
    public void sizeTests() {
        EditMenuItem emi = new EditMenuItem(1, "Coffee", "Small", "Medium", "Large");

        String test1 = "Small";
        String test2 = "Medium";
        String test3 = "Large";
        String test4 = null;
        String test5 = "Small    ";

        //Test cases
        assertEquals(true, emi.checkSizes(test1));
        assertEquals(true, emi.checkSizes(test2));
        assertEquals(true, emi.checkSizes(test3));
        assertEquals(false, emi.checkSizes(test4));
        assertEquals(false, emi.checkSizes(test5));
    }
}
