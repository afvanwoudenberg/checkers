/**
 * This enum defines the colors BLACK and WHITE
 * @author Aswin van Woudenberg
 */
public enum PlayerColor {
    BLACK, WHITE;
    
    /**
     * This method returns the opponents color
     * @return The opponents enum value
     */
    public PlayerColor opponent() {
        return (this==BLACK?WHITE:BLACK);
    }
}
