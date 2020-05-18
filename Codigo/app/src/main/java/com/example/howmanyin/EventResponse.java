package com.example.howmanyin;

public class EventResponse {

    String state;

    String env;

    EventRequest eventRes;

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

    public EventRequest getEventRes() {
        return eventRes;
    }

    public void setEventRes(EventRequest eventRes) {
        this.eventRes = eventRes;
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
                ", eventRes=" + eventRes +
                ", group='" + group + '\'' +
                '}';
    }
}
