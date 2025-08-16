package com.auction.util;

import jakarta.enterprise.context.SessionScoped;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SessionScoped
public class UserSessionManager implements Serializable {
    private String loggedInUser;
    private static final Map<String, String> users = new HashMap<>();

    static {
        users.put("admin", "admin");
    }

    public boolean login(String username, String password) {
        String storedPassword = users.get(username);
        if (storedPassword != null && storedPassword.equals(password)) {
            this.loggedInUser = username;
            return true;
        }
        return false;
    }

    public boolean register(String username, String password) {

        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, password);
        return true;
    }

    public void logout() {
        this.loggedInUser = null;
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }
}
