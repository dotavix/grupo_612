package com.example.becareful;

public class EventResponse {

    String state;

    String env;

    Event event;

    public class Event {

        String id ;
        String dni;
        String type_events;
        String state;
        String description;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDni() {
            return dni;
        }

        public void setDni(String dni) {
            this.dni = dni;
        }

        public String getType_events() {
            return type_events;
        }

        public void setType_events(String type_events) {
            this.type_events = type_events;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

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


    @Override
    public String toString() {
        return "EventResponse{" +
                "state='" + state + '\'' +
                ", env='" + env + '\'' +
                ", event=" + event +
                '}';
    }
}
