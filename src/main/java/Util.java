
/**
 * This class defines various useful functions
 * @author Aswin van Woudenberg
 */
public class Util {
    /**
     * This class contains only static utility methods. Thus, the caller should 
     * be prevented from constructing objects of this class, by declaring this 
     * private constructor. 
     */
    private Util() {
        // prevents even the native class from calling constuctor.
        throw new AssertionError();
    }
    
    /**
     * This private method converts an internal square representation to an external 
     * one and is used by the toString method.
     * @param square The internal representation of the square.
     * @return The external representation of a square according to standard checkers notation.
     */
    public static int squareIndexToNotation(int square) {
        int n = 45 - square;
        if (square<36) n--;
        if (square<27) n--;
        if (square<18) n--;
        return n;
    }
    
    /**
     * Print the checkerboard.
     * @param board The board to be printed.
     */
    public static void printBoard(Board board) {
        System.out.println("              WHITE");
        System.out.println("+---+---+---+---+---+---+---+---+");
        System.out.println("|   |:" + board.squareAt(10).toString() 
                + ":|   |:" + board.squareAt(11).toString() 
                + ":|   |:" + board.squareAt(12).toString() 
                + ":|   |:" + board.squareAt(13).toString() + ":|");
        System.out.println("+---+---+---+---+---+---+---+---+");
        System.out.println("|:" + board.squareAt(14).toString() 
                + ":|   |:" + board.squareAt(15).toString() 
                + ":|   |:" + board.squareAt(16).toString() 
                + ":|   |:" + board.squareAt(17).toString() + ":|   |");
        System.out.println("+---+---+---+---+---+---+---+---+");
        System.out.println("|   |:" + board.squareAt(19).toString() 
                + ":|   |:" + board.squareAt(20).toString() 
                + ":|   |:" + board.squareAt(21).toString() 
                + ":|   |:" + board.squareAt(22).toString() + ":|");
        System.out.println("+---+---+---+---+---+---+---+---+");
        System.out.println("|:" + board.squareAt(23).toString() 
                + ":|   |:" + board.squareAt(24).toString() 
                + ":|   |:" + board.squareAt(25).toString() 
                + ":|   |:" + board.squareAt(26).toString() + ":|   |");
        System.out.println("+---+---+---+---+---+---+---+---+");
        System.out.println("|   |:" + board.squareAt(28).toString() 
                + ":|   |:" + board.squareAt(29).toString() 
                + ":|   |:" + board.squareAt(30).toString() 
                + ":|   |:" + board.squareAt(31).toString() + ":|");
        System.out.println("+---+---+---+---+---+---+---+---+");
        System.out.println("|:" + board.squareAt(32).toString() 
                + ":|   |:" + board.squareAt(33).toString() 
                + ":|   |:" + board.squareAt(34).toString() 
                + ":|   |:" + board.squareAt(35).toString() + ":|   |");
        System.out.println("+---+---+---+---+---+---+---+---+");
        System.out.println("|   |:" + board.squareAt(37).toString() 
                + ":|   |:" + board.squareAt(38).toString() 
                + ":|   |:" + board.squareAt(39).toString() 
                + ":|   |:" + board.squareAt(40).toString() + ":|");
        System.out.println("+---+---+---+---+---+---+---+---+");
        System.out.println("|:" + board.squareAt(41).toString() 
                + ":|   |:" + board.squareAt(42).toString() 
                + ":|   |:" + board.squareAt(43).toString() 
                + ":|   |:" + board.squareAt(44).toString() + ":|   |");
        System.out.println("+---+---+---+---+---+---+---+---+");
        System.out.println("              BLACK");
    }
}
