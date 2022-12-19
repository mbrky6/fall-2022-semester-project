package webfiles;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Java Servlet that creates and updates the current Minesweeper game
 * @author idont
 */
@WebServlet(name = "GridData", urlPatterns = {"/GridData"})
public class GridData extends HttpServlet {

    /**
     * Create a Grid either from scratch or from a data file, update the data in the Grid depending on the action, and record each Tile on the Grid's status to a CSV file for later use
     * If starting a new game, create a Grid using the populate() method
     * If performing an action, create a Grid using the repopulate() method, then determine its mine and flag counts
     * If revealing a Tile, locate the Tile using the coordinates provided by the JS function, and use the reveal() method on it
     * If flagging a Tile, locate the Tile using the coordinates provided by the JS function, and use the flag() method on it
     * After the actions are complete, record the 1-character status of each Tile into a CSV file to be read when the next action is performed
     * @param request The information provided by the JS file (Grid size, mine count or Tile name)
     * @param response The information provided to the JS file (The CSV containing all the status info)
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        
        File csvStatus = new File("status.csv");
        Grid grid;
        int length = Integer.parseInt(request.getParameter("length"));
        int width = Integer.parseInt(request.getParameter("width"));
        int mines;
        int flags;
        
        if (request.getParameter("requestType").equals("NewGame")) {
            mines = Integer.parseInt(request.getParameter("mines"));

            grid = new Grid(length, width, mines);
            try {
                grid.setArea(grid.populate());
            } // try
            catch (MineOverflowException ex) {
            } // catch
            
            flags = 0;
        } // if (starting a new game)
        
        else {
            Scanner readStatus = new Scanner(csvStatus);
            
            List<String> lines = new ArrayList<>();
            char[][] board = new char[length][width];
            
            while (readStatus.hasNext()) {
                lines.add(readStatus.nextLine());
            } // while (more data in status.csv)
            
            for (int row = 0; row < lines.size(); row++) {
                
                for (int col = 0; col < lines.get(0).length(); col++) {
                    if (lines.get(row).charAt(col) != ',') {
                        board[row][col / 2] = lines.get(row).charAt(col);
                    } // if (character is not a comma)
                } // for (character in line)
            } // for (line in lines)
            
            grid = new Grid(length, width);
            grid.setArea(grid.repopulate(board));
            mines = grid.countMines();
            flags = grid.countFlags();
            grid.setMineCount(mines);
        } // else
        
        
        if (request.getParameter("requestType").equals("Reveal")) {
            String[] tileName = request.getParameter("tile").split("T");
            
            int x = Integer.parseInt(tileName[1]);
            int y = Integer.parseInt(tileName[0]);
            
            grid.getArea()[x][y].reveal();
        } // if (reveal() called)
        
        
        if (request.getParameter("requestType").equals("Flag")) {
            String[] tileName = request.getParameter("tile").split("T");
            
            int x = Integer.parseInt(tileName[1]);
            int y = Integer.parseInt(tileName[0]);
            
            if (flags < mines || grid.getArea()[x][y].isFlagged()) {
                grid.getArea()[x][y].flag();
            } // if (flags < mines)
        } // if (flag() called)
        
        
        try (FileWriter writerStatus = new FileWriter(csvStatus)) {
            for (Tile[] row : grid.getArea()) {
                StringBuilder line = new StringBuilder();
                for (Tile col : row) {
                    line.append(col.getStatus());
                    line.append(',');
                } // for (item in the row)
                line.append('\n');
                writerStatus.write(line.toString());
            } // for (row in the grid)
        } // try
        
        try {
            try (PrintWriter writer = response.getWriter()) {

                Scanner readStatus = new Scanner(csvStatus);
                readStatus.useDelimiter(",");

                while (readStatus.hasNext()) {
                    writer.write(readStatus.next());
                } // while (more data in the CSV file)
            } // try

        } //try
        catch (IOException ex) {
            System.out.println("File Not Found");

        } // catch
        
    } // | void doGet
} // + class GridData extends HttpServlet
