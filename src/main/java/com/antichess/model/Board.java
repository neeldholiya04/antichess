package com.antichess.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Piece[][] grid;

    public Board() {
        grid = new Piece[8][8];
        setupBoard();
    }

    private void setupBoard() {
        // Set up pawns
        for (int col = 0; col < 8; col++) {
            grid[1][col] = new Piece("white", "♙", "pawn");
            grid[6][col] = new Piece("black", "♟", "pawn");
        }

        // Set up other pieces
        String[] pieceOrder = {"rook", "knight", "bishop", "queen", "king", "bishop", "knight", "rook"};
        String[] whiteSymbols = {"♖", "♘", "♗", "♕", "♔", "♗", "♘", "♖"};
        String[] blackSymbols = {"♜", "♞", "♝", "♛", "♚", "♝", "♞", "♜"};

        for (int col = 0; col < 8; col++) {
            grid[0][col] = new Piece("white", whiteSymbols[col], pieceOrder[col]);
            grid[7][col] = new Piece("black", blackSymbols[col], pieceOrder[col]);
        }
    }

    public void display() {
    System.out.println("   A   B   C   D   E   F   G   H");
    for (int row = 7; row >= 0; row--) {
        System.out.printf("%d  ", row + 1); // Print row number with padding
        for (int col = 0; col < 8; col++) {
            Piece piece = grid[row][col];
            if (piece != null) {
                System.out.printf(" %s  ", piece); // Each piece gets uniform space
            } else {
                // Uniform space for empty squares
                System.out.printf(" %s  ", ((row + col) % 2 == 0) ? "·" : "□");
            }
        }
        System.out.printf(" %d\n", row + 1); // Print row number at the end
    }
    System.out.println("   A   B   C   D   E   F   G   H");
}


    public Piece getPiece(int row, int col) {
        return grid[row][col];
    }

    public void movePiece(int startRow, int startCol, int endRow, int endCol) {
        grid[endRow][endCol] = grid[startRow][startCol];
        grid[startRow][startCol] = null;
    }

    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, String currentPlayer) {
        Piece piece = getPiece(startRow, startCol);
        if (piece == null || !piece.getColor().equals(currentPlayer)) {
            return false;
        }

        // Check if the move is within the board
        if (endRow < 0 || endRow > 7 || endCol < 0 || endCol > 7) {
            return false;
        }

        // Check if the destination is not occupied by a piece of the same color
        Piece destPiece = getPiece(endRow, endCol);
        if (destPiece != null && destPiece.getColor().equals(currentPlayer)) {
            return false;
        }

        // Validate move based on piece type
        switch (piece.getType()) {
            case "pawn":
                return isValidPawnMove(startRow, startCol, endRow, endCol, currentPlayer);
            case "rook":
                return isValidRookMove(startRow, startCol, endRow, endCol);
            case "knight":
                return isValidKnightMove(startRow, startCol, endRow, endCol);
            case "bishop":
                return isValidBishopMove(startRow, startCol, endRow, endCol);
            case "queen":
                return isValidQueenMove(startRow, startCol, endRow, endCol);
            case "king":
                return isValidKingMove(startRow, startCol, endRow, endCol);
            default:
                return false;
        }
    }

    private boolean isValidPawnMove(int startRow, int startCol, int endRow, int endCol, String currentPlayer) {
        int direction = currentPlayer.equals("white") ? 1 : -1;
        int moveDistance = endRow - startRow;

        // Move forward
        if (startCol == endCol && getPiece(endRow, endCol) == null) {
            if (moveDistance == direction) {
                return true;
            }
            // First move can be two squares
            if ((startRow == 1 && currentPlayer.equals("white") && moveDistance == 2) ||
                (startRow == 6 && currentPlayer.equals("black") && moveDistance == -2)) {
                return getPiece(startRow + direction, startCol) == null;
            }
        }

        // Capture diagonally
        return Math.abs(startCol - endCol) == 1 && moveDistance == direction && getPiece(endRow, endCol) != null;
    }

    private boolean isValidRookMove(int startRow, int startCol, int endRow, int endCol) {
        if (startRow != endRow && startCol != endCol) {
            return false;
        }
        return isPathClear(startRow, startCol, endRow, endCol);
    }

    private boolean isValidKnightMove(int startRow, int startCol, int endRow, int endCol) {
        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endCol - startCol);
        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }

    private boolean isValidBishopMove(int startRow, int startCol, int endRow, int endCol) {
        if (Math.abs(endRow - startRow) != Math.abs(endCol - startCol)) {
            return false;
        }
        return isPathClear(startRow, startCol, endRow, endCol);
    }

    private boolean isValidQueenMove(int startRow, int startCol, int endRow, int endCol) {
        if (startRow == endRow || startCol == endCol || Math.abs(endRow - startRow) == Math.abs(endCol - startCol)) {
            return isPathClear(startRow, startCol, endRow, endCol);
        }
        return false;
    }

    private boolean isValidKingMove(int startRow, int startCol, int endRow, int endCol) {
        return Math.abs(endRow - startRow) <= 1 && Math.abs(endCol - startCol) <= 1;
    }

    private boolean isPathClear(int startRow, int startCol, int endRow, int endCol) {
        int rowStep = Integer.compare(endRow, startRow);
        int colStep = Integer.compare(endCol, startCol);

        int currentRow = startRow + rowStep;
        int currentCol = startCol + colStep;

        while (currentRow != endRow || currentCol != endCol) {
            if (getPiece(currentRow, currentCol) != null) {
                return false;
            }
            currentRow += rowStep;
            currentCol += colStep;
        }

        return true;
    }

    public boolean hasCaptures(String player) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece != null && piece.getColor().equals(player)) {
                    if (hasCapture(row, col, player)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean hasCapture(int startRow, int startCol, String player) {
        for (int endRow = 0; endRow < 8; endRow++) {
            for (int endCol = 0; endCol < 8; endCol++) {
                Piece targetPiece = getPiece(endRow, endCol);
                if (targetPiece != null && !targetPiece.getColor().equals(player)) {
                    if (isValidMove(startRow, startCol, endRow, endCol, player)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public List<String> getValidMoves(String player) {
        List<String> validMoves = new ArrayList<>();
        for (int startRow = 0; startRow < 8; startRow++) {
            for (int startCol = 0; startCol < 8; startCol++) {
                Piece piece = getPiece(startRow, startCol);
                if (piece != null && piece.getColor().equals(player)) {
                    for (int endRow = 0; endRow < 8; endRow++) {
                        for (int endCol = 0; endCol < 8; endCol++) {
                            if (isValidMove(startRow, startCol, endRow, endCol, player)) {
                                validMoves.add(String.format("%c%d %c%d",
                                    (char)('A' + startCol), startRow + 1,
                                    (char)('A' + endCol), endRow + 1));
                            }
                        }
                    }
                }
            }
        }
        return validMoves;
    }
}