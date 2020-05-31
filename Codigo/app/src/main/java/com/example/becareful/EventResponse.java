package com.example.becareful;

public class EventResponse {

    String state;

    String env;

    EventRequest event;

    String group;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public EventRequest getEvent() {
        return event;
    }

    public void setEvent(EventRequest event) {
        this.event = event;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "EventResponse{" +
                "state='" + state + '\'' +
                ", env='" + env + '\'' +
                ", event=" + event +
                ", group='" + group + '\'' +
                '}';
    }
}
