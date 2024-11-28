package io.github.unisim.event;

import java.util.ArrayList;

public class EventManager {
    private ArrayList<Event> events = new ArrayList<>();
    private int currentEvent;

    public EventManager() {
        currentEvent = 0;
    }

    public Event getCurrentEvent() {
        return events.get(currentEvent);
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void nextEvent() {
        if (events.size() > currentEvent) {
            // CHANGE - need to decide what handles the events changing
            currentEvent = events.size() + 1;
        }
    }
}
