# AntiChess Game

## Table of Contents
- [Introduction](#introduction)
- [Game Features](#game-features)
- [Gameplay Rules](#gameplay-rules)
- [Project Structure](#project-structure)
- [Classes Overview](#classes-overview)
- [How to Run](#how-to-run)
- [How to Play](#how-to-play)

## Introduction
AntiChess is a variant of chess where the goal is to lose all your pieces instead of winning by checkmating the opponent. The game forces you to make captures when possible, which adds a strategic twist to the traditional chess mechanics.

This Java-based project simulates the AntiChess game, allowing two players to play against each other in the console. The game follows standard chess piece movements with the additional anti-chess rules applied.

## Game Features
- Full implementation of AntiChess rules, where the objective is to lose all pieces.
- Custom chessboard display using Unicode symbols.
- Supports all standard chess piece moves (pawns, rooks, knights, bishops, queens, kings).
- Enforces mandatory captures when available.
- Detects game over conditions such as a player having no valid moves or losing all pieces.
- Provides hints for valid moves during the game.

## Gameplay Rules
- Each player takes turns moving one of their pieces.
- When a player can capture an opponent’s piece, the capture is mandatory.
- The game ends when a player either:
  - Loses all their pieces (they win).
  - Has no valid moves left (they lose).

## Project Structure

The project consists of the following packages and classes:

```bash
src
├── com
│   └── antichess
│       ├── App.java               # Main entry point of the game
│       ├── game
│       │   └── Game.java           # Handles game flow, user inputs, and move validation
│       └── model
│           ├── Board.java          # Manages the chessboard, pieces, and movement logic
│           └── Piece.java          # Represents individual pieces and their properties
└── resources
    └── README.md                   # Documentation (this file)
```

### Classes Overview

- **App.java**: The main class responsible for launching the game. It instantiates the `Game` class and starts the gameplay loop.
- **Game.java**: Manages the main game loop, handles player input, switches players, and checks for game-over conditions.
- **Board.java**: Contains the logic for setting up the board, moving pieces, validating moves, and detecting captures. It also manages the display of the chessboard.
- **Piece.java**: Represents a chess piece, with properties like color, type (e.g., rook, pawn), and symbol. The `Piece` class defines how each piece behaves and interacts on the board.

## How to Run

1. **Clone the Repository:**
    
    ```bash
    git clone https://github.com/neeldholiya04/antichess.git
    
    ```
    
2. **Compile the Project:**
Navigate to the project root directory, where `App.java` is located. Run the following command:
    
    ```bash
    javac -d bin src/com/antichess/*.java src/com/antichess/game/*.java src/com/antichess/model/*.java
    
    ```
    
3. **Run the Game:**
Once compiled, run the `App` class to start the game:
    
    ```bash
    java -cp bin com.antichess.App
    
    ```
## How to Play

1. **Start the game**: After running the program, the chessboard will be displayed, and it will prompt the current player (White or Black) to make a move.
2. **Enter commands**: Players can enter one of the following commands:
    - `move A2 B3`: Move a piece from position A2 to B3.
    - `display`: Re-display the current state of the chessboard.
    - `hint`: Display all valid moves for the current player.
    - `quit`: Quit the game.
3. **Move validation**: The game validates whether the move is allowed according to the piece's rules. If there are mandatory captures, the game will not accept non-capturing moves.
4. **Game end**: The game ends when:
    - One player has no pieces left (they win).
    - One player has no valid moves (the other player wins).
5. **Game Loop**: The game alternates turns between players until one of the above end conditions is met.

### Example Commands

- Move a pawn: `move A2 A3`
- Move a knight: `move B1 C3`
- Display valid moves: `hint`
- Quit the game: `quit`