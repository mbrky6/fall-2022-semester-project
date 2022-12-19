package webfiles;

/**
 * An individual tile in Minesweeper. Holds and updates all the information about it.
 * @author idont
 */
public class Tile {
    private boolean hidden;
    private boolean mine;
    private boolean flagged;
    private char status;
    private int xPos;
    private int yPos;
    private Boolean[] adjacentTiles;
    private int adjacentMines;
    private Grid home;

    /**
     * Full-arg constructor. This will never get used.
     * @param hidden Whether or not the Tile's contents are hidden
     * @param mine Whether or not the Tile has a mine
     * @param flagged Whether or not the Tile has a flag
     * @param status The 1-character status of the Tile
     * @param xPos X position of the Tile
     * @param yPos Y position of the Tile
     * @param adjacentTiles Array containing the value of each adjacent Tile's 'mine' variable
     * @param adjacentMines Number of adjacent Tiles that have mines
     * @param home Grid the Tile belongs to
     */
    public Tile(boolean hidden, boolean mine, boolean flagged, char status, int xPos, int yPos, Boolean[] adjacentTiles, int adjacentMines, Grid home) {
        this.hidden = hidden;
        this.mine = mine;
        this.flagged = flagged;
        this.status = status;
        this.xPos = xPos;
        this.yPos = yPos;
        this.adjacentMines = adjacentMines;
        this.adjacentTiles = adjacentTiles;
        this.home = home;
    } // + Tile

    /**
     * Constructor for creating a Tile in an empty grid. This is used when starting a new game.
     * @param hidden Whether or not the Tile's contents are hidden
     * @param mine Whether or not the Tile has a mine
     * @param flagged Whether or not the Tile has a flag
     * @param xPos X position of the Tile
     * @param yPos Y position of the Tile
     * @param home Grid the Tile belongs to
     */
    public Tile(boolean hidden, boolean mine, boolean flagged, int xPos, int yPos, Grid home) {
        this.hidden = hidden;
        this.mine = mine;
        this.flagged = flagged;
        this.xPos = xPos;
        this.yPos = yPos;
        this.home = home;
    } // + Tile (generating)

    /**
     * Constructor for creating a Tile with just a status. This is used when performing an action.
     * @param status The 1-character status of the Tile
     * @param xPos X position of the Tile
     * @param yPos Y position of the Tile
     * @param home Grid the Tile belongs to
     */
    public Tile(char status, int xPos, int yPos, Grid home) {
        this.status = status;
        this.xPos = xPos;
        this.yPos = yPos;
        this.home = home;
    } // + Tile (updating)

