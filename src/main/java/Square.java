/**
 * This enum represents a checkers square.
 * @author Aswin van Woudenberg
 */
public enum Square {
    WHITE_KING, BLACK_KING, WHITE_PAWN, BLACK_PAWN, EMPTY, BORDER;
    
    /**
     * This method returns true if the square belongs to a particular color.
     * @param pc A PlayerColor enum.
     * @return true if piece on square corresponds to PlayerColor.
     */
    public boolean belongsTo(PlayerColor pc) {
        if (pc == PlayerColor.BLACK) {
            return (this == BLACK_PAWN || this == BLACK_KING);
        } else {
            return (this == WHITE_PAWN || this == WHITE_KING);
        }
    }
    
    /**
     * This method returns the PlayerColor the square belongs to. If the square 
     * is empty or doesn't belong to any PlayerColor otherwise, the result is 
     * always PlayerColor.WHITE. Therefor one should always check whether the 
     * square contains a pawn or a king.
     * @return The PlayerColor the square belongs to, if the square represents a 
     * pawn or a king.
     */
    public PlayerColor belongsTo() {
        if (belongsTo(PlayerColor.BLACK)) return PlayerColor.BLACK;
        return PlayerColor.WHITE;
    }
    
    /**
     * This method determines if the current square is empty.
     * @return true if there is no piece on the square.
     */
    public boolean isEmpty() {
        return (this == EMPTY);
    }
    
    /**
     * This method checks if the square is occupied by a pawn.
     * @return A boolean indicating whether the square is a pawn.
     */
    public boolean isPawn() {
        return (this == BLACK_PAWN || this == WHITE_PAWN);
    }
    
    /**
     * This method checks if the square is occupied by a king.
     * @return A boolean indicating whether the square is a king.
     */
    public boolean isKing() {
        return (this == BLACK_KING || this == WHITE_KING);
    }
    
    /**
     * This method determines if the square is occupied by a king or a pawn.
     * @return A boolean is the square is not empty or represents a border.
     */
    public boolean isPiece() {
        return isKing() || isPawn();
    }
    
    /**
     * This method returns a string representation of the square.
     * @return The character representing the piece.
     */
    @Override
    public String toString() {
        switch(this) {
            case WHITE_KING: return "W";
            case BLACK_KING: return "B";
            case WHITE_PAWN: return "w";
            case BLACK_PAWN: return "b";
            case EMPTY: return ":";
            default: return "";
        }
    }
}
