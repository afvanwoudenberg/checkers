import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import javax.swing.JPanel;

/**
 * This class is responsible for painting the checkerboard.
 * @author Aswin van Woudenberg
 */
public class CheckerBoard extends JPanel implements Observer {
    private boolean inverted;
    private Board board;
    private Color selectedColor;
    private HashSet<Integer> selectedFields;
    
    /**
     * The constructor
     */
    public CheckerBoard() {
        selectedFields = new HashSet<Integer>();
        selectedColor = new Color(255,128,0);
    }
    
    /**
     * This method adds a selected field to the set. If the field is already 
     * selected, nothing happens.
     * @param field The field to select
     */
    public void selectField(int field) {
        selectedFields.add(field);
    }
    
    /**
     * Return the selected fields as a Set
     * @return A Set containing the selected fields.
     */
    public Set getSelectedFields() {
        return selectedFields;
    }           
    
    /**
     * This method removes a field from the selected set.
     * @param field The field to unselect
     */
    public void unselectField(int field) {
        selectedFields.remove(field);
    }
    
    /**
     * This method unselects all selected fields.
     */
    public void unselectAllFields() {
        selectedFields.clear();
    }
    
    /**
     * This method returns a boolean if the field is part of the set of selected 
     * fields.
     * @param field An int denoting the field.
     * @return A boolean that indicates whether the field is selected.
     */
    public boolean isSelected(int field) {
        return selectedFields.contains(field);
    }
    
    /**
     * Set the model board.
     * @param b The Board object.
     */
    public void setBoard(Board b) {
        board = b;
    }
    
    /**
     * Set inverted, which means draw the checkerboard from a certain point of view.
     * @param value A boolean indicating whether the checkerboard should be drawn inverted.
     */
    public void setInverted(boolean value) {
        inverted = value;
    }
    
    /**
     * This method returns true if the checkerboard is being drawn inverted.
     * @return A boolean indicating whether the checkerboard is being drawn inverted.
     */
    public boolean isInverted() {
        return inverted;
    }
    
    /**
     * This overridden method draws the checkerboard.
     * @param g The graphics object.
     */
    @Override
    public void paintComponent(Graphics g) {
        int squareWidth = getWidth() / 8;
        int squareHeight = getHeight() / 8;

        for (int y=0; y<8; y++) {
            for (int x=0; x<8; x++) {
                if ((x+y)%2 == 0) {
                    g.setColor(Color.WHITE);
                    g.fillRect(x*squareWidth, y*squareHeight, squareWidth, squareHeight);            
                } else {
                    if (isSelected(calcIndex(x,y))) {
                        g.setColor(selectedColor);
                    } else {
                        g.setColor(Color.GRAY);
                    }
                    g.fillRect(x*squareWidth, y*squareHeight, squareWidth, squareHeight);
                    if (board!=null) {
                        switch(board.squareAt(calcIndex(x,y))) {
                            case WHITE_KING:
                                g.setColor(Color.WHITE);
                                g.fillOval(x*squareWidth+2, y*squareHeight+2, squareWidth-5, squareHeight-5);
                                g.setColor(Color.BLACK);
                                g.drawOval(x*squareWidth+2, y*squareHeight+2, squareWidth-5, squareHeight-5);
                                g.fillOval(x*squareWidth+9, y*squareHeight+9, squareWidth-19, squareHeight-19);
                                break;
                            case BLACK_KING:
                                g.setColor(Color.BLACK);
                                g.fillOval(x*squareWidth+2, y*squareHeight+2, squareWidth-5, squareHeight-5);
                                g.setColor(Color.WHITE);
                                g.drawOval(x*squareWidth+2, y*squareHeight+2, squareWidth-5, squareHeight-5);
                                g.fillOval(x*squareWidth+9, y*squareHeight+9, squareWidth-19, squareHeight-19);
                                break;
                            case WHITE_PAWN:
                                g.setColor(Color.WHITE);
                                g.fillOval(x*squareWidth+2, y*squareHeight+2, squareWidth-5, squareHeight-5);
                                g.setColor(Color.BLACK);
                                g.drawOval(x*squareWidth+2, y*squareHeight+2, squareWidth-5, squareHeight-5);
                                break;
                            case BLACK_PAWN:
                                g.setColor(Color.BLACK);
                                g.fillOval(x*squareWidth+2, y*squareHeight+2, squareWidth-5, squareHeight-5);
                                g.setColor(Color.WHITE);
                                g.drawOval(x*squareWidth+2, y*squareHeight+2, squareWidth-5, squareHeight-5);
                                break;
                        }
                    }           
                }
            }
        }
    }

    /**
     * This method returns the field index value by pixel coordinates. This method
     * will most likely be called from the mouseClick event.
     * @return The index value
     */
    public int getFieldByPixelCoord(int mx, int my) {
        int squareWidth = getWidth() / 8;
        int squareHeight = getHeight() / 8;

        for (int y=0; y<8; y++) {
            for (int x=0; x<8; x++) {
                if (mx>=x*squareWidth && mx<(x+1)*squareWidth &&
                        my>=y*squareHeight && my<(y+1)*squareHeight ) {
                    return calcIndex(x,y);
                }
            }
        }
        return 0;
    }
    
    /**
     * Calculate the x and y coordinates to the index value for the internal board
     * representation.
     * @param x The x coordinate ranging from 0 to 7
     * @param y The y coordinate ranging from 0 to 7
     * @return The index value
     */
    private int calcIndex(int x, int y) {
        int indexArr[][] = new int[][] { 
            {0,10,0,11,0,12,0,13},
            {14,0,15,0,16,0,17,0},
            {0,19,0,20,0,21,0,22},
            {23,0,24,0,25,0,26,0},
            {0,28,0,29,0,30,0,31},
            {32,0,33,0,34,0,35,0},
            {0,37,0,38,0,39,0,40},
            {41,0,42,0,43,0,44,0} };
        int indexArrInv[][] = new int[][] {
            {0,44,0,43,0,42,0,41},
            {40,0,39,0,38,0,37,0},
            {0,35,0,34,0,33,0,32},
            {31,0,30,0,29,0,28,0},
            {0,26,0,25,0,24,0,23},
            {22,0,21,0,20,0,19,0},
            {0,17,0,16,0,15,0,14},
            {13,0,12,0,11,0,10,0} };
        if (inverted) {
            return indexArrInv[y][x];
        }
        return indexArr[y][x];
    }
        
    public void update(Observable o, Object arg) {
        if (o == board) {
            repaint();
        }
    }

}