    public boolean isHidden() {
        return hidden;
    } // + boolean isHidden

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    } // + void setHidden

    public boolean hasMine() {
        return mine;
    } // + boolean hasMine

    public void setMine(boolean mine) {
        this.mine = mine;
    } // + void setMine

    public boolean isFlagged() {
        return flagged;
    } // + boolean isFlagged

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    } // + void setFlagged

    public Boolean[] getAdjacentTiles() {
        return adjacentTiles;
    } // + short getAdjacentMines

    public void setAdjacentTiles(Boolean[] adjacentTiles) {
        this.adjacentTiles = adjacentTiles;
    } // + void setAdjacentMines

    public char getStatus() {
        return status;
    } // + char getStatus

    public void setStatus(char status) {
        this.status = status;
    } // + void setStatus

    public int getxPos() {
        return xPos;
    } // + int getxPos

    public void setxPos(int xPos) {
        this.xPos = xPos;
    } // + void setxPos

    public int getyPos() {
        return yPos;
    } // + int getyPos

    public void setyPos(int yPos) {
        this.yPos = yPos;
    } // + void setyPos

    public int getAdjacentMines() {
        return adjacentMines;
    } // + int getAdjacentMines

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    } // + void setAdjacentMines

    public Grid getHome() {
        return home;
    } // + Grid getHome

    public void setHome(Grid home) {
        this.home = home;
    } // + void setHome

    @Override
    public String toString() {
        return "T" + status + ": " + xPos + "x" + yPos;
    } // + String toString
    
    /**
     * Sets the Tile's 'hidden' variable to false if it is currently hidden.
     * Activates chord() if the Tile is not hidden
     */
    public void reveal() {
        checkAdjacent(getHome());
        
        if (isHidden() && !(isFlagged())) {
            setStatus((char)(getAdjacentMines() + 48));
            
            if (hasMine()) {
                setStatus('M');
            } // if (tile has a mine)
        } // if (tile is hidden)
        else if (!isHidden()) {
            chord(getHome());
        } // if (already revealed)
        
        if (getAdjacentMines() == 0) {
            chord(getHome());
        } // if (no adjacent mines)
        
        checkStatus();
    } // + void reveal
    
    /**
     * Checks each adjacent Tile's 'mine' and 'flagged' status. If the number of flags is the same as the number of mines, reveals all the unflagged tiles.
     * @param grid The Grid the method is to use as a reference
     */
    public void chord(Grid grid) {
        Boolean[] nearby = new Boolean[8];
        
        try {
            nearby[0] = grid.getArea()[xPos - 1][yPos - 1].isFlagged();
        } // try
        catch (ArrayIndexOutOfBoundsException e){
            nearby[0] = false;
        } // catch
        try {
            nearby[1] = grid.getArea()[xPos][yPos - 1].isFlagged();
        } // try
        catch (ArrayIndexOutOfBoundsException e){
            nearby[1] = false;
        } // catch
        try {
            nearby[2] = grid.getArea()[xPos + 1][yPos - 1].isFlagged();
        } // try
        catch (ArrayIndexOutOfBoundsException e){
            nearby[2] = false;
        } // catch
        try {
            nearby[3] = grid.getArea()[xPos - 1][yPos].isFlagged();
        } // try
        catch (ArrayIndexOutOfBoundsException e){
            nearby[3] = false;
        } // catch
        try {
            nearby[4] = grid.getArea()[xPos + 1][yPos].isFlagged();
        } // try
        catch (ArrayIndexOutOfBoundsException e){
            nearby[4] = false;
        } // catch
        try {
            nearby[5] = grid.getArea()[xPos - 1][yPos + 1].isFlagged();
        } // try
        catch (ArrayIndexOutOfBoundsException e){
            nearby[5] = false;
        } // catch
        try {
            nearby[6] = grid.getArea()[xPos][yPos + 1].isFlagged();
        } // try
        catch (ArrayIndexOutOfBoundsException e){
            nearby[6] = false;
        } // catch
        try {
            nearby[7] = grid.getArea()[xPos + 1][yPos + 1].isFlagged();
        } // try
        catch (ArrayIndexOutOfBoundsException e){
            nearby[7] = false;
        } // catch
        
        int counter = 0;
        for (Boolean item : nearby) {
            if (item) {
                counter++;
            } // if (mine adjacent)
        } // for (item in nearby)
        
        if (counter == getAdjacentMines()) {
            try {
                if (grid.getArea()[xPos - 1][yPos - 1].getStatus() == 'H' || grid.getArea()[xPos - 1][yPos - 1].getStatus() == 'B') {
                    grid.getArea()[xPos - 1][yPos - 1].reveal();
                } // if (surrounding item is not flagged)
            } // try
            catch (ArrayIndexOutOfBoundsException e){
                // No Tile; nothing to reveal
            } // catch
            try {
                if (grid.getArea()[xPos][yPos - 1].getStatus() == 'H' || grid.getArea()[xPos][yPos - 1].getStatus() == 'B') {
                    grid.getArea()[xPos][yPos - 1].reveal();
                } // if (surrounding item is not flagged)
            } // try
            catch (ArrayIndexOutOfBoundsException e){
                // Move along
            } // catch
            try {
                if (grid.getArea()[xPos + 1][yPos - 1].getStatus() == 'H' || grid.getArea()[xPos + 1][yPos - 1].getStatus() == 'B') {
                    grid.getArea()[xPos + 1][yPos - 1].reveal();
                } // if (surrounding item is not flagged)
            } // try
            catch (ArrayIndexOutOfBoundsException e){
                // Move along
            } // catch
            try {
                if (grid.getArea()[xPos - 1][yPos].getStatus() == 'H' || grid.getArea()[xPos - 1][yPos].getStatus() == 'B') {
                    grid.getArea()[xPos - 1][yPos].reveal();
                } // if (surrounding item is not flagged)
            } // try
            catch (ArrayIndexOutOfBoundsException e){
                // Move along
            } // catch
            try {
                if (grid.getArea()[xPos + 1][yPos].getStatus() == 'H' || grid.getArea()[xPos + 1][yPos].getStatus() == 'B') {
                    grid.getArea()[xPos + 1][yPos].reveal();
                } // if (surrounding item is not flagged)
            } // try
            catch (ArrayIndexOutOfBoundsException e){
                // Move along
            } // catch
            try {
                if (grid.getArea()[xPos - 1][yPos + 1].getStatus() == 'H' || grid.getArea()[xPos - 1][yPos + 1].getStatus() == 'B') {
                    grid.getArea()[xPos - 1][yPos + 1].reveal();
                } // if (surrounding item is not flagged)
            } // try
            catch (ArrayIndexOutOfBoundsException e){
                // Move along
            } // catch
            try {
                if (grid.getArea()[xPos][yPos + 1].getStatus() == 'H' || grid.getArea()[xPos][yPos + 1].getStatus() == 'B') {
                    grid.getArea()[xPos][yPos + 1].reveal();
                } // if (surrounding item is not flagged)
            } // try
            catch (ArrayIndexOutOfBoundsException e){
                // Move along
            } // catch
            try {
                if (grid.getArea()[xPos + 1][yPos + 1].getStatus() == 'H' || grid.getArea()[xPos + 1][yPos + 1].getStatus() == 'B') {
                    grid.getArea()[xPos + 1][yPos + 1].reveal();
                } // if (surrounding item is not flagged)
            } // try
            catch (ArrayIndexOutOfBoundsException e){
                // Move along
            } // catch
        } // if (same number of flags as adjacent mines)
        
    } // + void chord
    
    /**
     * Uses the Tile's status to switch its 'flagged' variable on or off
     */
    public void flag() {
        switch (getStatus()) {
            case 'H':
                setStatus('F');
                break;
            case 'F':
                setStatus('H');
                break;
            case 'B':
                setStatus('C');
                break;
            case 'C':
                setStatus('B');
                break;
            default:
                break;
        } // switch (status)
        checkStatus();
    } // + void flag
    
    /**
     * Changes the Tile's 'hidden', 'flagged', and 'mine' variables to reflect its status
     */
    public void checkStatus() {
        switch (getStatus()){
            case 'H':
                setHidden(true);
                setFlagged(false);
                setMine(false);
                break;
            case 'B':
                setHidden(true);
                setFlagged(false);
                setMine(true);
                break;
            case 'F':
                setHidden(true);
                setFlagged(true);
                setMine(false);
                break;
            case 'C':
                setHidden(true);
                setFlagged(true);
                setMine(true);
                break;
            case 'M':
                setHidden(false);
                setFlagged(false);
                setMine(true);
                break;
            default:
                setHidden(false);
                setFlagged(false);
                setMine(false);
        } // switch (getStatus)
    } // + void checkStatus
    
    /**
     * Checks the 'mine' status of each adjacent Tile, then counts the number of times it is true
     * @param grid The Grid the method is to use as a reference 
     */
    public void checkAdjacent(Grid grid) {
        Boolean[] nearby = new Boolean[8];
        
        try {
            nearby[0] = grid.getArea()[xPos - 1][yPos - 1].hasMine();
        } // try
        catch (ArrayIndexOutOfBoundsException e){
            nearby[0] = false;
        } // catch
        try {
            nearby[1] = grid.getArea()[xPos][yPos - 1].hasMine();
        } // try
        catch (ArrayIndexOutOfBoundsException e){
            nearby[1] = false;
        } // catch
        try {
            nearby[2] = grid.getArea()[xPos + 1][yPos - 1].hasMine();
        } // try
        catch (ArrayIndexOutOfBoundsException e){
            nearby[2] = false;
        } // catch
        try {
            nearby[3] = grid.getArea()[xPos - 1][yPos].hasMine();
        } // try
        catch (ArrayIndexOutOfBoundsException e){
            nearby[3] = false;
        } // catch
        try {
            nearby[4] = grid.getArea()[xPos + 1][yPos].hasMine();
        } // try
        catch (ArrayIndexOutOfBoundsException e){
            nearby[4] = false;
        } // catch
        try {
            nearby[5] = grid.getArea()[xPos - 1][yPos + 1].hasMine();
        } // try
        catch (ArrayIndexOutOfBoundsException e){
            nearby[5] = false;
        } // catch
        try {
            nearby[6] = grid.getArea()[xPos][yPos + 1].hasMine();
        } // try
        catch (ArrayIndexOutOfBoundsException e){
            nearby[6] = false;
        } // catch
        try {
            nearby[7] = grid.getArea()[xPos + 1][yPos + 1].hasMine();
        } // try
        catch (ArrayIndexOutOfBoundsException e){
            nearby[7] = false;
        } // catch
        
        setAdjacentTiles(nearby);
        
        int counter = 0;
        for (boolean item : nearby) {
            if (item) {
                counter++;
            } // if (mine adjacent)
        } // for (item in nearby)
        
        setAdjacentMines(counter);
    } // + void checkAdjacent
    
} // + class Tile
