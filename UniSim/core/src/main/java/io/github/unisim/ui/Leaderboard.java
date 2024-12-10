package io.github.unisim.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import io.github.unisim.GameState;
import io.github.unisim.event.Event;
import io.github.unisim.world.World;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Leaderboard {
    private ShapeActor bar;
    private Table boardTable = new Table();
    private Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

    private World world;

    private Label titleLabel;
    private ArrayList<Label> userLabels = new ArrayList<Label>();
    private ArrayList<Label> scoreLabels = new ArrayList<Label>();
    private Label yourScoreLabel;

    private Cell<Label> titleLabelCell;
    private ArrayList<Cell<Label>> userLabelCells = new ArrayList<Cell<Label>>();
    private ArrayList<Cell<Label>> scoreLabelCells = new ArrayList<Cell<Label>>();
    private Cell<Label> yourScoreLabelCell;

    private boolean shown = false;
    private ArrayList<String[]> leaderboard;

    public Leaderboard(Stage stage, World world) {
        this.world = world;
        bar = new ShapeActor(GameState.UIPrimaryColour);
        this.setVisible(false);
    }

    private void displayLeaderboard() {
      // Table creation
      titleLabel = new Label("Leaderboard", skin);
      titleLabel.setAlignment(Align.center);
      titleLabelCell = boardTable.add(titleLabel).align(Align.center).colspan(2);

      for (int i = 0; i < 5; i++) {
        boardTable.row();
        String[] details = getDetails(i);
        userLabels.add(new Label(details[0], skin));
        scoreLabels.add(new Label(details[1] + "%", skin));
        userLabels.get(i).setAlignment(Align.center);
        scoreLabels.get(i).setAlignment(Align.center);
        userLabelCells.add(boardTable.add(userLabels.get(i)).align(Align.center));
        scoreLabelCells.add(boardTable.add(scoreLabels.get(i)).align(Align.center));
      }
      boardTable.row();

      yourScoreLabel = new Label("Your Score: " + world.getScore(), skin);
      yourScoreLabelCell = boardTable.add(yourScoreLabel).align(Align.center).colspan(2);
    }

    private String[] getDetails(int i) {
      if (i >= leaderboard.size()) {
        return new String[]{"AAA", "0%"};
      }
      else {
        return leaderboard.get(i);
      }
    }

    private void updateLeaderboard() {
      shown = true;
      // Gets values from leaderboard.txt (sorted by descending score).
      String username = GameState.settings.getUsername();
      String lbString = Gdx.files.internal("leaderboard.txt").readString();
      leaderboard = new ArrayList<>(); // To write back to the file.
      // If leaderboard empty, add current username and score.
      if (lbString.isEmpty()) {
        leaderboard.add(new String[]{username, String.valueOf((int)world.getScore())});
      }
      // Otherwise, read from the current leaderboard and add in the current username and score.
      else {
        // Get names and scores off the leaderboard.
        String[] lbArray = lbString.split("\n");
        String[][] namePointsArray = new String[lbArray.length][2]; // To add new details to.
        for (int i = 0; i < lbArray.length; i++) {
          namePointsArray[i] = lbArray[i].split(" ");
        }
        // Add current name and score to leaderboard.
        boolean added = false;
        for (String[] namePoints : namePointsArray) {
          if (Integer.valueOf(namePoints[1]) <= (int)(world.getScore()) && !added) {
            leaderboard.add(new String[]{username, String.valueOf((int)(world.getScore()))});
            leaderboard.add(namePoints);
            added = true;
          } else if (namePoints[0] != username) {
            leaderboard.add(namePoints);
          }
        }
      }
      // Write back to file.
      String lbWrite = "";
      for (String[] namePoints : leaderboard) {
        lbWrite += namePoints[0] + " " + namePoints[1] + "\n";
      }
      try (BufferedWriter writer = new BufferedWriter(new FileWriter("leaderboard.txt"))) {
        writer.write(lbWrite);
      } catch (IOException e) {}
    }

    public void resize(int width, int height) {
      if (shown) {
        float barWidth = height * 0.3f;
        float barHeight = (height * 0.42f);
        bar.setBounds((width - barWidth) / 2f, ((height - barHeight) / 2f) - (height * 0.015f), barWidth, barHeight);
        boardTable.setBounds((width - barWidth) / 2f, ((height - barHeight) / 2f) - (height * 0.015f), barWidth, barHeight);

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
    }

    public void show(Stage stage) {
      if (!shown) {
        updateLeaderboard();
        displayLeaderboard();
      }
      stage.addActor(bar);
      stage.addActor(boardTable);
      this.setVisible(true);
    }

    public void setVisible(boolean visible) {
      boardTable.setVisible(visible);
      bar.setVisible(visible);
    }

    public void reset() {
      setVisible(false);
    }
}
