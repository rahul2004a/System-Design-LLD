# Tic-Tac-Toe Design

## Overview

The Tic-Tac-Toe system is designed to manage the game logic, players, and board efficiently. It supports different types of players, game pieces, and rules while ensuring scalability and maintainability.

## System Requirements

1. **Game Board**: A nxn grid to play the game.
2. **Players**: Two players, each assigned a unique piece type (X or O).
3. **Game Pieces**: Represent the moves made by players.
4. **Game Rules**: Enforce rules for valid moves and determine the winner.
5. **Turn Management**: Alternate turns between players.
6. **Win Conditions**: Check for row, column, or diagonal matches.
7. **Draw Condition**: Detect when the board is full without a winner.

## Class Diagram

```
+---------------------+
|     TicTacToe       |
+---------------------+
| + initializeGame()  |
| + startGame()       |
| + isThereWinner()   |
+---------------------+
        ^
        |
+---------------------+
|       Board         |
+---------------------+
| + printBoard()      |
| + addPiece()        |
| + getBoard()        |
| + getSize()         |
| + getFreeCells()    |
+---------------------+
        ^
        |
+---------------------+
|     Player          |
+---------------------+
| + getName()         |
| + getPiece()        |
+---------------------+
        ^
        |
+---------------------+
|   PlayingPiece      |
+---------------------+
| + getType()         |
+---------------------+
```

## Example Code

### Playing Piece Class
```java
// filepath: src/Modal/PlayingPiece.java
public abstract class PlayingPiece {
    private final String type;

    public PlayingPiece(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

// filepath: src/Modal/PlayingPieceX.java
public class PlayingPieceX extends PlayingPiece {
    public PlayingPieceX() {
        super("X");
    }
}

// filepath: src/Modal/PlayingPieceO.java
public class PlayingPieceO extends PlayingPiece {
    public PlayingPieceO() {
        super("O");
    }
}
```

### Player Class
```java
// filepath: src/Modal/Player.java
public class Player {
    private final String name;
    private final PlayingPiece piece;

    public Player(String name, PlayingPiece piece) {
        this.name = name;
        this.piece = piece;
    }

    public String getName() {
        return name;
    }

    public PlayingPiece getPiece() {
        return piece;
    }
}
```

### Board Class
```java
// filepath: src/Modal/Board.java
import Modal.PlayingPiece;

public class Board {
    private final PlayingPiece[][] grid;
    private final int size;

    public Board(int size) {
        this.size = size;
        this.grid = new PlayingPiece[size][size];
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(grid[i][j] == null ? "-" : grid[i][j].getType());
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public boolean addPiece(int row, int col, PlayingPiece piece) {
        if (row >= 0 && row < size && col >= 0 && col < size && grid[row][col] == null) {
            grid[row][col] = piece;
            return true;
        }
        return false;
    }

    public PlayingPiece[][] getBoard() {
        return grid;
    }

    public int getSize() {
        return size;
    }

    public List<Pair<Integer, Integer>> getFreeCells() {
        List<Pair<Integer, Integer>> freeCells = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == null) {
                    freeCells.add(new Pair<>(i, j));
                }
            }
        }
        return freeCells.isEmpty() ? null : freeCells;
    }
}
```

### TicTacToe Class
```java
// filepath: src/TicTacToe.java
import Modal.*;
import org.antlr.v4.runtime.misc.Pair;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TicTacToe {
    Deque<Player> players;
    Board gameBoard;

    public void initializeGame() {
        players = new LinkedList<>();

        PlayingPiece x = new PlayingPieceX();
        Player first = new Player("Player1", x);

        PlayingPiece o = new PlayingPieceO();
        Player second = new Player("Player2", o);

        players.add(first);
        players.add(second);

        gameBoard = new Board(3);
    }

    public String startGame() {
        boolean noWinner = true;
        while (noWinner) {
            Player playerTurn = players.removeFirst();
            gameBoard.printBoard();

            List<Pair<Integer, Integer>> freeSpaces = gameBoard.getFreeCells();
            if (freeSpaces == null) {
                noWinner = false;
                continue;
            }

            System.out.print("Player:" + playerTurn.getName() + " Enter row,column: ");
            Scanner inputScanner = new Scanner(System.in);
            String s = inputScanner.nextLine();
            String[] values = s.split(",");
            int inputRow = Integer.valueOf(values[0]);
            int inputColumn = Integer.valueOf(values[1]);

            boolean pieceAddedSuccessfully = gameBoard.addPiece(inputRow, inputColumn, playerTurn.getPiece());

            if (!pieceAddedSuccessfully) {
                System.out.println("Incorrect position chosen, try again");
                players.addFirst(playerTurn);
                continue;
            }
            players.addLast(playerTurn);

            boolean winner = isThereWinner(inputRow, inputColumn, playerTurn.getPiece().getType());
            if (winner) {
                return playerTurn.getName();
            }
        }
        return "tie";
    }

    private boolean isThereWinner(int row, int column, String pieceType) {
        boolean rowMatch = true;
        boolean columnMatch = true;
        boolean diagonalMatch = true;
        boolean antiDiagonalMatch = true;

        for (int i = 0; i < gameBoard.getSize(); i++) {
            if (gameBoard.getBoard()[row][i] == null || !gameBoard.getBoard()[row][i].getType().equals(pieceType)) {
                rowMatch = false;
            }
        }

        for (int i = 0; i < gameBoard.getSize(); i++) {
            if (gameBoard.getBoard()[i][column] == null || !gameBoard.getBoard()[i][column].getType().equals(pieceType)) {
                columnMatch = false;
            }
        }

        for (int i = 0, j = 0; i < gameBoard.getSize(); i++, j++) {
            if (gameBoard.getBoard()[i][j] == null || !gameBoard.getBoard()[i][j].getType().equals(pieceType)) {
                diagonalMatch = false;
            }
        }

        for (int i = 0, j = gameBoard.getSize() - 1; i < gameBoard.getSize(); i++, j--) {
            if (gameBoard.getBoard()[i][j] == null || !gameBoard.getBoard()[i][j].getType().equals(pieceType)) {
                antiDiagonalMatch = false;
            }
        }
        return rowMatch || columnMatch || diagonalMatch || antiDiagonalMatch;
    }
}
```

## Advantages

1. **Scalable**: Supports different game rules and player types.
2. **Maintainable**: Centralized logic for game management.
3. **Efficient**: Real-time validation of moves and win conditions.

## Conclusion

The Tic-Tac-Toe Design provides a structured approach to managing the game logic, players, and board efficiently. It ensures scalability, maintainability, and real-time operations for a seamless gaming experience.
