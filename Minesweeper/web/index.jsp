<%-- 
    Document   : index
    Created on : Nov 1, 2022, 1:52:21 PM
    Author     : Mark Berkey
--%>

<%@page import= "webfiles.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Minesweeper</title>
        <link rel="stylesheet" type="text/css" href="minesweeper.css">
        <script src="UpdateStatus.js"></script>
    </head>
    
    <body>
        <header>
            <h1>Minesweeper: Java Edition</h1>
        </header>
        
        <main>
            <div id="controls"> <!-- Fix up in the CSS when done -->
                <input type="number" placeholder="Width" id="length" name="length"/>
                <input type="number" placeholder="Height" id="width" name="width"/>
                <input type="number" placeholder="Number of mines" id="mines" name="mines"/>
                <input type="submit" value="Start Game" onclick="getTiles()"/>
            </div>
            
            <table>
                <tbody id="GameArea" oncontextmenu="return false"></tbody>
            </table>
            
            <!--
Cheat sheet:
H if hidden and no mine
B if hidden and a mine
F if flagged and no mine
C if flagged and a mine
M if mine revealed
0-8 depending on the number of nearby mines
-->
            
            <div id="dialogue"></div>
            
            <div id="tip">
                <p>Traditional game board sizes:</p>
                <ul>
                    <li>9:9:10 = Beginner</li>
                    <li>16:16:40 = Intermediate</li>
                    <li>30:16:99 = Expert</li>
                </ul>
            </div>
        </main>
        
        <footer>
            <div id="data-x" value="0"></div>
            <div id="data-y" value="0"></div>
            <p>This game was made by Mark Berkey as part of a final project in SDEV 200.</p>
        </footer>
    </body>
</html>
