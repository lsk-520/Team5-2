package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import io.github.unisim.GameState;
import io.github.unisim.event.Event;
import io.github.unisim.world.World;

import java.util.ArrayList;

public class Leaderboard {
    private ShapeActor bar;
    private Table boardTable = new Table();
    private Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    //cells
    private World world;

    private Label titleLabel;
    private ArrayList<Label> userLabels = new ArrayList<Label>();
    private ArrayList<Label> scoreLabels = new ArrayList<Label>();
    private Label yourScoreLabel;

    private Cell<Label> titleLabelCell;
    private ArrayList<Cell<Label>> userLabelCells = new ArrayList<Cell<Label>>();
    private ArrayList<Cell<Label>> scoreLabelCells = new ArrayList<Cell<Label>>();
    private Cell<Label> yourScoreLabelCell;

    public Leaderboard(Stage stage, World world) {
        this.world = world;

        // Table creation
        //boardTable.center().center();
        titleLabel = new Label("Leaderboard", skin);
        titleLabel.setAlignment(Align.center);
        titleLabelCell = boardTable.add(titleLabel).align(Align.center).colspan(2);
        yourScoreLabel = new Label("Your Score: 0%", skin);
        for (int i = 0; i < 5; i++) {
            boardTable.row();
            userLabels.add(new Label("AAA", skin));
            scoreLabels.add(new Label("0%", skin));
            userLabels.get(i).setAlignment(Align.center);
            scoreLabels.get(i).setAlignment(Align.center);
            userLabelCells.add(boardTable.add(userLabels.get(i)).align(Align.center));
            scoreLabelCells.add(boardTable.add(scoreLabels.get(i)).align(Align.center));
        }
        boardTable.row();
        yourScoreLabelCell = boardTable.add(yourScoreLabel).align(Align.center).colspan(2);

        bar = new ShapeActor(GameState.UIPrimaryColour);
        this.setVisible(false);
    }

    public void resize(int width, int height) {
        float barWidth = height * 0.3f;
        float barHeight = (height * 0.42f);
        bar.setBounds((width - barWidth) / 2f, ((height-barHeight)/2f)-(height * 0.015f), barWidth, barHeight);
        boardTable.setBounds((width - barWidth) / 2f, ((height-barHeight)/2f)-(height * 0.015f), barWidth, barHeight);

        // look at info bar for other stuff
        titleLabel.setFontScale(height * 0.002f);
        titleLabelCell.height(height * 0.05f);
        yourScoreLabel.setFontScale(height * 0.002f);
        yourScoreLabelCell.height(height * 0.1f);
        for (int i = 0; i < 5; i++) {
            userLabels.get(i).setFontScale(height * 0.0015f);
            userLabelCells.get(i).width(barWidth * 0.5f).height(height * 0.05f);
            scoreLabels.get(i).setFontScale(height * 0.0015f);
            scoreLabelCells.get(i).width(barWidth * 0.5f).height(height * 0.05f);
        }
    }

    public void show(Stage stage) {
        stage.addActor(bar);
        stage.addActor(boardTable);
        this.setVisible(true);
    }

    public void setVisible(boolean visible) {
        boardTable.setVisible(visible);
        bar.setVisible(visible);
    }
}
