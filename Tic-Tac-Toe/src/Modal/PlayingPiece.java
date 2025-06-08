package Modal;

public class PlayingPiece {

    private PieceType pieceType;

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    PlayingPiece(PieceType pieceType) {
        this.pieceType = pieceType;
    }
}

