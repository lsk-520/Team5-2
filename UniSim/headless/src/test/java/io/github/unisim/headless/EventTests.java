package io.github.unisim.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.unisim.building.BuildingType;
import io.github.unisim.event.Event;
import io.github.unisim.event.EventManager;
import io.github.unisim.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EventTests {

  // Runs before each test to mock graphical functionality of tested classes
  @BeforeEach
  public void initTest() {
    // Mock Gdx.gl to avoid any OpenGL calls
    GL20 mockGl = mock(GL20.class);
    when(mockGl.glCreateShader(anyInt())).thenReturn(1);  // Mock shader creation
    Gdx.gl = mockGl;

    // Mock Gdx.graphics
    Graphics mockGraphics = mock(Graphics.class);
    when(mockGraphics.getWidth()).thenReturn(800);  // Mock screen width
    when(mockGraphics.getHeight()).thenReturn(600); // Mock screen height
    Gdx.graphics = mockGraphics;

    // Mock Gdx.files
    Gdx.files = mock(com.badlogic.gdx.Files.class);
    FileHandle mockFileHandle = mock(FileHandle.class);
    when(Gdx.files.internal(anyString())).thenReturn(mockFileHandle);

    // Mock Gdx.app
    Gdx.app = mock(com.badlogic.gdx.Application.class);
  }

  @Test
  public void eventManagerTest(){
    HeadlessLauncher.main(new String[0]);
    World mockWorld = mock(World.class);
    EventManager eventManager = new EventManager(mockWorld);
    Event eventFirst = eventManager.getCurrentEvent();
    eventManager.nextEvent();
    Event eventSecond = eventManager.getCurrentEvent();
    assertNotEquals(eventFirst, eventSecond);
  }

  @Test
  public void eventTest() {
    HeadlessLauncher.main(new String[0]);
    Event testTickingEvent = new Event( "Fee Rise",
                                        "Student tuition fees have risen.\nStudent satisfaction decreases by 1% per second.",
                                        new Image(new Texture(Gdx.files.internal("buildings/library.png"))),
                                        -1,
                                        1_000f);

    Event testBuildingEvent = new Event("Roses",
                    "Time to compete!\nMake sure you have enough sports buildings",
                                new Image(new Texture(Gdx.files.internal("buildings/stadium.png"))),
                               -30, 1,
                               BuildingType.RECREATION);


    assertEquals(testBuildingEvent.getAdjustment(), 0.7f);
    assertTrue(testTickingEvent.isTickEvent());
    assertFalse(testBuildingEvent.isTickEvent());
    assertEquals(testBuildingEvent.getBuildingType(), BuildingType.RECREATION);

    assertEquals(testBuildingEvent.tick(), -30);

    assertEquals(testTickingEvent.getRemainingTime(), "00:30");
    assertEquals(testTickingEvent.tick(), 0);
    testTickingEvent.testTimerTick();
    assertEquals(testTickingEvent.getRemainingTime(), "00:20");
    testTickingEvent.reset();
    assertEquals(testTickingEvent.getRemainingTime(), "00:30");
    for (int i = 0; i < 3; i++){
      testTickingEvent.testTimerTick();
    }
    testTickingEvent.testTimerTick();
    testTickingEvent.tick();
    assertEquals(testTickingEvent.getRemainingTime(), "00:30");
  }
}
