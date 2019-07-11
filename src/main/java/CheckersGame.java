import java.util.Observable;
import java.util.Stack;
import java.util.Vector;

/**
 * This class manages a checkers game.
 * @author Aswin van Woudenberg
 */
public class CheckersGame extends Observable {
    private Board board;
    private Search search;
    private PlayerType players[];
    private PlayerColor currentPlayer;
    private Stack<Board> history;
    private CheckersSearchWorker worker;
    private boolean gameHasEnded;
    
    /**
     * The constructor.
     */
    public CheckersGame() {
        board = new Board();
        search = new Search();
        players = new PlayerType[2];
        players[0] = PlayerType.HUMAN;
        players[1] = PlayerType.COMPUTER;
        currentPlayer = PlayerColor.BLACK;
        history = new Stack<Board>();
        gameHasEnded = false;
    }
    
    /**
     * This method returns true if the game has ended.
     * @return A boolean indicating whether the game is over.
     */
    public boolean isGameOver() {
        return gameHasEnded;
    }
    
    /**
     * Method that returns if the board is in it's initial state.
     * @return Boolean indicating the initial state.
     */
    public boolean isInitialState() {
        return history.isEmpty();
    }
    
    /**
     * This method returns the current player who is to move.
     * @return A PlayerColor type indicating which player's turn it is.
     */
    public PlayerColor getCurrentPlayerColor() {
        return currentPlayer;
    }
    
    /**
     * This method returns is the current player is human or a computer.
     * @return PlayerType denoting COMPUTER or HUMAN.
     */
    public PlayerType getCurrentPlayerType() {
        switch (currentPlayer) {
            case BLACK:
                return players[0];
            default: // WHITE
                return players[1];
        }
    }
    
    /**
     * Start a new game.
     */
    public void newGame() {
        if (worker != null && !worker.isDone()) {
            // Cancel current search to begin a new one.
            // You want only one search at a time.
            worker.cancel(true);
            worker = null;
        }
        gameHasEnded = false;
        board.setupBoard();
        currentPlayer = PlayerColor.BLACK;
        history.clear();
        setChanged();
        notifyObservers();
        doComputerMoveIfNeeded();
    }
    
    /**
     * Generate all possible moves for the current player and the current board 
     * situation.
     * @return A vector with all possible moves.
     */
    public Vector generatePossibleMoves() {
        Vector possibleMoves = MoveGen.generatePossibleMoves(board, currentPlayer);
        if (possibleMoves.isEmpty()) {
            gameHasEnded = true;
            setChanged();
            notifyObservers();
        }
        return possibleMoves;
    }
    
    /**
     * Execute a move and give turn to the opponent.
     * @param m The move to be executed.
     */
    public void executeMove(Move m) {
        if (m!=null) {
            history.push(board.copy());
            board.executeMove(m);
            currentPlayer = currentPlayer.opponent();
            setChanged();
            notifyObservers();
            doComputerMoveIfNeeded();
        } else {
            gameHasEnded = true;
            setChanged();
            notifyObservers();
        }
    }
    
    public void undoMove() {
        if (!history.empty()) {
            if (worker != null && !worker.isDone()) {
                // Cancel current search to begin a new one.
                // You want only one search at a time.
                worker.cancel(true);
                worker = null;
            }
            Board b = history.pop();
            board.undoMove(b);
            currentPlayer = currentPlayer.opponent();
            gameHasEnded = false;
            setChanged();
            notifyObservers();
            doComputerMoveIfNeeded();
        }
    }
    
    /**
     * This method returns true if a certain player has won this game.
     * @param pc The PlayerColor, BLACK or WHITE
     * @return A boolean indicating if a player has won.
     */
    public boolean isWinner(PlayerColor pc) {
        if (getCurrentPlayerColor()==pc.opponent() && gameHasEnded) {
            if (MoveGen.generatePossibleMoves(board, pc.opponent()).isEmpty()) {
                return true;
            }                
        }
        return false;
    }
    
    /**
     * This method returns true if a certain player has lost this game.
     * @param pc The player for which to find out.
     * @return A boolean indicating if the player has lost.
     */
    public boolean isLoser(PlayerColor pc) {
        if (getCurrentPlayerColor()==pc && gameHasEnded) {
            if (MoveGen.generatePossibleMoves(board, pc).isEmpty()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * This method checks if the current player is the computer, and if this is 
     * the case, it find a good move.
     */
    private void doComputerMoveIfNeeded() {
        if (getCurrentPlayerType()==PlayerType.COMPUTER && !gameHasEnded) {
            worker = new CheckersSearchWorker(search, this);
            worker.execute();
        }
    }
    
    /**
     * Set the searchdepth.
     * @param depth The searchdepth a.k.a. level.
     */
    public void setLevel(int depth) {
        if (worker != null && !worker.isDone()) {
            // Cancel current search to begin a new one.
            // You want only one search at a time.
            worker.cancel(true);
            worker = null;
        }
        search.setSearchDepth(depth);
        setChanged();
        notifyObservers();
        doComputerMoveIfNeeded();
    }
    
    /**
     * Get the searchdepth
     * @return The searchdepth a.k.a. level.
     */
    public int getLevel() {
        return search.getSearchDepth();
    }
    
    /**
     * This method returns the PlayerType for a specific PlayerColor.
     * @param pc The PlayerColor for which the PlayerType is wanted.
     * @return The PlayerType, HUMAN or COMPUTER.
     */
    public PlayerType getPlayerType(PlayerColor pc) {
        switch(pc) {
            case BLACK:
                return players[0];
            default: // WHITE:
                return players[1];
        }
    }
    
    /**
     * This method sets the PlayerType for PlayerColor black or white
     * @param pc The PlayerType, HUMAN or COMPUTER
     * @param pt The PlayerColor, BLACK or WHITE
     */
    public void setPlayerType(PlayerColor pc, PlayerType pt) {
        if (worker != null && !worker.isDone()) {
            // Cancel current search to begin a new one.
            // You want only one search at a time.
            worker.cancel(true);
            worker = null;
        }
        switch(pc) {
            case BLACK:
                players[0] = pt;
                break;
            case WHITE:
                players[1] = pt;
                break;
        }
        setChanged();
        notifyObservers();
        doComputerMoveIfNeeded();
    }
    
    /**
     * This method returns the board representation of the game.
     * @return The board
     */
    public Board getBoard() {
        return board;
    }
}
