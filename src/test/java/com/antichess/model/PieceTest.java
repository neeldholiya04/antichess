package com.antichess.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PieceTest {

    @Test
    @DisplayName("Piece should be created with correct attributes")
    void testPieceCreation() {
        Piece piece = new Piece("white", "♙", "pawn");

        assertEquals("white", piece.getColor());
        assertEquals("♙", piece.getSymbol());
        assertEquals("pawn", piece.getType());
    }

    @Test
    @DisplayName("Piece toString should return symbol")
    void testPieceToString() {
        Piece piece = new Piece("black", "♜", "rook");
        assertEquals("♜", piece.toString());
    }
}