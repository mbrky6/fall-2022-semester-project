package webfiles;
import java.util.Random;

/**
 * The game board of Minesweeper. This class creates and holds every Tile in the game, and can perform actions on all of them.
 * @author idont
 */
public class Grid {
    private int length; // How many tiles horizontal
    private int width; // How many tiles vertical
    private int mineCount;
    private Tile[][] area;

    /**
     * Full-arg constructor. This will never get used.
     * @param width Number of tiles long the Grid is
     * @param height Number of tiles wide the Grid is
     * @param mineCount Number of mines the Grid contains
     * @param area Array containing each Tile in the Grid
     */
    public Grid(int width, int height, int mineCount, Tile[][] area) {
        this.length = width;
        this.width = height;
        this.mineCount = mineCount;
        this.area = area;
    } // + Grid

    /**
     * Constructor for creating a Grid with a new width, height, and mine count. This is used when starting a new game.
     * @param width Number of tiles long the Grid is
     * @param height Number of tiles wide the Grid is
     * @param mineCount Number of mines the Grid contains
     */
    public Grid(int width, int height, int mineCount) {
        this.length = width;
        this.width = height;
        this.mineCount = mineCount;
    } // + Grid (no area)

    /**
     * Constructor for creating a Grid with only the length and width. This is used when performing an action.
     * @param length
     * @param width 
     */
    public Grid(int length, int width) {
        this.length = length;
        this.width = width;
    } // + Grid (length & width only)

    public int getLength() {
        return length;
    } // + int getLength

    public void setLength(int length) {
        this.length = length;
    } // + void setLength

    public int getWidth() {
        return width;
    } // + int getWidth

    public void setWidth(int width) {
        this.width = width;
    } // + void setWidth

    public int getMineCount() {
        return mineCount;
    } // + int getMineCount

    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    } // + void setMineCount

    public Tile[][] getArea() {
        return area;
    } // + Tile[][] getArea

    public void setArea(Tile[][] area) {
        this.area = area;
    } // + void setArea
    
    /**
     * Creates an array of Tiles, then randomly adds a pre-specified number of mines. This is used when starting a new game.
     * @return
     * @throws MineOverflowException 
     */
    public Tile[][] populate() throws MineOverflowException {
        Tile[][] space = new Tile[length][width];
        Random shuffler = new Random();
        int counter = 0;
        
        try {
            if (mineCount >= (length * width - 1)) {
                throw new MineOverflowException(mineCount, (length * width));
            } // if (more mines than tiles)
            if (mineCount < 1) {
                this.setMineCount(1);
            } // if (no mines)
        } // try
        catch(MineOverflowException e) {
            this.setMineCount((length * width - 1));
        } // catch
        
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; ++j) {
                space[i][j] = new Tile(true, false, false, i, j, this);
                if (space[i][j].isHidden()) {
                    space[i][j].setStatus('H');
                } // if (the new tile is hidden, which it is)
            } // for (length)
        } // for (width)
        
        while (counter < mineCount) {
            Tile sample = space[shuffler.nextInt(length)][shuffler.nextInt(width)];
            if (!sample.hasMine()) {
                sample.setMine(true);
                sample.setStatus('B');
                counter++;
            } // if (no mine on the randomly selected tile)
        } // for (mineCount)
        
        return space;
    } // + Tile[][] populate
    
    /**
     * Creates an array of Tiles out of an array of chars. This is used when performing an action.
     * @param statusList Char array sourced from a CSV file that contains the status of each Tile on the Grid
     * @return 
     */
    public Tile[][] repopulate(char[][] statusList) {
        Tile[][] space = new Tile[length][width];
        
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; ++j) {
                space[i][j] = new Tile(statusList[i][j], i, j, this);
                space[i][j].checkStatus();
            } // for (length)
        } // for (width)
        
        return space;
    } // + Tile[][] repopulate
    
    /**
     * Check each Tile in the 'area' array, then return the number of Tiles that have a mine
     * @return 
     */
    public int countMines() {
        int count = 0;
        
        for (Tile[] row : this.getArea()) {
            for (Tile item : row) {
                if (item.hasMine()) {
                    count++;
                } // if (item has mine)
            } // for (item in Row)
        } // for (row in Area)
        
        return count;
    } // + int countMines()
    
    /**
     * Check each Tile in the 'area' array, then return the number of Tiles that have a flag
     * @return 
     */
    public int countFlags() {
        int count = 0;
        
        for (Tile[] row : this.getArea()) {
            for (Tile item : row) {
                if (item.isFlagged()) {
                    count++;
                } // if (item has flag)
            } // for (item in Row)
        } // for (row in Area)
        
        return count;
    } // + int countMines()
    
} // + class Grid
