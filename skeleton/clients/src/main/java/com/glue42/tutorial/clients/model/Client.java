package com.glue42.tutorial.clients.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.glue42.tutorial.clients.model.ClientDetails;

import java.util.List;

public class Client {

    @JsonProperty("FullName")
    private String fullName;
    @JsonProperty("PID")
    private String pid;
    @JsonProperty("GID")
    private String gid;
    @JsonProperty("Manager")
    private String manager;
    @JsonProperty("Details")
    private List<ClientDetails> details;
    @JsonProperty("PhoneNumber")
    private String phoneNumber;

    public String getFullName() {
        return fullName;
    }

    public String getPid() {
        return pid;
    }

    public String getGid() {
        return gid;
    }

    public String getManager() {
        return manager;
    }

    public List<ClientDetails> getDetails() {
        return details;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
