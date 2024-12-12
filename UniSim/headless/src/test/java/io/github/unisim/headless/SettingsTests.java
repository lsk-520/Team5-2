package io.github.unisim.headless;

import io.github.unisim.Settings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SettingsTests {
    @Test
    public void settingsTest(){
      Settings settings = new Settings();
      assertEquals(settings.getVolume(), 1.0f);
      assertEquals(settings.getUsername(), "");
      settings.setVolume(0.5f);
      assertEquals(settings.getVolume(), 0.5f);
      settings.setUsername("TestName");
      assertEquals(settings.getUsername(), "TestName");
    }
}
