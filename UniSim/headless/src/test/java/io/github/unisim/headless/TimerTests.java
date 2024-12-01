package io.github.unisim.headless;

import io.github.unisim.Timer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TimerTests {
    /**
     * Tests that the tick function of the timer removes time correctly
     */
    @Test
    public void tickTest(){
        Timer timer = new Timer(90_000);
        assertEquals(timer.getRemainingTime(), "01:30");
        timer.tick(10_000);
        assertEquals(timer.getRemainingTime(), "01:20");
    }

    /**
     * Tests that the timer returns correct result when time reaches 0 or less
     */
    @Test
    public void hasFinishedTest(){
        Timer timer = new Timer(90_000);
        // timer.tick should return true when the remaining time is > 0
        // and return false when remaining time is <= 0
        assertTrue(timer.tick(10_000));
        assertFalse(timer.tick(90_000));
    }

    /**
     * Tests that the timer resets to initial value correctly
     */
    @Test
    public void resetTest(){
        Timer timer = new Timer(90_000);
        timer.tick(20_000);
        timer.reset();
        assertEquals(timer.getRemainingTime(), "01:30");
    }
}
