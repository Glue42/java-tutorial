package com.glue42.tutorial.portfolio;

public class PortfolioEntry {

    String symbol;
    String description;
    String bid;
    String ask;

    public PortfolioEntry(String symbol, String description, String bid, String ask) {
        this.symbol = symbol;
        this.description = description;
        this.bid = bid;
        this.ask = ask;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDescription() {
        return description;
    }

    public String getBid() {
        return bid;
    }

    public String getAsk() {
        return ask;
    }
}
