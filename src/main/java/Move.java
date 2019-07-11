/**
 * This class represents a checkers move. A checkers move consists of a sequence
 * of squares. The square numbers follow that of an internal representation.
 * @author Aswin van Woudenberg
 */
import java.util.Vector;

public class Move {
    /**
     * The vector of Integer objects represents the move sequence.
     */
    private Vector<Integer> squares;
    
    /**
     * This constructor creates a new move.
     */
    public Move() {
        squares = new Vector<Integer>();
    }
    
    /**
     * This constructor creates a new move with new square values
     */
    public Move(int...sq) {
        squares = new Vector<Integer>();
        for (int i: sq) {
            squares.add(i);
        }
    }
    
    /**
     * This method creates a copy of this Move object and returns it.
     * @return The return value is a copy of this move object.
     */
    public Move copy() {
        Move m = new Move();
	m.squares = (Vector)squares.clone();
	return m;
    }
    
    /**
     * This method returns as a Vector the squares the move consists of.
     * @return A vector representing the move sequence.
     */
    public Vector getSquares() {
        return squares;
    }
    
    /**
     * Add a new square to the move sequence.
     * @param square This is the location one wants to add to the move sequence.
     */
    public void addSquare(int square) {
        squares.add(square);
    }

    /**
     * This method is used to determine the number of squares in a move sequence.
     * @return The number of squares in a move.
     */
    public int length() {
        return squares.size();
    }
    
    /**
     * This method returns the square at a particular index of the move sequence.
     * @param index The index of the move sequence
     * @return The square sollicited.
     */
    public int squareAt(int index) {
        return squares.elementAt(index);
    }
    
    /**
     * This method returns the final square of the move sequence.
     * @return An integer donating the final square where the move finishes.
     */
    public int finalSquare() {
        return squares.elementAt(squares.size()-1);
    }
    
    /**
     * This method checks if the move contains one or more jumps
     * @return A boolean indicates is the move contains a jump.
     */
    public boolean includesJump() {
        if (squares.size() > 2)
            return true;
        if (Math.abs(squares.elementAt(0) - squares.elementAt(squares.size()-1)) > 5) 
            return true;
        return false;
    }
    
    /**
     * This method returns a string representation of the move in standard checkers notation.
     * @return The string representing the move.
     */
    @Override
    public String toString() {
	String result = "";
        if (squares.size()>=2) {
            result += Util.squareIndexToNotation(squares.elementAt(0));
            result += "-";
            result += Util.squareIndexToNotation(squares.elementAt(squares.size()-1));
        }
	return result;
    }
}
