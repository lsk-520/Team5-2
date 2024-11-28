package io.github.unisim.event;

import com.badlogic.gdx.graphics.Texture;

public class Event {
    //public Texture texture;
    public String name;
    public String description;

    public Event(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
