package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import io.github.unisim.GameState;
import io.github.unisim.event.Event;
import io.github.unisim.world.World;

public class EventBar {
    private ShapeActor bar;
    private Table eventsTable = new Table();
    private Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    //cells
    private World world;
    private Event currentEvent;

    private Label eventNameLabel;
    private Label eventDescriptionLabel;
    private Label eventImplicationsLabel;

    private Cell<Label> eventNameLabelCell;
    private Cell<Label> eventDescriptionLabelCell;
    private Cell<Label> eventImplicationsCell;

    public EventBar(Stage stage, World world) {

        // CHANGE (JUST FOR TESTING)
        currentEvent = new Event("Test event", "this will be the event description");

        this.world = world;

        // do buttons and stuff
        eventNameLabel = new Label(currentEvent.name, skin); // CHANGE when eventManager implemented
        eventDescriptionLabel = new Label(currentEvent.description, skin);
        eventDescriptionLabel.setWrap(true);
        eventImplicationsLabel = new Label("uh oh", skin);

        eventsTable.center().center();
        eventNameLabelCell = eventsTable.add(eventNameLabel).align(Align.center);
        eventDescriptionLabelCell = eventsTable.add(eventDescriptionLabel).align(Align.center);
        eventsTable.row();
        eventImplicationsCell = eventsTable.add(eventImplicationsLabel);

        bar = new ShapeActor(GameState.UISecondaryColour);
        stage.addActor(bar);
        stage.addActor(eventsTable);
    }

    public void update() {
        // CHANGE when eventManager implemented
        // currentEvent = ???

    }

    public void resize(int width, int height) {
        float barWidth = height * 0.29f;
        bar.setBounds(width - barWidth, height * 0.75f, barWidth, height * 0.20f);
        eventsTable.setBounds(width - barWidth, height * 0.75f, barWidth, height * 0.20f);


        // look at info bar for other stuff
        eventNameLabel.setFontScale(height * 0.001f);
        eventDescriptionLabel.setFontScale(height * 0.001f);
        eventImplicationsLabel.setFontScale(height * 0.001f);

        eventNameLabelCell.width(barWidth * 0.9f / 2f).height(height * 0.10f);
        eventDescriptionLabelCell.width(barWidth * 0.9f / 2f).height(height * 0.10f);
        eventImplicationsCell.width(barWidth * 0.9f / 2f).height(height * 0.10f);

        eventsTable.setDebug(true);
    }

    public void reset() {
        // reset events
    }
}
