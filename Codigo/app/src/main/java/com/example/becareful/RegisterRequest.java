package com.example.becareful;

public class RegisterRequest {

    private String name;

    private String lastname;

    private String dni;

    private String password;

    private String email;

    private String env;

    private String commission;

    private String group;

    public RegisterRequest() {

    }

    public String getName() {
        return name;
    }

    public RegisterRequest(String name, String lastname, String dni, String password, String email, String env, String commission, String group) {
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.password = password;
        this.email = email;
        this.env = env;
        this.commission = commission;
        this.group = group;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dni='" + dni + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", env='" + env + '\'' +
                ", commission='" + commission + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
