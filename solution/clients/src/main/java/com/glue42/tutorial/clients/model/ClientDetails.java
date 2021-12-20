package com.glue42.tutorial.clients.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientDetails {

    @JsonProperty("Symbol")
    String symbol;
    @JsonProperty("Description")
    String description;
    @JsonProperty("Bid")
    Double bid;
    @JsonProperty("Ask")
    Double ask;

    public String getSymbol() {
        return symbol;
    }

    public String getDescription() {
        return description;
    }

    public Double getBid() {
        return bid;
    }

    public Double getAsk() {
        return ask;
    }
}
