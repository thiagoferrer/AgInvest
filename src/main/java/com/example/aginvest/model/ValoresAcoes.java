package com.example.aginvest.model;

public class ValoresAcoes {
    private double regularMarketPrice;
    private double regularMarketDayHigh;
    private String symbol;
    private double regularMarketChange;

    public double getRegularMarketChange() {
        return regularMarketChange;
    }

    public void setRegularMarketChange(double regularMarketChange) {
        this.regularMarketChange = regularMarketChange;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getRegularMarketDayHigh() {
        return regularMarketDayHigh;
    }

    public void setRegularMarketDayHigh(double regularMarketDayHigh) {
        this.regularMarketDayHigh = regularMarketDayHigh;
    }

    public double getRegularMarketPrice() {
        return regularMarketPrice;
    }

    public void setRegularMarketPrice(double regularMarketPrice) {
        this.regularMarketPrice = regularMarketPrice;
    }
}
