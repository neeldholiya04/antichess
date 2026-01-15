package com.antichess.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    @DisplayName("Board should be initialized with correct setup")
    void testBoardInitialization() {
        for (int col = 0; col < 8; col++) {
            Piece piece = board.getPiece(1, col);
            assertNotNull(piece);
            assertEquals("white", piece.getColor());
            assertEquals("pawn", piece.getType());
        }

        for (int col = 0; col < 8; col++) {
            Piece piece = board.getPiece(6, col);
            assertNotNull(piece);
            assertEquals("black", piece.getColor());
            assertEquals("pawn", piece.getType());
        }

        Piece whiteKing = board.getPiece(0, 4);
        assertNotNull(whiteKing);
        assertEquals("king", whiteKing.getType());
        assertEquals("white", whiteKing.getColor());

        Piece blackQueen = board.getPiece(7, 3);
        assertNotNull(blackQueen);
        assertEquals("queen", blackQueen.getType());
        assertEquals("black", blackQueen.getColor());

        assertNull(board.getPiece(3, 3));
        assertNull(board.getPiece(4, 4));
    }

    @Test
    @DisplayName("Move piece should update board correctly")
    void testMovePiece() {
        Piece piece = board.getPiece(1, 0);
        assertNotNull(piece);

        board.movePiece(1, 0, 3, 0);

        assertNull(board.getPiece(1, 0));
        assertNotNull(board.getPiece(3, 0));
        assertEquals(piece, board.getPiece(3, 0));
    }

    @Test
    @DisplayName("Invalid move - piece doesn't belong to current player")
    void testInvalidMoveWrongPlayer() {
        assertFalse(board.isValidMove(6, 0, 5, 0, "white"));
    }

    @Test
    @DisplayName("Invalid move - no piece at start position")
    void testInvalidMoveNoPiece() {
        assertFalse(board.isValidMove(3, 3, 4, 4, "white"));
    }

    @Test
    @DisplayName("Invalid move - destination occupied by same color")
    void testInvalidMoveSameColorDestination() {
        assertFalse(board.isValidMove(0, 0, 1, 0, "white"));
    }

    @Test
    @DisplayName("Valid pawn move - one square forward")
    void testValidPawnMoveOneSquare() {
        assertTrue(board.isValidMove(1, 0, 2, 0, "white"));
        assertTrue(board.isValidMove(6, 0, 5, 0, "black"));
    }

    @Test
    @DisplayName("Valid pawn move - two squares forward from start")
    void testValidPawnMoveTwoSquares() {
        assertTrue(board.isValidMove(1, 0, 3, 0, "white"));
        assertTrue(board.isValidMove(6, 0, 4, 0, "black"));
    }

    @Test
    @DisplayName("Invalid pawn move - two squares not from start")
    void testInvalidPawnMoveTwoSquaresNotFromStart() {
        board.movePiece(1, 0, 2, 0);
        assertFalse(board.isValidMove(2, 0, 4, 0, "white"));
    }

    @Test
    @DisplayName("Valid pawn capture - diagonal")
    void testValidPawnCapture() {
        board.movePiece(1, 1, 3, 1);
        board.movePiece(6, 2, 4, 2);

        assertTrue(board.isValidMove(3, 1, 4, 2, "white"));
    }

    @Test
    @DisplayName("Invalid pawn move - diagonal without capture")
    void testInvalidPawnMoveDiagonalNoCapture() {
        assertFalse(board.isValidMove(1, 0, 2, 1, "white"));
    }

    @Test
    @DisplayName("Valid knight move - L-shape")
    void testValidKnightMove() {
        assertTrue(board.isValidMove(0, 1, 2, 0, "white"));
        assertTrue(board.isValidMove(0, 1, 2, 2, "white"));
    }

    @Test
    @DisplayName("Invalid knight move - not L-shape")
    void testInvalidKnightMove() {
        assertFalse(board.isValidMove(0, 1, 2, 1, "white"));
    }

    @Test
    @DisplayName("Valid rook move - vertical")
    void testValidRookMoveVertical() {
        board.movePiece(1, 0, 3, 0);
        assertTrue(board.isValidMove(0, 0, 2, 0, "white"));
    }

    @Test
    @DisplayName("Valid rook move - horizontal")
    void testValidRookMoveHorizontal() {
        board.movePiece(0, 1, 2, 2);
        assertTrue(board.isValidMove(0, 0, 0, 1, "white"));
    }

    @Test
    @DisplayName("Invalid rook move - diagonal")
    void testInvalidRookMoveDiagonal() {
        board.movePiece(1, 0, 3, 0);
        assertFalse(board.isValidMove(0, 0, 2, 2, "white"));
    }

    @Test
    @DisplayName("Invalid rook move - path blocked")
    void testInvalidRookMoveBlocked() {
        assertFalse(board.isValidMove(0, 0, 5, 0, "white"));
    }

    @Test
    @DisplayName("Valid bishop move - diagonal")
    void testValidBishopMove() {
        board.movePiece(1, 3, 3, 3);
        assertTrue(board.isValidMove(0, 2, 2, 4, "white"));
    }

    @Test
    @DisplayName("Invalid bishop move - not diagonal")
    void testInvalidBishopMove() {
        board.movePiece(1, 2, 3, 2);
        assertFalse(board.isValidMove(0, 2, 2, 2, "white"));
    }

    @Test
    @DisplayName("Invalid bishop move - path blocked")
    void testInvalidBishopMoveBlocked() {
        assertFalse(board.isValidMove(0, 2, 3, 5, "white"));
    }

    @Test
    @DisplayName("Valid queen move - horizontal, vertical, and diagonal")
    void testValidQueenMove() {
        board.movePiece(1, 3, 3, 3);
        assertTrue(board.isValidMove(0, 3, 2, 3, "white"));
        board.movePiece(0, 3, 2, 3);
        assertTrue(board.isValidMove(2, 3, 2, 5, "white"));
        board.movePiece(2, 3, 2, 5);
        assertTrue(board.isValidMove(2, 5, 4, 7, "white"));
    }

    @Test
    @DisplayName("Invalid queen move - not straight or diagonal")
    void testInvalidQueenMove() {
        board.movePiece(1, 3, 3, 3);
        assertFalse(board.isValidMove(0, 3, 2, 4, "white"));
    }

    @Test
    @DisplayName("Valid king move - one square any direction")
    void testValidKingMove() {
        board.movePiece(1, 4, 3, 4);
        assertTrue(board.isValidMove(0, 4, 1, 4, "white"));
        board.movePiece(0, 4, 1, 4);
        assertTrue(board.isValidMove(1, 4, 2, 5, "white"));
    }

    @Test
    @DisplayName("Invalid king move - more than one square")
    void testInvalidKingMove() {
        board.movePiece(1, 4, 3, 4);
        assertFalse(board.isValidMove(0, 4, 2, 4, "white"));
    }

    @Test
    @DisplayName("hasCaptures should return true when captures available")
    void testHasCapturesTrue() {
        board.movePiece(1, 4, 3, 4);
        board.movePiece(6, 5, 4, 5);

        assertTrue(board.hasCaptures("white"));
    }

    @Test
    @DisplayName("hasCaptures should return false when no captures available")
    void testHasCapturesFalse() {
        assertFalse(board.hasCaptures("white"));
        assertFalse(board.hasCaptures("black"));
    }

    @Test
    @DisplayName("getValidMoves should return all valid moves for player")
    void testGetValidMoves() {
        List<String> whiteMoves = board.getValidMoves("white");

        assertFalse(whiteMoves.isEmpty());
        assertTrue(whiteMoves.contains("A2 A3"));
        assertTrue(whiteMoves.contains("A2 A4"));
        assertTrue(whiteMoves.contains("B1 A3"));
        assertTrue(whiteMoves.contains("B1 C3"));
    }

    @Test
    @DisplayName("getValidMoves should only return capture moves when captures available")
    void testGetValidMovesWithCapturesAvailable() {
        board.movePiece(1, 4, 3, 4);
        board.movePiece(6, 5, 4, 5);

        List<String> whiteMoves = board.getValidMoves("white");

        assertFalse(whiteMoves.isEmpty(), "Moves list should not be empty");

        for (String move : whiteMoves) {
            String[] parts = move.split(" ");
            int endCol = parts[1].charAt(0) - 'A';
            int endRow = Integer.parseInt(parts[1].substring(1)) - 1;

            assertNotNull(board.getPiece(endRow, endCol), "Move " + move + " must be a capture");
        }
    }

    @Test
    @DisplayName("Board should handle edge coordinates correctly")
    void testBoardEdges() {
        assertNotNull(board.getPiece(0, 0));
        assertNotNull(board.getPiece(0, 7));
        assertNotNull(board.getPiece(7, 0));
        assertNotNull(board.getPiece(7, 7));
    }
}