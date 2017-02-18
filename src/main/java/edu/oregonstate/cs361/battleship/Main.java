package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import spark.Request;

import java.util.List;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        //This will allow us to server the static pages such as index.html, app.js, etc.
        staticFiles.location("/public");

        //This will listen to GET requests to /model and return a clean new model
        get("/model", (req, res) -> newModel());
        //This will listen to POST requests and expects to receive a game model, as well as location to fire to
        post("/fire/:row/:col", (req, res) -> fireAt(req));
        //This will listen to POST requests and expects to receive a game model, as well as location to place the ship
        post("/placeShip/:id/:row/:col/:orientation", (req, res) -> placeShip(req));

        //System.out.print("5");
    }

    //This function should return a new model
    static String newModel() {

        // make new model, make gson object, convert model to json using gson
        BattleshipModel test = new BattleshipModel();
        Gson gson = new Gson();
        String model = gson.toJson(test);
        //System.out.println(model);
        return model;
    }

    //This function should accept an HTTP request and deseralize it into an actual Java object.
    public static BattleshipModel getModelFromReq(Request req){
        String data = req.body();
        Gson gson = new Gson();
        BattleshipModel ship = gson.fromJson(data, BattleshipModel.class);
        return ship;
    }

    //This controller should take a json object from the front end, and place the ship as requested, and then return the object.
    public static String placeShip(Request req) {
        BattleshipModel model = getModelFromReq(req); //is this correct?
        String id, orientation, row, col;
        id = req.params("id");  //name of what ship with which player in front of it
        orientation = req.params("orientation"); //horizontal/vertical
        row = req.params("row");    //row #
        col = req.params("col");    //col #

        List PlayerFireMiss = model.getPlayerMisses();
        List PlayerFireHit = model.getPlayerHits();


        if(!PlayerFireHit.isEmpty() || !PlayerFireMiss.isEmpty()) {//list isn't empty
            Gson gson = new Gson();
            return gson.toJson(model);
        }

        Ship PAircraftCarrier = model.getPlayerAircraftCarrier();
        Ship PBattleship = model.getPlayerBattleship();
        Ship PCruiser = model.getPlayerCruiser();
        Ship PDestroyer = model.getPlayerDestroyer();
        Ship PSubmarine = model.getPlayerSubmarine();

        Ship CAircraftCarrier = model.getComputerAircraftCarrier();
        Ship CBattleship = model.getComputerBattleship();
        Ship CCruiser = model.getComputerCruiser();
        Ship CDestroyer = model.getComputerDestroyer();
        Ship CSubmarine = model.getComputerSubmarine();

        int rows;
        rows = Integer.parseInt(row);
        int column;
        column = Integer.parseInt(col);

        Ship current = model.getShipByID(id);

        current.setEnd(-1,-1);
        current.setStart(-1,-1);

        int size = current.getLength();
        int stop = 0;
        Point cord = new Point();

        if (orientation.equals("horizontal") && (rows + size - 1) < 11 && rows > 0 && column < 11 && column > 0) {
            for (int i = rows; i < (rows + size); i++) {
                cord.setAcross(i);
                cord.setDown(column);

                //if ship lands on another ship then
                if (Hit(PAircraftCarrier.getStart(), PAircraftCarrier.getEnd(), cord)) {
                    stop = 1;
                } else if (Hit(PBattleship.getStart(), PBattleship.getEnd(), cord)) {
                    stop = 1;
                } else if (Hit(PCruiser.getStart(), PCruiser.getEnd(), cord)) {
                    stop = 1;
                } else if (Hit(PDestroyer.getStart(), PDestroyer.getEnd(), cord)) {
                    stop = 1;
                } else if (Hit(PSubmarine.getStart(), PSubmarine.getEnd(), cord)) {
                    stop = 1;
                }
            }

            if (stop == 0) {
                current.setStart(rows, column);
                current.setEnd(rows + size - 1, column);
            }

        } else if (orientation.equals("vertical") && rows < 11 && rows > 0 && column + size - 1 < 11 && column > 0) {
            for (int k = column; k < (column + size); k++) {
                cord.setAcross(rows);
                cord.setDown(k);

                //if ship lands on another ship then
                if (Hit(PAircraftCarrier.getStart(), PAircraftCarrier.getEnd(), cord)) {
                    stop = 1;
                } else if (Hit(PBattleship.getStart(), PBattleship.getEnd(), cord)) {
                    stop = 1;
                } else if (Hit(PCruiser.getStart(), PCruiser.getEnd(), cord)) {
                    stop = 1;
                } else if (Hit(PDestroyer.getStart(), PDestroyer.getEnd(), cord)) {
                    stop = 1;
                } else if (Hit(PSubmarine.getStart(), PSubmarine.getEnd(), cord)) {
                    stop = 1;
                }
            }

            if (stop == 0) {
                current.setStart(rows, column);
                current.setEnd(rows, column + size - 1);
            }
        }

//        Point computerStart = new Point();
//        Point computerEnd = new Point();
//        int computer_x;
//        int computer_y;
//        int horizontal;
//
//        if (stop == 0) {
//            if (id.toLowerCase().contains("aircraftcarrier")) {
//                while (stop == 0) {
//                    computer_x = (int) (Math.random() * 6 + 1);
//                    computer_y = (int) (Math.random() * 10 + 5);
//                    horizontal = (int) (Math.random() * 1);
//
//                    computerStart = new Point(computer_x, computer_y);
//
//                    if (horizontal == 1) {
//                        computerEnd.setAcross(computer_x + 4);
//                        computerEnd.setDown(computer_y);
//                    }else{
//                        computerEnd.setAcross(computer_x);
//                        computerEnd.setDown(computer_y - 4);
//                    }
//
//                    //Test if it's placed on any other ship and if no then stop = 1
//                    //set starting point and ending point
//                    if(ComputerMatchShipPlacement( model, computerStart, computerEnd, horizontal)) {
//                        CAircraftCarrier.setStart(computerStart.getAcross(),computerStart.getDown());
//                        CAircraftCarrier.setEnd(computerStart.getAcross(),computerStart.getDown());
//                        stop = 1;
//                    }
//                }
//            } else if (id.toLowerCase().contains("battleship")) {
//                while (stop == 0) {
//                    computer_x = (int) (Math.random() * 7 + 1);
//                    computer_y = (int) (Math.random() * 10 + 4);
//                    horizontal = (int) (Math.random() * 1);
//
//                    if (horizontal == 1) {
//                        computerEnd.setAcross(computer_x + 3);
//                        computerEnd.setDown(computer_y);
//                    }else{
//                        computerEnd.setAcross(computer_x);
//                        computerEnd.setDown(computer_y - 3);
//                    }
//
//                    //Test if it's placed on any other ship and if no then stop = 1
//                    //set starting point and ending point
//                    if(ComputerMatchShipPlacement( model, computerStart, computerEnd, horizontal)) {
//                        CBattleship.setStart(computerStart.getAcross(),computerStart.getDown());
//                        CBattleship.setEnd(computerStart.getAcross(),computerStart.getDown());
//                        stop = 1;
//                    }
//                }
//
//            } else if (id.toLowerCase().contains("cruiser")) {
//                while (stop == 0) {
//                    computer_x = (int) (Math.random() * 8 + 1);
//                    computer_y = (int) (Math.random() * 10 + 3);
//                    horizontal = (int) (Math.random() * 1);
//
//                    computerStart.setDown(computer_y);
//                    computerStart.setAcross(computer_x);
//
//                    if (horizontal == 1) {
//                        computerEnd.setAcross(computer_x + 2);
//                        computerEnd.setDown(computer_y);
//                    }else{
//                        computerEnd.setAcross(computer_x);
//                        computerEnd.setDown(computer_y - 2);
//                    }
//
//                    //Test if it's placed on any other ship and if no then stop = 1
//                    //set starting point and ending point
//                    if(ComputerMatchShipPlacement( model, computerStart, computerEnd, horizontal)) {
//                        CCruiser.setStart(computerStart.getAcross(),computerStart.getDown());
//                        CCruiser.setEnd(computerStart.getAcross(),computerStart.getDown());
//                        stop = 1;
//                    }
//                }
//            } else if (id.toLowerCase().contains("destroyer")) {
//                while (stop == 0) {
//                    computer_x = (int) (Math.random() * 9 + 1);
//                    computer_y = (int) (Math.random() * 10 + 3);
//                    horizontal = (int) (Math.random() * 1);
//
//                    computerStart.setDown(computer_y);
//                    computerStart.setAcross(computer_x);
//
//                    if (horizontal == 1) {
//                        computerEnd.setAcross(computer_x + 2);
//                        computerEnd.setDown(computer_y);
//                    }else{
//                        computerEnd.setAcross(computer_x);
//                        computerEnd.setDown(computer_y - 2);
//                    }
//
//                    //Test if it's placed on any other ship and if no then stop = 1
//                    //set starting point and ending point
//                    if(ComputerMatchShipPlacement( model, computerStart, computerEnd, horizontal)) {
//                        CDestroyer.setStart(computerStart.getAcross(),computerStart.getDown());
//                        CDestroyer.setEnd(computerStart.getAcross(),computerStart.getDown());
//                        stop = 1;
//                    }
//                }
//            }
//        } else if (id.toLowerCase().contains("submarine")) {
//            while (stop == 0) {
//                computer_x = (int) (Math.random() * 8 + 1);
//                computer_y = (int) (Math.random() * 10 + 2);
//                horizontal = (int) (Math.random() * 1);
//
//                computerStart.setDown(computer_y);
//                computerStart.setAcross(computer_x);
//
//
//                if (horizontal == 1) {
//                    computerEnd.setAcross(computer_x + 1);
//                    computerEnd.setDown(computer_y);
//                }else{
//                    computerEnd.setAcross(computer_x);
//                    computerEnd.setDown(computer_y - 1);
//                }
//
//                //Test if it's placed on any other ship and if no then stop = 1
//                //set starting point and ending point
//                if(ComputerMatchShipPlacement( model, computerStart, computerEnd, horizontal)) {
//                    CSubmarine.setStart(computerStart.getAcross(),computerStart.getDown());
//                    CSubmarine.setEnd(computerStart.getAcross(),computerStart.getDown());
//                    stop = 1;
//                }
//            }
//        }

        Gson gson = new Gson();
        return gson.toJson(model);

    }

    //Similar to placeShip, but with firing.
    private static String fireAt(Request req) {

        // Generate model from json, get coordinates from fire request
        BattleshipModel model = getModelFromReq(req);

        String X = req.params("row");
        String Y = req.params("col");
        int row = Integer.parseInt(X);
        int col = Integer.parseInt(Y);
        row = Integer.parseInt(X);
        col = Integer.parseInt(Y);

        // Make point object from coordinates
        Point FireSpot = new Point(row,col);

        // Grab player and computer ships from current model
        Ship PAircraftCarrier = model.getPlayerAircraftCarrier();
        Ship PBattleship = model.getPlayerBattleship();
        Ship PCruiser = model.getPlayerCruiser();
        Ship PDestroyer = model.getPlayerDestroyer();
        Ship PSubmarine = model.getPlayerSubmarine();

        Ship CAircraftCarrier = model.getComputerAircraftCarrier();
        Ship CBattleship = model.getComputerBattleship();
        Ship CCruiser = model.getComputerCruiser();
        Ship CDestroyer = model.getComputerDestroyer();
        Ship CSubmarine = model.getComputerSubmarine();




        if(PAircraftCarrier.getStart().getAcross() < 1){
            Gson gson = new Gson();
            String jsonobject = gson.toJson(model);
            return jsonobject;
        }else if(PBattleship.getStart().getAcross() < 1){
            Gson gson = new Gson();
            String jsonobject = gson.toJson(model);
            return jsonobject;
        }else if(PCruiser.getStart().getAcross() < 1){
            Gson gson = new Gson();
            String jsonobject = gson.toJson(model);
            return jsonobject;
        }else if(PDestroyer.getStart().getAcross() < 1){
            Gson gson = new Gson();
            String jsonobject = gson.toJson(model);
            return jsonobject;
        }else if(PSubmarine.getStart().getAcross() < 1){
            Gson gson = new Gson();
            String jsonobject = gson.toJson(model);
            return jsonobject;
        }

        // The following branch tree checks if a point fired at BY A PLAYER has hit a COMPUTER ship and adds the point to the array of hits if so
        if( Hit( CAircraftCarrier.getStart(), CAircraftCarrier.getEnd(), FireSpot ) ){
            model.addPointtoArray(FireSpot, model.getComputerHits());
        }
        else if ( Hit( CBattleship.getStart(), CBattleship.getEnd(), FireSpot ) ){
            model.addPointtoArray(FireSpot, model.getComputerHits());
        }
        else if ( Hit( CCruiser.getStart(), CCruiser.getEnd(), FireSpot  ) ){
            model.addPointtoArray(FireSpot, model.getComputerHits());
        }
        else if ( Hit( CDestroyer.getStart(), CDestroyer.getEnd(), FireSpot  ) ){
            model.addPointtoArray(FireSpot, model.getComputerHits());
        }
        else if ( Hit( CSubmarine.getStart(), CSubmarine.getEnd(), FireSpot  ) ){
            model.addPointtoArray(FireSpot, model.getComputerHits());
        }
        else{   // No hits on any ships, adds point to array of misses instead
            model.addPointtoArray(FireSpot, model.getComputerMisses());
        }


        // Create two random coordinates for computer to shoot at and make a point object of them
        int shootX = (int )(Math.random() * 10 + 1);
        int shootY = (int )(Math.random() * 10 + 1);
        Point FireSpotComputer = new Point(shootX, shootY);

        // Following branch tree checks if a point fired at BY THE COMPUTER has hit a PLAYER ship and adds the point to the array of hits if so
        if( Hit( PAircraftCarrier.getStart(), PAircraftCarrier.getEnd(), FireSpotComputer ) ){
            model.addPointtoArray(FireSpotComputer, model.getPlayerHits());
        }
        else if ( Hit( PBattleship.getStart(), PBattleship.getEnd(), FireSpotComputer  ) ){
            model.addPointtoArray(FireSpotComputer, model.getPlayerHits());
        }
        else if ( Hit( PCruiser.getStart(), PCruiser.getEnd(), FireSpotComputer  ) ){
            model.addPointtoArray(FireSpotComputer, model.getPlayerHits());
        }
        else if ( Hit( PDestroyer.getStart(), PDestroyer.getEnd(), FireSpotComputer  ) ){
            model.addPointtoArray(FireSpotComputer, model.getPlayerHits());
        }
        else if ( Hit( PSubmarine.getStart(), PSubmarine.getEnd(), FireSpotComputer  ) ){
            model.addPointtoArray(FireSpotComputer, model.getPlayerHits());
        }
        else{   // No hits on any ships, adds point to array of misses instead
            model.addPointtoArray(FireSpotComputer, model.getPlayerMisses());
        }

        Gson gson = new Gson();
        String jsonobject = gson.toJson(model);
        return jsonobject;
    }

    public static boolean Hit(Point shipStart, Point shipEnd, Point shotPoint){

        if(shipStart.getDown() == shipEnd.getDown()){     // if start and end on same y coordinate, ship is horizontal
            int y = shipStart.getDown();
            for (int x = shipStart.getAcross(); x <= shipEnd.getAcross(); x++){  // loop from left to right of ship position

                if(x == shotPoint.getAcross() && y == shotPoint.getDown())
                    return true;   // if the coordinates of current point match shot, you hit!
            }
            return false; // check all points ship lies on, found no match to shot point
        }

        else if (shotPoint.getAcross() == shipStart.getAcross()) {      // if start and end on same x coordinate, ship is vertical
            int x = shipStart.getAcross();
            for (int y = shipStart.getDown(); y <= shipEnd.getDown(); y++) {

                if (x == shotPoint.getAcross() && y == shotPoint.getDown())
                    return true;   // if the coordinates of current point match shot, you hit!
            }
            return false; // check all points ship lies on, found no match to shot point
        }

        return false; // points given are not horizontal or vertical and not valid, can't hit diagonally
    }


