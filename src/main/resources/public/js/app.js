var gameModel;

//Get each board
var playerTable =   document.getElementById("PlayerBoard");
var computerTable = document.getElementById("ComputerBoard");

//If the table exists, iterate to get each square in (j+1,i+1) format and set an onclick
if (playerTable != null) {
    for (var i = 0; i < playerTable.rows.length; i++) {
        for (var j = 0; j < playerTable.rows[i].cells.length; j++) {
            playerTable.rows[i].cells[j].onclick = (function (i, j) {


                return function clickPlace() {
                    //Checks if the Computer has fired at all and if so then alert the user that you can't place or move ships
                    //after you have fired a single shot.
                    if(gameModel.computerMisses.length > 0 || gameModel.computerHits.length > 0){
                        alert("You have started the game.\nYou can't place down or move ships now.");
                    }


                    // This ajax call will asnychonously call the back end, and tell it where to place the ship,
                    // then get back a game model with the ship placed, and display the new model.
                    var request = $.ajax({
                        url: "/placeShip/"+$( "#shipSelec" ).val()+"/"+(j+1)+"/"+(i+1)+"/"+$( "#orientationSelec" ).val(),
                        method: "post",
                        data: JSON.stringify(gameModel),
                        contentType: "application/json; charset=utf-8",
                        dataType: "json"
                    });


                    //This will be called when the call is returned from the server.
                    request.done(function( currModel ) {
                        displayGameState(currModel);
                        gameModel = currModel;

                    });

                    // if there is a problem, and the back end does not respond, then an alert will be shown.
                    request.fail(function( jqXHR, textStatus ) {
                        alert( "Request failed: " + textStatus );
                    });
                };


            }(i, j));
        }
    }
}

//If the table exists, iterate to get each square in (j+1,i+1) format and set an onclick
if (computerTable != null) {
    for (var i = 0; i < computerTable.rows.length; i++) {
        for (var j = 0; j < computerTable.rows[i].cells.length; j++) {
            computerTable.rows[i].cells[j].onclick = (function (i, j) {


                return function clickFire() {
                    if(gameModel.playerAircraftCarrier.start.Across < 1){
                        alert( "Place Aircraft Carrier!!!\nBefore you can fire.");
                    }
                    if(gameModel.playerBattleship.start.Across < 1){
                        alert( "Place Battleship!!!\nBefore you can fire.");
                    }
                    if(gameModel.playerCruiser.start.Across < 1){
                        alert( "Place Cruiser!!!\nBefore you can fire.");
                    }
                    if(gameModel.playerDestroyer.start.Across < 1){
                        alert( "Place Destroyer!!!\nBefore you can fire.");
                    }
                    if(gameModel.playerSubmarine.start.Across < 1){
                        alert( "Place Submarine!!!\nBefore you can fire.");
                    }


                    var request = $.ajax({
                        url: "/fire/"+(j+1)+"/"+(i+1),
                        method: "post",
                        data: JSON.stringify(gameModel),
                        contentType: "application/json; charset=utf-8",
                        dataType: "json"
                    });

                    request.done(function( currModel ) {
                        displayGameState(currModel);
                        gameModel = currModel;

                    });

                    request.fail(function( jqXHR, textStatus ) {
                        alert( "Request failed: " + textStatus );
                    });
                };


            }(i, j));
        }
    }
}

//This function will be called once the page is loaded.  It will get a new game model from the back end, and display it.
$( document ).ready(function() {

  $.getJSON("model", function( json ) {
    displayGameState(json);
    gameModel = json;
  });
});
//$( document ).ready(function() {
//
//  $.getJSON("model", function( json ) {
//    displayGameState(currModel);
//    gameModel = json;
//   });
//});

