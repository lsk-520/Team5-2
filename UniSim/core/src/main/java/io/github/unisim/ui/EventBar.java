package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import io.github.unisim.GameState;
import io.github.unisim.event.Event;
import io.github.unisim.world.World;

/**
 * Create an events bar with events information.
 */
public class EventBar {
    private ShapeActor bar;
    private Table eventsTable = new Table();
    private Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

    private World world;
    private Event currentEvent;

    private Label eventNameLabel;
    private Label eventDescriptionLabel;

    private Cell<Label> eventNameLabelCell;
    private Cell<Label> eventDescriptionLabelCell;
    private Cell<Image> eventIconCell;

  /**
   * Create a new eventBar and add its components onto the stage.
   *
   * @param stage - The stage to draw the eventBar onto.
   * @param world - The world to access the events from.
   */
  public EventBar(Stage stage, World world) {

        // initial event
        currentEvent = new Event("Plague",
            "event description",
            new Image(new Texture(Gdx.files.internal("buildings/pharmacy.png"))),
            2,
            1000f);

        this.world = world;

        // Set up ui elements
        eventNameLabel = new Label(currentEvent.name, skin);
        eventDescriptionLabel = new Label(currentEvent.description, skin);
        eventDescriptionLabel.setWrap(true);

        eventsTable.center().center();
        eventIconCell = eventsTable.add(currentEvent.icon);
        eventNameLabelCell = eventsTable.add(eventNameLabel).align(Align.center);
        eventDescriptionLabelCell = eventsTable.add(eventDescriptionLabel).align(Align.center);
        eventsTable.row();

        bar = new ShapeActor(GameState.UISecondaryColour);
        stage.addActor(bar);
        stage.addActor(eventsTable);
        bar.setVisible(false);
        eventsTable.setVisible(false);
    }

  /**
   * Called when the UI needs to be updated, usually on every frame.
   */
    public void update() {
        if (world.getCurrentEvent() != null && currentEvent == null){
            setVisible(true);
            currentEvent = world.getCurrentEvent();
        }
        else if (world.getCurrentEvent() == null){
            currentEvent = null;
            setVisible(false);
        }

        if (currentEvent != null) {
            currentEvent = world.getCurrentEvent();
            eventNameLabel.setText(currentEvent.name + "\n" + currentEvent.getRemainingTime());
            eventNameLabel.setAlignment(Align.center);
            eventDescriptionLabel.setText(currentEvent.description);
            eventDescriptionLabel.setAlignment(Align.center);

            eventNameLabelCell.setActor(eventNameLabel);
            eventDescriptionLabelCell.setActor(eventDescriptionLabel);
            eventIconCell.setActor(currentEvent.icon);
        }
    }

  /**
   * Update the bounds of the background & table actors to fit the new size of the screen.

   * @param width - The new width of the screen in pixels.
   * @param height - The enw height of the screen in pixels.
   */
    public void resize(int width, int height) {
        float barWidth = height * 0.6f;
        float barHeight = (height * 0.10f);
        bar.setBounds(width - barWidth, height-barHeight-(height * 0.05f), barWidth, barHeight);
        eventsTable.setBounds(width - barWidth, height-barHeight-(height * 0.05f), barWidth, barHeight);


        // look at info bar for other stuff
        eventNameLabel.setFontScale(height * 0.002f);
        eventDescriptionLabel.setFontScale(height * 0.0015f);
        //eventImplicationsLabel.setFontScale(height * 0.001f);

        eventIconCell.width(barWidth * 0.20f).height(height * 0.10f);
        eventNameLabelCell.width(barWidth * 0.4f / 2f).height(height * 0.10f);
        eventDescriptionLabelCell.width(barWidth * 1.2f / 2f).height(height * 0.10f);
        //eventImplicationsCell.width(barWidth * 0.9f / 2f).height(height * 0.10f);

        //eventsTable.setDebug(true);
    }

    public void reset() {
        setVisible(false);
    }

    public void setVisible(boolean visible) {
        eventsTable.setVisible(visible);
        bar.setVisible(visible);
    }
}
