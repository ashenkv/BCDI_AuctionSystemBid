package com.auction.ejb;

import jakarta.ejb.Stateful;

@Stateful
public class ClientSessionBean {
    private String username;
    private String email;

    public void login(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }
}