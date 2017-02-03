package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import spark.Request;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

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
    }

    //This function should return a new model
    static String newModel() {
        BattleshipModel test = new BattleshipModel();
        Gson gson = new Gson();
        String model = new String(gson.toJson(test));
        //System.out.println(model);
        return model;
    }

    //This function should accept an HTTP request and deseralize it into an actual Java object.
    private static BattleshipModel getModelFromReq(Request req){
        String data = req.body();
        Gson gson = new Gson();
        BattleshipModel ship = gson.fromJson(data, BattleshipModel.class);
        return ship;
    }

    //This controller should take a json object from the front end, and place the ship as requested, and then return the object.
    private static String placeShip(Request req) {
        BattleshipModel model = getModelFromReq(req); //is this correct?
        /*String id, orientation, row, col;
        id = req.params("id");  //name of what ship with which player in front of it
        orientation = req.params("orientation"); //horizontal/vertical
        row = req.params("row");    //row #
        col = req.params("col");    //col #


        Ship PAircraftCarrier = model.getPlayerAircraftCarrier();
        Ship PBattleship = model.getPlayerBattleship();
        Ship PCruiser = model.getPlayerCruiser();
        Ship PDestroyer = model.getPlayerDestroyer();
        Ship PSubmmarine = model.getPlayerSubmarine();

        Ship CAircraftCarrier = model.getComputerAircraftCarrier();
        Ship CBattleship = model.getComputerBattleship();
        Ship CCruiser = model.getComputerCruiser();
        Ship CDestroyer = model.getComputerDestroyer();
        Ship CSubmmarine = model.getComputerSubmarine();

        int rows = Integer.parseInt(row);
        int column = Integer.parseInt(col);

        Ship current = model.getShipByID(id);
        current.setStart(rows, column);

        int size = current.getLength();
        int shoot_x;
        int shoot_y;
        int stop = 0;
        Point cord;
        Point qwer;

        if(orientation.equals("horizontal") || rows < 11 || column - size < 1){
            current.setEnd(rows, column + size);

            if (id.toLowerCase().contains("aircraftcarrier")) {
                while (stop == 0) {
                    shoot_x = (int) (Math.random() * 6 + 1);
                    shoot_y = (int) (Math.random() * 10 + 5);
                    cord = new Point(shoot_x, shoot_y);
                    if(shoot_x > shoot_y){
                        qwer = new Point(shoot_x,shoot_y);
                    }

                    if(Hit(cord, CAircraftCarrier.getStart(), CAircraftCarrier.getEnd()) == 0){
                        stop = 1;
                    }
                }

            } else if (id.toLowerCase().contains("battleship")) {
                shoot_x = (int )(Math.random() * 7 + 1);
                shoot_y = (int )(Math.random() * 10 + 4);

            } else if (id.toLowerCase().contains("submarine") ) {
                shoot_x = (int )(Math.random() * 8 + 1);
                shoot_y = (int )(Math.random() * 10 + 3);

            } else if (id.toLowerCase().contains("cruiser")) {
                shoot_x = (int )(Math.random() * 8 + 1);
                shoot_y = (int )(Math.random() * 10 + 3);


            } else if (id.toLowerCase().contains("destroyer")) {
                shoot_x = (int )(Math.random() * 9 + 1);
                shoot_y = (int )(Math.random() * 10 + 2);



            }
            //place computer ship

        }else if(rows < 11 || column - size > 0){
            current.setEnd(rows,column - size);
            //place computer ship

        } else {
            current.setStart(-1,-1);
        }

        */

        Gson gson = new Gson();
        return gson.toJson(model);

    }

    //Similar to placeShip, but with firing.
    private static String fireAt(Request req) {
        BattleshipModel model = getModelFromReq(req);
        String X = req.params("row");
        String Y = req.params("col");

        int row = Integer.parseInt(X);
        int col = Integer.parseInt(Y);

        Point FireSpot = new Point(row,col);

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

        // Following branch tree checks if a point fired at has hit a ship and adds the point to the array of hits if so
        if(Hit(FireSpot, CAircraftCarrier.getStart(), CAircraftCarrier.getEnd())){
            model.addPointtoArray(FireSpot, model.getPlayerHits());
        }else if (Hit(FireSpot, CBattleship.getStart(), CBattleship.getEnd())){
            model.addPointtoArray(FireSpot, model.getPlayerHits());
        }else if (Hit(FireSpot, CCruiser.getStart(), CCruiser.getEnd())){
            model.addPointtoArray(FireSpot, model.getPlayerHits());
        }else if (Hit(FireSpot, CDestroyer.getStart(), CDestroyer.getEnd())){
            model.addPointtoArray(FireSpot, model.getPlayerHits());
        }else if (Hit(FireSpot, CSubmarine.getStart(), CSubmarine.getEnd())){
            model.addPointtoArray(FireSpot, model.getPlayerHits());
        }else{
            model.addPointtoArray(FireSpot, model.getPLayerMisses());
        }

        // EVAN IS HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        int shot = 0;

        while (shot == 0){
            int shoot_x = (int )(Math.random() * 10 + 1);
            int shoot_y = (int )(Math.random() * 10 + 1);
            Point FireSpotComputer = new Point(shoot_x,shoot_y);

            //if shoot_x or shoot_y == already fired there then get new number


            if(Hit(FireSpot, PAircraftCarrier.getStart(), PAircraftCarrier.getEnd())){
                //fire
            }else if (Hit(FireSpot, PBattleship.getStart(), PBattleship.getEnd())){
                //fire
            }else if (Hit(FireSpot, PCruiser.getStart(), PCruiser.getEnd())){
                //fire
            }else if (Hit(FireSpot, PDestroyer.getStart(), PDestroyer.getEnd())){
                //fire
            }else if (Hit(FireSpot, PSubmarine.getStart(), PSubmarine.getEnd())){
                //fire
            }else{
                //Miss
            }
        }

        Gson gson = new Gson();
        String jsonobject = gson.toJson(model);
        return jsonobject;
    }

    private static boolean Hit(Point shipStart, Point shipEnd, Point shotPoint){

        if(shipStart.getDown() == shipEnd.getDown()){     // if start and end on same y coordinate, ship is horizontal
            int y = shipStart.getDown();
            for (int x = shipStart.getAcross(); x < shipEnd.getAcross(); x++){  // loop from left to right of ship position

                if(x == shotPoint.getAcross() && y == shotPoint.getDown())
                    return true;   // if the coordinates of current point match shot, you hit!
            }
            return false; // check all points ship lies on, found no match to shot point
        }

        else if (shotPoint.getAcross() == shipStart.getAcross()) {      // if start and end on same x coordinate, ship is vertical
            int x = shipStart.getAcross();
            for (int y = shipEnd.getDown(); y < shipStart.getDown(); y++) {

                if (x == shotPoint.getAcross() && y == shotPoint.getDown())
                    return true;   // if the coordinates of current point match shot, you hit!
            }
            return false; // check all points ship lies on, found no match to shot point
        }

        return false; // points given are not horizontal or vertical and not valid, can't hit diagonally
    }

    private static int ComputerMatchShipPlacement(BattleshipModel model, Point A, Point B){
        Ship CAircraftCarrier = model.getComputerAircraftCarrier();
        Ship CBattleship = model.getComputerBattleship();
        Ship CCruiser = model.getComputerCruiser();
        Ship CDestroyer = model.getComputerDestroyer();
        Ship CSubmmarine = model.getComputerSubmarine();

        Point C;
        Point D;

        Ship boat;

        for(int i = 0;i>5;i++){
            if(i == 0){
                boat = model.getComputerAircraftCarrier();
            }else if(i == 1){
                boat = model.getComputerBattleship();
            }else if(i == 2){
                boat = model.getComputerCruiser();
            }else if(i == 3){
                boat = model.getComputerDestroyer();
            }else{
                boat = model.getComputerSubmarine();
            }

            for(int k = 0;k>5;k++) {
                int startA = boat.getStart().getAcross();
                int startB = boat.getStart().getDown();

                int checkA = A.getAcross();
                int checkB = A.getDown();

                if(startA == -1) {
                    i = 5;
                    break;
                }else if(startA == boat.getEnd().getAcross()){ //Horizontal
                    startB = startB - k;

                    for(int q = 0;q<5;q++){
                        if(A.getAcross() == B.getAcross()){
                            checkB = checkB - q;
                        }else{
                            checkA = checkA + q;
                        }

                        if(checkA == startA && checkB == startB){
                            return 1;
                        }
                    }
                }else{ //   Vertically
                    startA = startA + k;

                    for(int q = 0; q<5; q++){
                        if(A.getAcross() == B.getAcross()){
                            checkB = checkB - q;
                        }else{
                            checkA = checkA + q;
                        }

                        if(checkA == startA && checkB == startB){
                            return 1;
                        }
                    }
                }

            }
        }

        return 0;
    }

}