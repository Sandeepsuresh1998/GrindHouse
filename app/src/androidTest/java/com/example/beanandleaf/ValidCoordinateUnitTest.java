package com.example.beanandleaf;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ValidCoordinateUnitTest {
    @Test
    public void testValidCoordinates() {
        AddStore asObj = new AddStore();

        String test1 = "14.456789"; //Valid
        String test2 = "hello"; //Invalid
        String test3 = "86,899"; //Invalid
        String test4 = "12. 6678";

        assertEquals(false, asObj.isValidCoord(test2));
        assertEquals(false, asObj.isValidCoord(test3));
        assertEquals(false, asObj.isValidCoord(test4));
        assertTrue(asObj.isValidCoord(test1));
    }
}
