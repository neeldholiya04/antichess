package com.antichess.model;

public class Piece {
    private final String color;
    private final String symbol;
    private final String type;

    public Piece(String color, String symbol, String type) {
        this.color = color;
        this.symbol = symbol;
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return symbol;
    }
}