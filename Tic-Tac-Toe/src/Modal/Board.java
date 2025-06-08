package Modal;

import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.misc.Pair;

public class Board {
    private int size ;
    private PlayingPiece[][] board;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public PlayingPiece[][] getBoard() {
        return board;
    }

    public void setBoard(PlayingPiece[][] board) {
        this.board = board;
    }

    public Board(int i) {
        this.size = i;
        board = new PlayingPiece[i][i];
    }

    public boolean addPiece(int r, int c,PlayingPiece playingPiece){
        if(board[r][c]==null){
            board[r][c] = playingPiece;
            return true;
        }
        return false;
    }

    public List<Pair<Integer,Integer>> getFreeCells(){
        List<Pair<Integer,Integer>> freeCall = new ArrayList<>();

        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++)
                if(board[i][j]==null)
                    freeCall.add(new Pair<>(i,j));
        }

        return freeCall;
    }

    public void printBoard(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(board[i][j]==null)
                    System.out.print("  ");
                else{
                    System.out.print( board[i][j].getPieceType().name()+" ");
                }
                System.out.print("|");
            }
            System.out.println();
        }
    }
}
