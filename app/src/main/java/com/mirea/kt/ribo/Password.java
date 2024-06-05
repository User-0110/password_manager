package com.mirea.kt.ribo;

public class Password {
    private String service;
    private String login;
    private String password;

    public Password(String service, String login, String password) {
        this.service = service;
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getService() {
        return service;
    }
}