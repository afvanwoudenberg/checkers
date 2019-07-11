
import java.util.Vector;


/**
 * This class implements the minimax with alpha-beta pruning algoritm for Checkers
 * @author Aswin van Woudenberg
 */
public class Search {
    // Used by the evaluate method to determine the board situation.
    private static final int PAWNS_WORTH = 100;
    private static final int KINGS_WORTH = 130;
    private static final int BACKRANKS_WORTH = 10;
    
    // Used by the minimax with alpha-beta pruning algorithm
    private static final int INFINITY = 10000;
    private static final int MAX_DEPTH = 40;
    
    /**
     * This variable defines how deep the algorithm searches for an optimal solution.
     */
    private int searchDepth;
    
    /**
     * The constructor
     */
    public Search() {
        searchDepth = 5;
    }
    
    /**
     * This method sets the search depth for the alpha-beta algorithm
     * @param depth The search depth
     */
    public void setSearchDepth(int depth) {
        searchDepth = depth;
    }
    
    /**
     * This method returns the search depth used by the alpha-beta algorithm
     * @return The search depth
     */
    public int getSearchDepth() {
        return searchDepth;
    }
    
    /**
     * This method evaluates the current board situation for a specific player.
     * @param board The current board situation
     * @param pc The current player
     * @return An integer evaluating the board situation.
     */
    private int evaluation(Board board, PlayerColor pc) {
        int score=0;
        int countWhiteKings=0, countBlackKings=0, countWhitePawns=0, countBlackPawns=0;
        
        for (int i=10; i<45; i++) {
            switch(board.squareAt(i)) {
                case WHITE_PAWN: 
                    countWhitePawns++; 
                    break;
                case BLACK_PAWN:
                    countBlackPawns++;
                    break;
                case WHITE_KING:
                    countWhiteKings++;
                    break;
                case BLACK_KING:
                    countBlackKings++;
                    break;
            }
        }
        
        if (board.squareAt(10)==Square.WHITE_PAWN && board.squareAt(12)==Square.WHITE_PAWN && countBlackPawns>1)
            score -= BACKRANKS_WORTH;
        if (board.squareAt(44)==Square.BLACK_PAWN && board.squareAt(42)==Square.BLACK_PAWN && countWhitePawns>1)
            score += BACKRANKS_WORTH;
        
        int blackMaterial = countBlackKings*KINGS_WORTH + countBlackPawns*PAWNS_WORTH;
        int whiteMaterial = countWhiteKings*KINGS_WORTH + countWhitePawns*PAWNS_WORTH;
        
        score += ((blackMaterial-whiteMaterial)*200)/(blackMaterial+whiteMaterial);
        score += blackMaterial-whiteMaterial;
        
        return pc==PlayerColor.BLACK?score:-score;
    }
    
    /**
     * The minimax alpha-beta pruning algorithm
     * @param b The board situation
     * @param d The search depth
     * @param pc The current player color
     * @param lowest The low cut-off value
     * @param highest The high cut-off value
     * @param realDepth The actual search depth
     * @return An int evaluating the board potential situation.
     */
    private int alphaBeta(Board b, int d, PlayerColor pc, int lowest, int highest, int realDepth) {
        Board board;
        Vector<Move> possibleMoves;
        possibleMoves = MoveGen.generatePossibleMoves(b, pc);
        int depth = d;
        
        if (possibleMoves.isEmpty()) {
            return -INFINITY+realDepth-1;
        }
        if (depth<1 && possibleMoves.size()==1) {
            depth++;
        }
        if (depth<1 || realDepth>=MAX_DEPTH-1) {
            return evaluation(b, pc);
        }
        
        int best = -INFINITY;
        for (int i=0; i<possibleMoves.size() && best<highest; i++) {
            board = b.copy();
            board.executeMove(possibleMoves.elementAt(i));
            int score = -alphaBeta(board,depth-1,pc.opponent(),-highest,-lowest,realDepth+1);
            if (score>best) {
                best = score;
                if (score<lowest) {
                    lowest = best;
                }
            }
        }
        return best;
    }
    
    /**
     * Part two of the alphaBeta algorithm. This method is called by the 
     * getComputerMove method and returns the best next move.
     * @param b The board state.
     * @param depth The search depth.
     * @param pc The current player's color.
     * @return The best move found searching.
     */
    private Move alphaBeta(Board b, int depth, PlayerColor pc) {
        int lowest = -INFINITY;
        int highest = INFINITY;
        
        Board board;
        
        Vector<Move> possibleMoves;
        possibleMoves = MoveGen.generatePossibleMoves(b, pc);
        
        int best = -INFINITY;
        Move bestMove = possibleMoves.elementAt(0);
        for (int i=0; i<possibleMoves.size() && best<highest; i++) {
            board = b.copy();
            board.executeMove(possibleMoves.elementAt(i));
            int score = -alphaBeta(board, depth-1, pc.opponent(), -highest, -lowest, 1);
            if (score>best) {
                best = score;
                bestMove = possibleMoves.elementAt(i);
                if (score>lowest) {
                    lowest = best;
                }
            }
        }
        return bestMove;
    }
    
    /**
     * This method calls the minimax alpha beta pruning function to calculate the 
     * best move to make next. This method should not be called when the game has
     * already ended.
     * @param board The current board state.
     * @param pc The PlayerColor who is to move.
     * @return The most optimal move to take next.
     */
    public Move getComputerMove(Board board, PlayerColor pc) {
        Vector<Move> possibleMoves;
        possibleMoves = MoveGen.generatePossibleMoves(board, pc);
        if (possibleMoves.isEmpty()) {
            return null;
        } else if (possibleMoves.size()==1) {
            return possibleMoves.elementAt(0);
        } else {
            return alphaBeta(board,searchDepth,pc);
        }
    }
    
}
