package io.github.unisim.event;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.unisim.Timer;
import io.github.unisim.building.BuildingType;

public class Event {

    public String name;
    public String description;
    public Image icon;

    // The initial score factor to change score by, and the value by which a chosen
    // building type will increase the score by for new placements
    private int scoreFactor;
    private BuildingType buildingType;
    private int buildingTypeScoreIncrease;

    // Fields for having score decrement per certain time increment - scoreFactor
    // will be used as the value to change by
    private float tickPeriod;
    private float lastTickPeriod = 0;
    private boolean isTickEvent;
    public boolean finished = false;

    // All events last 30 seconds
    private Timer timer = new Timer(30_000f);

    // IMPLEMENT DOCSTRING PLS!!
    // Non-ticking event, where scoreFactor changes score initially (as factor), and new buildings of type specified
    // update the score by buildingTypeScoreIncrease
    public Event(String name, String description, Image icon, int scoreFactor, int buildingTypeScoreIncrease,
                 BuildingType buildingType) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.scoreFactor = scoreFactor;
        this.buildingType = buildingType;
        this.buildingTypeScoreIncrease = buildingTypeScoreIncrease;
        this.isTickEvent = false;
    }

    // IMPLEMENT DOCSTRING PLS!!
    // Ticking event, where scoreFactor changes the score incrementally (as increment) over a certain period
    // of ms (tickPeriod)
    public Event(String name, String description, Image icon, int scoreFactor, float tickPeriod) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.scoreFactor = scoreFactor;
        this.tickPeriod = tickPeriod;
        this.isTickEvent = true;
    }

    public int tick() {
        boolean changeScore = false;

        finished = !timer.tick(Gdx.graphics.getDeltaTime() * 1000f);
        if (isTickEvent) {
            float currentTimeInterval = (30_000f % tickPeriod);
            if (currentTimeInterval < lastTickPeriod) { changeScore = true; }
            lastTickPeriod = currentTimeInterval;
        }
        if (finished) {
            timer.reset();
        }

        return changeScore ? scoreFactor : 0;
    }

    public String getRemainingTime(){
        return timer.getRemainingTime();
    }

    public void reset() {
        timer.reset();
        finished = false;
        lastTickPeriod = 0;
    }
}
