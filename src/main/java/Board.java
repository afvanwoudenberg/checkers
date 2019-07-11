
import java.util.Observable;
import java.util.Vector;

/**
 * This class represents a checkerboard. A checkerboard consists of 64 squares
 * of which only 32 are used. Instead of standard notation an internal representation
 * is used for easier move calculation. 
 * 
 *     Standard                Internal
 *  representation           representation
 * 
 *       White                    White
 *    32  31  30  29          10  11  12  13
 *  28  27  26  25          14  15  16  17
 *    24  23  22  21          19  20  21  22
 *  20  19  18  17          23  24  25  26
 *    16  15  14  13          28  29  30  31
 *  12  11  10   9          32  33  34  35
 *     8   7   6   5          37  38  39  40
 *  4    3   2   1          41  42  43  44
 *       Black                   Black
 * 
 * @author Aswin van Woudenberg
 */
public class Board extends Observable {
    /**
     * The array of Piece's represent a checkerboard. An internal representation 
     * is used for easy Move generation.
     */
    private Square squares[] = new Square[55];
    
    /**
     * This constructor simply initializes the board
     */
    public Board() {
        setupBoard();
    }
    
    /**
     * This method initializes the board
     */
    public void setupBoard() {
        for (int i=0;i<55;i++) 
            squares[i] = Square.BORDER;
        for (int i=10;i<45;i++)
            squares[i] = Square.EMPTY;
        for (int i=10;i<23;i++)
            squares[i] = Square.WHITE_PAWN;
        for (int i=32;i<45;i++)
            squares[i] = Square.BLACK_PAWN;
        squares[18] = Square.BORDER;
        squares[27] = Square.BORDER;
        squares[36] = Square.BORDER;
        setChanged();
        notifyObservers();
    }
    
    public void undoMove(Board newBoard) {
        for (int i=0; i<55; i++) {
            squares[i] = newBoard.squareAt(i);
        }
        setChanged();
        notifyObservers();
    }
    
    /**
     * This method executes a move updating the checkerboard.
     * @param move The move to be executed.
     */
    public void executeMove(Move move) {
        int start = move.squareAt(0);
        Square sq = squares[start];
        squares[start] = Square.EMPTY;
        if (!move.includesJump()) {
            squares[move.squareAt(1)] = sq;
            setChanged();
            notifyObservers();
        } else {
            Vector<Integer> remove = new Vector<Integer>();
            for (int i=1; i<move.length(); i++) {
                int r = Math.abs(move.squareAt(i)+move.squareAt(i-1)) / 2;
                remove.add(r);
                squares[move.squareAt(i)] = sq;
                setChanged();
                notifyObservers();
                if (i<(move.length()-1)) {
                    squares[move.squareAt(i)] = Square.EMPTY;
                }
            }
            for (int i : remove) {
                squares[i] = Square.EMPTY;
                setChanged();
                notifyObservers();
            }
        }
        int last = move.squareAt(move.length()-1);
        if (last<14 || last>40) {
            switch(sq) {
                case BLACK_PAWN: 
                    squares[last] = Square.BLACK_KING; 
                    setChanged();
                    notifyObservers();
                    break;
                case WHITE_PAWN:
                    squares[last] = Square.WHITE_KING; 
                    setChanged();
                    notifyObservers();
                    break;
            }
        }
    }
    
    /**
     * This method returns the square at a specific index
     * @param index The index of the square
     * @return The square enum constant
     */
    public Square squareAt(int index) {
        return squares[index];
    }
    
    /**
     * This method can be used to find out if a square belongs to a specific player
     * @param index The index of the square
     * @param pc A PlayerColor enum value
     * @return A boolean indicating whether the square belongs to this player.
     */
    public boolean squareBelongsTo(int index, PlayerColor pc) {
        return squares[index].belongsTo(pc);
    }
    
    /**
     * This method returns true if a square has no pieces on it and it isn't the border.
     * @param index The index of the square.
     * @return A boolean indicating whether the square is empty.
     */
    public boolean squareIsEmpty(int index) {
        return squares[index].isEmpty();
    }
    
    /**
     * This method creates a copy of this Move object and returns it.
     * @return The return value is a copy of this move object.
     */
    public Board copy() {
        Board b = new Board();
	b.squares = (Square[])squares.clone();
	return b;
    }
}
