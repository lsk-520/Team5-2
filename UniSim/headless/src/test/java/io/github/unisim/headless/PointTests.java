package io.github.unisim.headless;

import io.github.unisim.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PointTests {
    @Test
    public void pointEqualsTest(){
        Point p = new Point(10,30);
        Point p2 = new Point(10, 30);
        Point p3 = new Point();
        assertFalse(p.equals("test string"));
        assertTrue(p.equals(p2));
        assertFalse(p.equals(p3));
    }

    @Test
    public void pointToStringTest(){
        Point p = new Point(10,30);
        String s = p.toString();
        assertEquals(s, "(10, 30)");
    }

    @Test
    public void getNewPointTest(){
        Point p = new Point(10,30);
        Point p2 = p.getNewPoint();
        assertEquals(p, p2);
    }
}