//    private static boolean ComputerMatchShipPlacement(BattleshipModel model, Point BoatStart, Point BoatEnd, int horizontal){
//        Ship CAircraftCarrier = model.getComputerAircraftCarrier();
//        Ship CBattleship = model.getComputerBattleship();
//        Ship CCruiser = model.getComputerCruiser();
//        Ship CDestroyer = model.getComputerDestroyer();
//        Ship CSubmarine = model.getComputerSubmarine();
//
//        Point cord = BoatStart;
//
//        if(horizontal == 1) {   //  Horizontal
//            for (int i = BoatStart.getAcross(); i < BoatEnd.getAcross(); i++) {
//                cord.setAcross(i);
//                cord.setDown(BoatStart.getDown());
//
//                //if ship lands on another ship then
//                if (Hit(CAircraftCarrier.getStart(), CAircraftCarrier.getEnd(), cord)) {
//                    return false;
//                } else if (Hit(CBattleship.getStart(), CBattleship.getEnd(), cord)) {
//                    return false;
//                } else if (Hit(CCruiser.getStart(), CCruiser.getEnd(), cord)) {
//                    return false;
//                } else if (Hit(CDestroyer.getStart(), CDestroyer.getEnd(), cord)) {
//                    return false;
//                } else if (Hit(CSubmarine.getStart(), CSubmarine.getEnd(), cord)) {
//                    return false;
//                }
//            }
//        }else{  //      Vertical
//            for (int i = BoatStart.getDown(); i < BoatEnd.getDown(); i--) { //vertical and goes down
//                cord.setAcross(BoatStart.getAcross());
//                cord.setDown(i);
//
//                //if ship lands on another ship then
//                if (Hit(CAircraftCarrier.getStart(), CAircraftCarrier.getEnd(), cord)) {
//                    return false;
//                } else if (Hit(CBattleship.getStart(), CBattleship.getEnd(), cord)) {
//                    return false;
//                } else if (Hit(CCruiser.getStart(), CCruiser.getEnd(), cord)) {
//                    return false;
//                } else if (Hit(CDestroyer.getStart(), CDestroyer.getEnd(), cord)) {
//                    return false;
//                } else if (Hit(CSubmarine.getStart(), CSubmarine.getEnd(), cord)) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }


}