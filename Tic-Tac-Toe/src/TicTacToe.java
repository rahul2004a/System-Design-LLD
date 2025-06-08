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
        Player first = new Player("Player1",x);

        PlayingPiece o = new PlayingPieceO();
        Player second = new Player("Player2",o);


        players.add(first);
        players.add(second);

        gameBoard = new Board(3);
    }

    public String startGame() {
        boolean noWinner = true;
        while(noWinner){

            //take out the player whose turn is and also put the player in the list back
            Player playerTurn = players.removeFirst();

            //get the free space from the board
            gameBoard.printBoard();

            //get the free space from the board
            List<Pair<Integer,Integer>> freeSpaces = gameBoard.getFreeCells();
            if(freeSpaces==null){
                noWinner = false;
                continue;
            }

            //read the user input
            System.out.print("Player:" + playerTurn.getName() + " Enter row,column: ");
            Scanner inputScanner = new Scanner(System.in);
            String s = inputScanner.nextLine();
            String[] values = s.split(",");
            int inputRow = Integer.valueOf(values[0]);
            int inputColumn = Integer.valueOf(values[1]);

            //place the piece
            boolean pieceAddedSuccessfully = gameBoard.addPiece(inputRow,inputColumn,playerTurn.getPlayingPiece());

            if(!pieceAddedSuccessfully) {
                //player can not insert the piece into this cell, player has to choose another cell
                System.out.println("Incorredt possition chosen, try again");
                players.addFirst(playerTurn);
                continue;
            }
            players.addLast(playerTurn);

            boolean winner = isThereWinner(inputRow, inputColumn, playerTurn.getPlayingPiece().getPieceType());
            if(winner) {
                return playerTurn.getName();
            }

        }
        return "tie";
    }

    private boolean isThereWinner(int row, int column, PieceType pieceType) {
        boolean rowMatch = true;
        boolean columnMatch = true;
        boolean diagonalMatch = true;
        boolean antiDiagonalMatch = true;

        //need to check in row
        for(int i=0;i<gameBoard.getSize();i++) {

            if(gameBoard.getBoard()[row][i] == null || gameBoard.getBoard()[row][i].getPieceType() != pieceType) {
                rowMatch = false;
            }
        }

        //need to check in column
        for(int i=0;i<gameBoard.getSize();i++) {

            if(gameBoard.getBoard()[i][column] == null || gameBoard.getBoard()[i][column].getPieceType() != pieceType) {
                columnMatch = false;
            }
        }

        //need to check diagonals
        for(int i=0, j=0; i<gameBoard.getSize();i++,j++) {
            if (gameBoard.getBoard()[i][j] == null || gameBoard.getBoard()[i][j].getPieceType() != pieceType) {
                diagonalMatch = false;
            }
        }

        //need to check anti-diagonals
        for(int i=0, j=gameBoard.getSize()-1; i<gameBoard.getSize();i++,j--) {
            if (gameBoard.getBoard()[i][j] == null || gameBoard.getBoard()[i][j].getPieceType() != pieceType) {
                antiDiagonalMatch = false;
            }
        }
        return rowMatch || columnMatch || diagonalMatch || antiDiagonalMatch;
    }
}
