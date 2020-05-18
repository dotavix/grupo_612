package com.example.howmanyin;

public class RegistroResponse {

    private String env ;

    private String token;

    private String state;

    private String msg;

    public RegistroResponse(String env, String token, String state, String msg) {
        this.env = env;
        this.token = token;
        this.state = state;
        this.msg = msg;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "RegistroResponse{" +
                "env='" + env + '\'' +
                ", token='" + token + '\'' +
                ", state='" + state + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
