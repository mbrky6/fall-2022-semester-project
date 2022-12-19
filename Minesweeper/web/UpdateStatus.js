// Audio
const flagSound = new Audio("media/flag.mp3");
const revealSound = new Audio("media/reveal.mp3");
const loseSound = new Audio("media/lose.mp3"); // Change "media/lose.mp3" to "media/lose_funny.mp3" for a surprise
const winSound = new Audio("media/win.mp3");

function getTiles() {
    let length = parseInt(Math.abs(document.getElementById("length").value));
    let width = parseInt(Math.abs(document.getElementById("width").value));
    let mines = parseInt(Math.abs(document.getElementById("mines").value));
    let board = document.getElementById("GameArea");
    
    let request = new XMLHttpRequest();
    request.open("GET", "http://localhost:8080/Minesweeper/GridData?requestType=NewGame&length=" + length + "&width=" + width +"&mines=" + mines, true);
    request.send();
    request.onreadystatechange = function() {
        if(request.readyState === 4 && request.status === 200){
            let payload = request.responseText;
            
            let area = payload.split("\n");
            
            loseSound.pause();
            winSound.pause();
            
            flagCount = mines;

            // Clear old game
            board.innerHTML = "";
            document.getElementById("dialogue").innerHTML = "";
            
            for (let row = 0; row < width; row++) {
                board.innerHTML += "<tr id=\"R" + row + "\">";
                for (let col = 0; col < length; col++) {
                    document.getElementById("R" + row).innerHTML += "<td class=\"tile\" name=\"" + row + "T" + col + "T\" id=\"T" + area[col][row] + "\" onclick=\"reveal(event)\" oncontextmenu=\"flag(event)\"></td>";
                } // for col in row
                board.innerHTML += "</tr>";
            } // for row in grid
            
            document.getElementById("data-x").setAttribute("value", length);
            document.getElementById("data-y").setAttribute("value", width);
        } // if (the webpage is ready)
    }; // function
} // function getTiles

function reveal(e) {
    let length = parseInt(document.getElementById("data-x").getAttribute("value"));
    let width = parseInt(document.getElementById("data-y").getAttribute("value"));
    let board = document.getElementById("GameArea");
    let gameOver = true;
    
    let request = new XMLHttpRequest();
    request.open("GET", "http://localhost:8080/Minesweeper/GridData?requestType=Reveal&tile=" + e.srcElement.attributes.name.value + "&length=" + length + "&width=" + width, true);
    request.send();
    request.onreadystatechange = function() {
        if(request.readyState === 4 && request.status === 200){
            let payload = request.responseText;
            
            revealSound.pause();
            revealSound.load();
            revealSound.play();
            
            // Check if the game is over
            for (let item in payload) {
                if (payload[item] === 'H') {
                    gameOver = false;
                } // if (safe tiles still hidden)
                
                if (payload[item] === 'M') {
                    lose();
                    gameOver = true;
                    break;
                } // if (mine revealed)
                
                if (item >= (payload.length - 1)) {
                    if (gameOver) {
                        win();
                    } // if (gameOver)
                } // if (all tiles checked)
            } // for (item in payload)
            
            let area = payload.split("\n");

            // Clear old board
            board.innerHTML = "";
            
            for (let row = 0; row < width; row++) {
                board.innerHTML += "<tr id=\"R" + row + "\">";
                for (let col = 0; col < length; col++) {
                    if (!gameOver) {
                        document.getElementById("R" + row).innerHTML += "<td class=\"tile\" name=\"" + row + "T" + col + "T\" id=\"T" + area[col][row] + "\" onclick=\"reveal(event)\" oncontextmenu=\"flag(event)\"></td>";
                    } // if (game continues)
                    else {
                        document.getElementById("R" + row).innerHTML += "<td class=\"tile\" name=\"" + row + "T" + col + "T\" id=\"T" + area[col][row] + "\" onclick=\"return false\" oncontextmenu=\"return false\"></td>";
                    } // else
                } // for col in row
                board.innerHTML += "</tr>";
            } // for row in grid
        } // if (the webpage is ready)
    }; // function
} // function reveal

function flag(e) {
    let length = parseInt(document.getElementById("data-x").getAttribute("value"));
    let width = parseInt(document.getElementById("data-y").getAttribute("value"));
    let board = document.getElementById("GameArea");
    
    let request = new XMLHttpRequest();
    request.open("GET", "http://localhost:8080/Minesweeper/GridData?requestType=Flag&tile=" + e.srcElement.attributes.name.value + "&length=" + length + "&width=" + width, true);
    request.send();
    request.onreadystatechange = function() {
        if(request.readyState === 4 && request.status === 200){
            let payload = request.responseText;
            
            flagSound.pause();
            flagSound.load();
            flagSound.play();
            
            let area = payload.split("\n");

            // Clear old board
            board.innerHTML = "";
            
            for (let row = 0; row < width; row++) {
                board.innerHTML += "<tr id=\"R" + row + "\">";
                for (let col = 0; col < length; col++) {
                    document.getElementById("R" + row).innerHTML += "<td class=\"tile\" name=\"" + row + "T" + col + "T\" id=\"T" + area[col][row] + "\" onclick=\"reveal(event)\" oncontextmenu=\"flag(event)\"></td>";
                } // for col in row
                board.innerHTML += "</tr>";
            } // for row in grid
        } // if (the webpage is ready)
    }; // function
} // function flag

function lose() {
    console.log("BOOM! Big ol' explosion!");
    loseSound.load();
    loseSound.play();
    document.getElementById("dialogue").innerHTML = "<p class=\"lost\">Ouch, you lost!</p>";
} // function lose()

function win() {
    console.log("Congraatuion you are win!");
    winSound.load();
    winSound.play();
    document.getElementById("dialogue").innerHTML = "<p class=\"won\">Congratulations, you won!</p>";
} // function win()