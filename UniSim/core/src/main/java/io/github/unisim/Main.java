package io.github.unisim;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.sun.management.internal.GarbageCollectorExtImpl;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends Game {
    private Screen currentScreen;
    private Music music;

    @Override
    public void create() {
        GameState.currentScreen = GameState.startScreen;
        GameState.mainScreen = GameState.startScreen;
        music = Gdx.audio.newMusic(Gdx.files.internal("assets/sound/bg_music.mp3"));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render() {
        if (currentScreen != GameState.currentScreen) {
            currentScreen = GameState.currentScreen;
            setScreen(currentScreen);
            currentScreen.resume();
            music.setVolume(GameState.settings.getVolume()*0.75f);
        }

        super.render(); // Ensures the active screen is rendered
    }

    @Override
    public void dispose() {}

    @Override
    public void resize(int width, int height) {
        if (width + height == 0) {
            return;
        }
        ((FullscreenInputProcessor) GameState.fullscreenInputProcessor).resize(width, height);
        GameState.gameScreen.resize(width, height);
        GameState.settingScreen.resize(width, height);
        GameState.startScreen.resize(width, height);
    }
}
