# semester-project
A customizable game of Minesweeper made in Java through JSP

Contains:
- index.jsp: web page that the game is played through
- minesweeper.css: stylesheet to render the game
- UpdateStatus.js: JavaScript file to communicate between the web page and the Java server
- Media folder: all the sound effects used in the game
- Graphics folder: all the images used in the game

- Tile.java: Java class to store the data of an individual tile
- Grid.java: Java class to store information about every tile in the game and perform actions on them
- GridData.java: Java Servlet to create and update the game grid as needed; data sent to and received from UpdateStatus.js
- MineOverflowException.java: custom exception to deal with an issue that occurs when too many mines are added to a Grid
