package com.glue42.tutorial.clients.model;

public class Contact {
    private final String clientName;
    private final String phone;

    public Contact(String name, String phone) {
        this.clientName = name;
        this.phone = phone;
    }

    public String getClientName() {
        return clientName;
    }

    public String getPhone() {
        return phone;
    }
}
