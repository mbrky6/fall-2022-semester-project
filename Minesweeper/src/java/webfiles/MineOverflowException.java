package webfiles;

/**
 * An Exception that can be thrown when a Grid creates a new 'area' array
 * If a Grid has more mines than Tiles, the populate() method will get stuck in an infinite loop trying to find an open space. To prevent that from happening, throw this exception.
 * @author idont
 */
public class MineOverflowException extends Exception {
    private int mineCount;
    private int areaSize;

    /**
     * Constructor the the exception
     * @param mineCount Number of mines in a Grid
     * @param areaSize Number of Tiles in a Grid
     */
    public MineOverflowException(int mineCount, int areaSize) {
        super("The number of mines (" + mineCount + ") exceeds the size of the game area (" + areaSize + " tiles).");
        this.mineCount = mineCount;
        this.areaSize = areaSize;
    } // + MineOverflowException

    public int getMineCount() {
        return mineCount;
    } // + int getMineCount

    public int getAreaSize() {
        return areaSize;
    } // + int getAreaSize
    
} //  + class MineOverflowException
