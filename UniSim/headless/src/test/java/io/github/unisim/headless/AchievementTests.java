package io.github.unisim.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.unisim.achievement.Achievement;
import io.github.unisim.achievement.AchievementManager;
import io.github.unisim.achievement.BuildingPlacementRequirement;
import io.github.unisim.building.BuildingType;
import io.github.unisim.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AchievementTests {

  // Runs before each test to mock graphical functionality of tested classes
  // Essential as the headless launcher will fail if any rendering is required in code
  @BeforeEach
  public void initTest() {
    // Mock Gdx.gl to avoid any OpenGL calls
    GL20 mockGl = mock(GL20.class);
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
  public void achievTest(){
    HeadlessLauncher.main(new String[0]);
    World mockWorld = mock(World.class);
    Image achievementImage = new Image(new Texture("achievements/trophy.png"));
    Achievement testAchievement = new Achievement("Test Name",
                                                  "This is a test description",
                                                  achievementImage,
                                                  10,
                                                  new BuildingPlacementRequirement(BuildingType.values(), 5, 500));
    assertEquals(testAchievement.getScoreChange(), 10f);
    assertFalse(testAchievement.checkAchieved(mockWorld));
    assertFalse(testAchievement.testAchieved());
    testAchievement.reset();
    assertFalse(testAchievement.testAchieved());
  }

  @Test
  public void achievManagerTest(){
    HeadlessLauncher.main(new String[0]);
    World mockWorld = mock(World.class);
    AchievementManager achievementManager = new AchievementManager(mockWorld);
    assertFalse(achievementManager.displaying());
    achievementManager.testCurrentAchievement();
    achievementManager.displayAchievement(false);
    assertTrue(achievementManager.displaying());
    achievementManager.reset();
    assertFalse(achievementManager.displaying());
  }
}