function placeShip() {

    //Checks if the Computer has fired at all and if so then alert the user that you can't place or move ships
    //after you have fired a single shot.
    if(gameModel.computerMisses.length > 0 || gameModel.computerHits.length > 0){
        alert("You have started the game.\nYou can't place down or move ships now.");
    }


   // This ajax call will asnychonously call the back end, and tell it where to place the ship, then get back a game model with the ship placed, and display the new model.
   var request = $.ajax({
     url: "/placeShip/"+$( "#shipSelec" ).val()+"/"+$( "#rowSelec" ).val()+"/"+$( "#colSelec" ).val()+"/"+$( "#orientationSelec" ).val(),
     method: "post",
     data: JSON.stringify(gameModel),
     contentType: "application/json; charset=utf-8",
     dataType: "json"
   });

   //This will be called when the call is returned from the server.
   request.done(function( currModel ) {
     displayGameState(currModel);
     gameModel = currModel;

   });

   // if there is a problem, and the back end does not respond, then an alert will be shown.
   request.fail(function( jqXHR, textStatus ) {
     alert( "Request failed: " + textStatus );
   });
}

//Similar to placeShip, but instead it will fire at a location the user selects.
function fire(){
    //Checks if each ship has been placed and if not then alert the user that he/she should place
    //the individual ship before he/she can fire.
    if(gameModel.playerAircraftCarrier.start.Across < 1){
        alert( "Place Aircraft Carrier!!!\nBefore you can fire.");
    }
    if(gameModel.playerBattleship.start.Across < 1){
        alert( "Place Battleship!!!\nBefore you can fire.");
    }
    if(gameModel.playerCruiser.start.Across < 1){
        alert( "Place Cruiser!!!\nBefore you can fire.");
    }
    if(gameModel.playerDestroyer.start.Across < 1){
        alert( "Place Destroyer!!!\nBefore you can fire.");
    }
    if(gameModel.playerSubmarine.start.Across < 1){
        alert( "Place Submarine!!!\nBefore you can fire.");
    }


   var request = $.ajax({
     url: "/fire/"+$( "#rowFire" ).val()+"/"+$( "#colFire" ).val(),
     method: "post",
     data: JSON.stringify(gameModel),
     contentType: "application/json; charset=utf-8",
     dataType: "json"
   });

   request.done(function( currModel ) {
     displayGameState(currModel);
     gameModel = currModel;

   });

   request.fail(function( jqXHR, textStatus ) {
     alert( "Request failed: " + textStatus );
   });

}

//This function will display the game model.  It displays the ships on the users board, and then shows where there have been hits and misses on both boards.
function displayGameState(gameModel){
$( '#MyBoard td'  ).css("background-color", "blue");
$( '#TheirBoard td'  ).css("background-color", "blue");

// EDITED THIS
displayShip(gameModel.playerAircraftCarrier);
displayShip(gameModel.playerBattleship);
displayShip(gameModel.playerCruiser);
displayShip(gameModel.playerDestroyer);
displayShip(gameModel.playerSubmarine);
// /EDITED THIS

for (var i = 0; i < gameModel.computerMisses.length; i++) {
   $( '#TheirBoard #' + gameModel.computerMisses[i].Down + '_' + gameModel.computerMisses[i].Across ).css("background-color", "green");
}
for (var i = 0; i < gameModel.computerHits.length; i++) {
   $( '#TheirBoard #' + gameModel.computerHits[i].Down + '_' + gameModel.computerHits[i].Across ).css("background-color", "red");
}

for (var i = 0; i < gameModel.playerMisses.length; i++) {
   $( '#MyBoard #' + gameModel.playerMisses[i].Down + '_' + gameModel.playerMisses[i].Across ).css("background-color", "green");
}
for (var i = 0; i < gameModel.playerHits.length; i++) {
   $( '#MyBoard #' + gameModel.playerHits[i].Down + '_' + gameModel.playerHits[i].Across ).css("background-color", "red");
}



}


//This function will display a ship given a ship object in JSON
function displayShip(ship){
 startCoordAcross = ship.start.Across;
 startCoordDown = ship.start.Down;
 endCoordAcross = ship.end.Across;
 endCoordDown = ship.end.Down;
 if(startCoordAcross > 0){
    if(startCoordAcross == endCoordAcross){
        for (i = startCoordDown; i <= endCoordDown; i++) {
            $( '#MyBoard #'+i+'_'+startCoordAcross  ).css("background-color", "yellow");
        }
    } else {
        for (i = startCoordAcross; i <= endCoordAcross; i++) {
            $( '#MyBoard #'+startCoordDown+'_'+i  ).css("background-color", "yellow");
        }
    }
 }
}
