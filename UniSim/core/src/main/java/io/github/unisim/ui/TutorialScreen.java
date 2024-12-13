package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.unisim.GameState;

public class TutorialScreen implements Screen {
  private Stage stage;
  private Table table;
  private Skin skin = GameState.defaultSkin;
  private Label titleLabel;
  private Label descriptionLabel;
  private TextButton backButton;
  private InputMultiplexer inputMultiplexer = new InputMultiplexer();

  public TutorialScreen() {
    stage = new Stage();
    table = new Table();

    // Back button
    backButton = new TextButton("Back", skin);
    backButton.setPosition(150, 80);
    backButton.setSize(200,50);
    backButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        GameState.currentScreen = GameState.settingScreen;
      }
    });

    table.setFillParent(true);
    table.center().center();
    table.pad(100,100,100,100);
    table.add(backButton).center().width(250).height(57);
    stage.addActor(table);

    inputMultiplexer.addProcessor(GameState.fullscreenInputProcessor);
    inputMultiplexer.addProcessor(stage);
  }

  @Override
  public void show() {}

  @Override
  public void render(float delta) {
    ScreenUtils.clear(GameState.UISecondaryColour);

    stage.act(delta);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {}

  @Override
  public void resume() {
    Gdx.input.setInputProcessor(inputMultiplexer);
  }

  @Override
  public void hide() {}

  @Override
  public void dispose() {
    stage.dispose();
    skin.dispose();
  }
}
