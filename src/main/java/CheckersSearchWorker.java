import javax.swing.SwingWorker;

/**
 * This class subclasses the SwingWorker class and encapsulates the search algorithm
 * in a seperate Thread.
 * @author Aswin van Woudenberg
 */
public class CheckersSearchWorker extends SwingWorker<Move, Void> {
    private Search search;
    private CheckersGame checkersGame;
    
    /**
     * The constructor
     * @param s The search class
     * @param cg The CheckersGame class
     */
    public CheckersSearchWorker(Search s, CheckersGame cg) {
        search = s;
        checkersGame = cg;
    }
    
    @Override
    public Move doInBackground() {
        Move result = search.getComputerMove(checkersGame.getBoard(), checkersGame.getCurrentPlayerColor());
        return result;
    }
    
    @Override
    public void done() {
        try {
            if (isCancelled()) {
                return;
            }
            Move m = get();
            checkersGame.executeMove(m);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
