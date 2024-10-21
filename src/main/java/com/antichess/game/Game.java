package com.antichess.game;

import com.antichess.model.Board;
import com.antichess.model.Piece;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
    private final Board board;
    private String currentPlayer;
    private final Scanner scanner;

    public Game() {
        board = new Board();
        currentPlayer = "white";
        scanner = new Scanner(System.in);
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer.equals("white") ? "black" : "white";
    }

    private int[] parseMove(String move) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile("([A-H])([1-8]) ([A-H])([1-8])", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(move);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid move format. Use 'A2 B4' format.");
        }
        return new int[]{
            Integer.parseInt(matcher.group(2)) - 1,
            matcher.group(1).toUpperCase().charAt(0) - 'A',
            Integer.parseInt(matcher.group(4)) - 1,
            matcher.group(3).toUpperCase().charAt(0) - 'A'
        };
    }

    private boolean makeMove(String move) {
        try {
            int[] positions = parseMove(move);
            int startRow = positions[0], startCol = positions[1], endRow = positions[2], endCol = positions[3];

            if (!board.isValidMove(startRow, startCol, endRow, endCol, currentPlayer)) {
                System.out.println("Invalid move. Try again.");
                return false;
            }

            boolean hasCaptures = board.hasCaptures(currentPlayer);
            Piece targetPiece = board.getPiece(endRow, endCol);

            if (hasCaptures && targetPiece == null) {
                System.out.println("You must capture a piece when possible.");
                return false;
            }

            board.movePiece(startRow, startCol, endRow, endCol);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private String checkWin() {
        int whitePieces = 0, blackPieces = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null) {
                    if (piece.getColor().equals("white")) {
                        whitePieces++;
                    } else {
                        blackPieces++;
                    }
                }
            }
        }

        if (whitePieces == 0) {
            return "white";
        } else if (blackPieces == 0) {
            return "black";
        }
        return null;
    }

    public void play() {
        while (true) {
            board.display();
            System.out.println("\n" + currentPlayer.substring(0, 1).toUpperCase() + currentPlayer.substring(1) + "'s turn");

            List<String> validMoves = board.getValidMoves(currentPlayer);
            if (validMoves.isEmpty()) {
                System.out.println(currentPlayer.substring(0, 1).toUpperCase() + currentPlayer.substring(1) + " has no valid moves. " +
                        (currentPlayer.equals("white") ? "Black" : "White") + " wins!");
                break;
            }

            System.out.print("Enter 'move A2 B4', 'display', 'hint', or 'quit': ");
            String action = scanner.nextLine().toLowerCase();

            if (action.equals("quit")) {
                System.out.println(currentPlayer.substring(0, 1).toUpperCase() + currentPlayer.substring(1) + " quits. " +
                        (currentPlayer.equals("white") ? "Black" : "White") + " wins!");
                break;
            } else if (action.equals("display")) {
                continue;
            } else if (action.equals("hint")) {
                System.out.println("Valid moves: " + String.join(", ", validMoves));
                continue;
            } else if (action.startsWith("move ")) {
                if (makeMove(action.substring(5))) {
                    String winner = checkWin();
                    if (winner != null) {
                        board.display();
                        System.out.println("\n" + winner.substring(0, 1).toUpperCase() + winner.substring(1) + " wins!");
                        break;
                    }
                    switchPlayer();
                }
            } else {
                System.out.println("Invalid action. Try again.");
            }
        }
        scanner.close();
    }
}