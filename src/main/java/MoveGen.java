import java.util.Vector;

/**
 * This class generates checkers moves.
 * @author Aswin van Woudenberg
 */
public class MoveGen {
    /**
     * This class contains only static methods. Thus, the caller should 
     * be prevented from constructing objects of this class, by declaring this 
     * private constructor. 
     */
    private MoveGen() {
        // prevents even the native class from calling constuctor.
        throw new AssertionError();
    }
    
    /**
     * This method generates possible moves for a specific PlayerColor.
     * @param board The board state
     * @param pc The current PlayerColor who's to move
     * @return A vector with all possible moves. It only returns standard moves 
     * when there are no captures.
     */
    public static Vector generatePossibleMoves(Board board, PlayerColor pc) {
        Vector<Move> result = generatePossibleJumps(board,pc);
        if (result.isEmpty()) {
            return generatePossibleMovesAux(board,pc);
        }
        return result;
    }
    
    /**
     * This auxiliary method generates all possible moves, but not captures for
     * a specific PlayerColor.
     * @param board The board state
     * @param pc The current PlayerColor
     * @return A vector with all possible moves that are not captures.
     */
    private static Vector generatePossibleMovesAux(Board board, PlayerColor pc) {
        Vector<Move> result = new Vector<Move>();
        for (int i=0; i<55; i++) {
            if (board.squareBelongsTo(i, pc)) {
                result.addAll(generatePossibleMovesOnePiece(board,i));
            }
        }
        return result;
    }
    
    /**
     * This method returns a Vector with all possible moves starting from a 
     * particular square.
     * @param board The current board state
     * @param index The square to start from
     * @return A vector containing all moves starting from a particular square.
     */
    private static Vector generatePossibleMovesOnePiece(Board board, int index) {
        Vector<Move> result = new Vector<Move>();
        for (int i : moveValues(board.squareAt(index))) {
            if (board.squareIsEmpty(index + i)) {
                result.add(new Move(index,index+i));
            }
        }
        return result;
    }
    
    /**
     * This method generates a Vector with all possible jumps or captures for 
     * a particular PlayerColor
     * @param board The current board state
     * @param pc The current PlayerColor
     * @return A vector containing all possible jumps.
     */
    private static Vector generatePossibleJumps(Board board, PlayerColor pc) {
        Vector<Move> result = new Vector<Move>();
        for (int i=0; i<55; i++) {
            if (board.squareBelongsTo(i, pc)) {
                result.addAll(generatePossibleJumpsOnePiece(board,i));
            }
        }
        return result;
    }
    
    /**
     * This method generates a Vector with all possible jumps or captures starting 
     * from a particular square.
     * @param board The current board state
     * @param index The starting square
     * @return A vector containing all possible jumps.
     */
    private static Vector generatePossibleJumpsOnePiece(Board board, int index) {
        Vector<Move> result = new Vector<Move>();
        PlayerColor pc = board.squareAt(index).belongsTo();
        for (int i : moveValues(board.squareAt(index))) {
            if (board.squareBelongsTo(index+i,pc.opponent()) && board.squareIsEmpty(index+i+i)) {
                Move m = new Move(index,index+i+i);
                Board b = board.copy();
                b.executeMove(m);
                
                if (!(board.squareAt(index).isPawn() && b.squareAt(index+i+i).isKing())) {
                    Vector<Move> extResult = generatePossibleJumpsOnePieceExtend(b, m);
                    if (extResult.isEmpty()) {
                        result.add(m);
                    } else {
                        result.addAll(extResult);
                    }
                } else {
                    result.add(m);
                }
            }
        }
        return result;
    }
    
    /**
     * This method generates a Vector with all possible jumps or captures that 
     * can follow a sequence of jumps.
     * @param board The current board state
     * @param jumpsSoFar The move so far
     * @return A vector containing all possible jumps.
     */
    private static Vector generatePossibleJumpsOnePieceExtend(Board board, Move jumpsSoFar) {
        Vector<Move> result = new Vector<Move>();
        int index = jumpsSoFar.finalSquare();
        PlayerColor pc = board.squareAt(index).belongsTo();
        for (int i : moveValues(board.squareAt(index))) {
            if (board.squareBelongsTo(index+i,pc.opponent()) && board.squareIsEmpty(index+i+i)) {
                Move move = jumpsSoFar.copy();
                move.addSquare(index+i+i);
                
                Move m = new Move(index,index+i+i);
                Board b = board.copy();
                b.executeMove(m);
                if (!(board.squareAt(index).isPawn() && b.squareAt(index+i+i).isKing())) {
                    Vector<Move> extResult = generatePossibleJumpsOnePieceExtend(b, move);
                    if (extResult.isEmpty()) {
                        result.add(move);
                    } else {
                        result.addAll(extResult);
                    }
                } else {
                    result.add(move);
                }
            }
        }
        return result;
    }
    
    /**
     * This method returns an array with integers representing possible steps 
     * for a specific piece.
     * @param sq The square the piece is on.
     * @return An array with possible steps.
     */
    private static int[] moveValues(Square sq) {
        switch(sq) {
            case WHITE_PAWN: return new int[]{4, 5};
            case BLACK_PAWN: return new int[]{-4, -5};
            case WHITE_KING: 
            case BLACK_KING: return new int[]{4, 5, -4, -5};
            default: return new int[]{};
        }
    }
}
