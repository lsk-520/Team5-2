package io.github.unisim.event;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.unisim.Timer;
import io.github.unisim.building.BuildingType;


/**
 * Represents an event affecting gameplay for the player.
 */
public class Event {

    public String name;
    public String description;
    public Image icon;

    // The initial score factor to change score by, and the value by which a chosen
    // building type will increase the score by for new placements

    /**The initial factor to change score by.*/
    private float scoreFactor;
    private BuildingType buildingType;
    /**The value by which a chosen building type will increase the score by for new placements.*/
    private int buildingTypeScoreIncrease;

    // Fields for having score decrement per certain time increment - scoreFactor
    // will be used as the value to change by
    private float tickPeriod;
    private float lastTickPeriod = 0;
    private boolean isTickEvent;
    public boolean finished = false;

    // All events last 30 seconds
    private Timer timer = new Timer(30_000f);

    /**A non-ticking event.
     *
     * <p>Score changes by a constant amount at the start of the event. Score can be changed by building placement.</p>
     *
     * @param name The name of the event.
     * @param description A short description of the event and its implications to the player.
     * @param icon The icon of the event to be displayed on the event bar.
     * @param scoreFactor The factor score changes by at the start of the event.
     * @param buildingTypeScoreIncrease The factor score then changes by when a building is placed.
     * @param buildingType The type of building that must be placed.
     */
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

    /**A ticking event - score changes incrementally over time.
     *
     * @param name The name of the event.
     * @param description A short description of the event and its implications.
     * @param icon The icon of the event to be displayed.
     * @param scoreFactor The factor score changes by every tickPeriod for duration of the event.
     * @param tickPeriod I think we can get rid of this
     */
    public Event(String name, String description, Image icon, int scoreFactor, float tickPeriod) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.scoreFactor = scoreFactor;
        this.tickPeriod = tickPeriod;
        this.isTickEvent = true;
    }

    /**The timer of the event.
     * <p>Handles the ui of the timer of the event, and the score changing each tick.</p>
     * @return The amount the score should change by.*/
    public float tick() {
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
