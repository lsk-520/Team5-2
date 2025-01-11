package io.github.unisim.headless;

import io.github.unisim.Settings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SettingsTests {
    @Test
    public void settingsTest(){
      Settings settings = new Settings();
      assertEquals(settings.getUsername(), "");
      settings.setUsername("TestName");
      assertEquals(settings.getUsername(), "TestName");
    }
}
