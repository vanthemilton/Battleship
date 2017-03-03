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
        //This will listen to GET requests to /model and return a clean new model
        get("/modelUpdated", (req, res) -> newModelUpdated());
        //This will listen to POST requests and expects to receive a game model, as well as location to fire to
        post("/fire/:row/:col/:num", (req, res) -> fireAt(req));
        //This will listen to POST requests and expects to receive a game model, as well as location to fire to
        post("/fire/:row/:col", (req, res) -> fireAtNew(req));
        //This will handle the scan feature
        post("/scan/:row/:col", (req, res) -> scan(req));

        //This will listen to POST requests and expects to receive a game model, as well as location to place the ship
        post("/placeShip/:id/:row/:col/:orientation/:num", (req, res) -> placeShip(req));
        //This will listen to POST requests and expects to receive a game model, as well as location to place the ship
        post("/placeShip/:id/:row/:col/:orientation", (req, res) -> placeShipUpdated(req));
    }

    //This function should return a new model
    static String newModel() {

        // make new model, make gson object, convert model to json using gson
        BattleshipModelNormal test = new BattleshipModelNormal();
        Gson gson = new Gson();
        String model = gson.toJson(test);
        return model;
    }



    //This function should return a new model
    static String newModelUpdated() {

        // make new model, make gson object, convert model to json using gson
        BattleshipModelUpdated test = new BattleshipModelUpdated();
        Gson gson = new Gson();
        String model = gson.toJson(test);
        //System.out.println(model);
        return model;
    }




    //This function should accept an HTTP request and deseralize it into an actual Java object.
    public static BattleshipModel getModelFromReq(Request req){

        String data = req.body();
        Gson gson = new Gson();
        BattleshipModelNormal game = gson.fromJson(data, BattleshipModelNormal.class);
        return game;
    }




    //This function should accept an HTTP request and deserialize it into an actual Java object.
    public static BattleshipModel getModelUpdatedFromReq(Request req){

        String data = req.body();
        Gson gson = new Gson();
        BattleshipModelUpdated game = gson.fromJson(data, BattleshipModelUpdated.class);
        return game;
    }






    //This controller should take a json object from the front end, and place the ship as requested, and then return the object.
    public static String placeShipUpdated(Request req) {

        BattleshipModelUpdated model = (BattleshipModelUpdated) getModelUpdatedFromReq(req);
        String id, orientation, row, col, num;
        id = req.params("id");  //name of what ship with which player in front of it
        orientation = req.params("orientation"); //horizontal/vertical
        row = req.params("row");    //row #
        col = req.params("col");    //col #

        List PlayerFireMiss = model.getPlayerMisses();
        List PlayerFireHit = model.getPlayerHits();


        if (!PlayerFireHit.isEmpty() || !PlayerFireMiss.isEmpty()) {//list isn't empty
            Gson gson = new Gson();
            return gson.toJson(model);
        }

        Ship PAircraftCarrier = model.getPlayerAircraftCarrier();
        Ship PBattleship = model.getPlayerBattleship();
        Ship PSubmarine = model.getPlayerSubmarine();
        Ship_Civilian PDinghy = model.getPlayerDinghy();
        Ship_Civilian PClipper = model.getPlayerClipper();

        Ship CAircraftCarrier = model.getComputerAircraftCarrier();
        Ship_Stealth CBattleship = model.getComputerBattleship();
        Ship_Stealth CSubmarine = model.getComputerSubmarine();
        Ship_Civilian CDinghy = model.getComputerDinghy();
        Ship_Civilian CClipper = model.getComputerClipper();


        int rows = Integer.parseInt(row);
        int column = Integer.parseInt(col);

        System.out.println(id);

        model.getShipByID(id).setEnd(0, 0);
        model.getShipByID(id).setStart(0, 0);

        int size = model.getShipByID(id).getLength();
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
                } else if (Hit(PSubmarine.getStart(), PSubmarine.getEnd(), cord)) {
                    stop = 1;
                } else if (Hit(PDinghy.getStart(), PDinghy.getEnd(), cord)) {
                    stop = 1;
                } else if (Hit(PClipper.getStart(), PClipper.getEnd(), cord)) {
                    stop = 1;
                }
            }

            if (stop == 0) {
                model.getShipByID(id).setStart(rows, column);
                model.getShipByID(id).setEnd(rows + size - 1, column);
                System.out.println("Should be placed\n");
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
                } else if (Hit(PSubmarine.getStart(), PSubmarine.getEnd(), cord)) {
                    stop = 1;
                } else if (Hit(CDinghy.getStart(), CDinghy.getEnd(), cord)) {
                    stop = 1;
                } else if (Hit(CClipper.getStart(), CClipper.getEnd(), cord)) {
                    stop = 1;
                }
            }

            if (stop == 0) {
                model.getShipByID(id).setStart(rows, column);
                model.getShipByID(id).setEnd(rows, column + size - 1);
            }
        }

        //If the player sets down a ship the computer sets down the same ship

        Point computerStart;
        Point computerEnd;
        int computer_x = 0;
        int computer_y = 0;
        int horizontal = 0;
        int computer_length_increase = 0;
        int computerLength = model.getShipByID(id).getLength() - 1;

        if (id.toLowerCase().contains("aircraftcarrier")) {
            CAircraftCarrier.setStart(0, 0);
            CAircraftCarrier.setEnd(0, 0);
            CAircraftCarrier.setHealth(5);

        } else if (id.toLowerCase().contains("battleship")) {
            CBattleship.setStart(0, 0);
            CBattleship.setEnd(0, 0);
            CBattleship.setHealth(4);

        } else if (id.toLowerCase().contains("submarine")) {
            CSubmarine.setStart(0, 0);
            CSubmarine.setEnd(0, 0);
            CSubmarine.setHealth(3);

        } else if (id.toLowerCase().contains("clipper")) {
            CClipper.setStart(0, 0);
            CClipper.setEnd(0, 0);
            CClipper.setHealth(1);

        } else if (id.toLowerCase().contains("dinghy")) {
            CDinghy.setStart(0, 0);
            CDinghy.setEnd(0, 0);
            CDinghy.setHealth(1);

        }

        int movingPoint = 0, stoppedPoint = 0;

        while (stop == 0) {
            computer_x = (int) (Math.random() * 10 + 1);
            computer_y = (int) (Math.random() * 10 + 1);
            horizontal = (int) (Math.random() * 2 + 1);

            computerStart = new Point(computer_x, computer_y);
            computerEnd = new Point(0, 0);
            stop = 1;

            if (horizontal == 1 && computer_x + computerLength < 11) { //horizontal
                computerEnd = new Point(computer_x + computerLength, computer_y);

                computer_length_increase = computerEnd.getAcross();
                movingPoint = computer_x;
                stoppedPoint = computer_y;

            } else if (horizontal == 2 && computer_y + computerLength < 11) { //vertical
                computerEnd = new Point(computer_x, computer_y + computerLength);
                computer_length_increase = computerEnd.getDown();

                movingPoint = computer_y;
                stoppedPoint = computer_x;
            } else {
                stop = 0;
            }


            if (stop == 1) {
                for (movingPoint = movingPoint - 1; movingPoint < (computer_length_increase + 2); movingPoint++) {
                    if (horizontal == 1) {
                        cord.setAcross(movingPoint);
                        cord.setDown(stoppedPoint);

                    } else {
                        cord.setAcross(stoppedPoint);
                        cord.setDown(movingPoint);
                    }


                    //if ship lands on another ship then
                    if (Hit(CAircraftCarrier.getStart(), CAircraftCarrier.getEnd(), cord)) {
                        stop = 0;
                    } else if (Hit(CBattleship.getStart(), CBattleship.getEnd(), cord)) {
                        stop = 0;
                    } else if (Hit(CSubmarine.getStart(), CSubmarine.getEnd(), cord)) {
                        stop = 0;
                    } else if (Hit(CClipper.getStart(), CClipper.getEnd(), cord)) {
                        stop = 0;
                    } else if (Hit(CDinghy.getStart(), CDinghy.getEnd(), cord)) {
                        stop = 0;
                    }
                }

                if (stop == 1) {
                    if (id.toLowerCase().contains("aircraftcarrier")) {
                        CAircraftCarrier.setStart(computerStart.getAcross(), computerStart.getDown());
                        CAircraftCarrier.setEnd(computerEnd.getAcross(), computerEnd.getDown());

                    } else if (id.toLowerCase().contains("battleship")) {
                        CBattleship.setStart(computerStart.getAcross(), computerStart.getDown());
                        CBattleship.setEnd(computerEnd.getAcross(), computerEnd.getDown());

                    } else if (id.toLowerCase().contains("submarine")) {
                        CSubmarine.setStart(computerStart.getAcross(), computerStart.getDown());
                        CSubmarine.setEnd(computerEnd.getAcross(), computerEnd.getDown());

                    } else if (id.toLowerCase().contains("dinghy")) {
                        CDinghy.setStart(computerStart.getAcross(), computerStart.getDown());
                        CDinghy.setEnd(computerEnd.getAcross(), computerEnd.getDown());

                    } else if (id.toLowerCase().contains("clipper")) {
                        CClipper.setStart(computerStart.getAcross(), computerStart.getDown());
                        CClipper.setEnd(computerEnd.getAcross(), computerEnd.getDown());
                    }
                }
            }
        }

        Gson gson = new Gson();
        return gson.toJson(model);

    }






    //Similar to placeShip, but with firing.
    private static String fireAtNew(Request req) {

        // Generate model from json, get coordinates from fire request
        BattleshipModelUpdated model = (BattleshipModelUpdated) getModelUpdatedFromReq(req);

        String X = req.params("row");
        String Y = req.params("col");

        int row = Integer.parseInt(X);
        int col = Integer.parseInt(Y);
        int i = 0;

        // Make point object from coordinates
        Point FireSpot = new Point(row,col);

        // Grab player and computer ships from current model
        Ship PAircraftCarrier = model.getPlayerAircraftCarrier();
        Ship_Stealth PBattleship = model.getPlayerBattleship();
        Ship_Stealth PSubmarine = model.getPlayerSubmarine();
        Ship_Civilian PClipper = model.getPlayerClipper();
        Ship_Civilian PDinghy = model.getPlayerDinghy();


        Ship CAircraftCarrier = model.getComputerAircraftCarrier();
        Ship_Stealth CBattleship = model.getComputerBattleship();
        Ship_Stealth CSubmarine = model.getComputerSubmarine();
        Ship_Civilian CClipper = model.getComputerClipper();
        Ship_Civilian CDinghy = model.getComputerDinghy();


        if(PAircraftCarrier.getHealth() == 0){
            PAircraftCarrier.setHealth(-1);

        }else if(PBattleship.getHealth() == 0){
            PBattleship.setHealth(-1);

        }else if(PClipper.getHealth() == 0){
            PClipper.setHealth(-1);

        }else if(PDinghy.getHealth() == 0){
            PDinghy.setHealth(-1);

        }else if(PSubmarine.getHealth() == 0){
            PSubmarine.setHealth(-1);
        }

        if(CAircraftCarrier.getHealth() == 0){
            CAircraftCarrier.setHealth(-1);

        }else if(CBattleship.getHealth() == 0){
            CBattleship.setHealth(-1);

        }else if(CClipper.getHealth() == 0){
            CClipper.setHealth(-1);

        }else if(CDinghy.getHealth() == 0){
            CDinghy.setHealth(-1);

        }else if(CSubmarine.getHealth() == 0){
            CSubmarine.setHealth(-1);
        }

        //Player has shot at this place already
        if( alreadyShot( FireSpot, model, true) ){
            Gson gson = new Gson();
            String jsonobject = gson.toJson(model);
            return jsonobject;
        }

        //Won't fire unless all ships are placed down
        if(PAircraftCarrier.getStart().getAcross() < 1){
            Gson gson = new Gson();
            String jsonobject = gson.toJson(model);
            return jsonobject;
        }else if(PBattleship.getStart().getAcross() < 1){
            Gson gson = new Gson();
            String jsonobject = gson.toJson(model);
            return jsonobject;
        }else if(PClipper.getStart().getAcross() < 1){
            Gson gson = new Gson();
            String jsonobject = gson.toJson(model);
            return jsonobject;
        }else if(PDinghy.getStart().getAcross() < 1){
            Gson gson = new Gson();
            String jsonobject = gson.toJson(model);
            return jsonobject;
        }else if(PSubmarine.getStart().getAcross() < 1){
            Gson gson = new Gson();
            String jsonobject = gson.toJson(model);
            return jsonobject;
        }


        // The following branch tree checks if a point fired at
        // BY A PLAYER has hit a COMPUTER ship and adds the point to the array of hits if so
        if( Hit( CAircraftCarrier.getStart(), CAircraftCarrier.getEnd(), FireSpot ) ){
            model.addPointtoArray(FireSpot, model.getComputerHits());
            CAircraftCarrier.setHealth(CAircraftCarrier.getHealth()-1);

        } else if ( Hit( CBattleship.getStart(), CBattleship.getEnd(), FireSpot ) ){
            model.addPointtoArray(FireSpot, model.getComputerHits());
            CBattleship.setHealth(CBattleship.getHealth()-1);

        } else if ( Hit( CClipper.getStart(), CClipper.getEnd(), FireSpot  ) ) {
            Point midPoint = new Point((CClipper.getStart().getAcross() + CClipper.getEnd().getAcross()) / 2,(CClipper.getStart().getDown() + CClipper.getEnd().getDown()) / 2);
            model.addPointtoArray(midPoint, model.getComputerHits());
            model.addPointtoArray(CClipper.getStart(), model.getComputerHits());
            model.addPointtoArray(CClipper.getEnd(), model.getComputerHits());
            CClipper.setHealth(CClipper.getHealth()-1);


        } else if ( Hit( CDinghy.getStart(), CDinghy.getEnd(), FireSpot  ) ){
            model.addPointtoArray(FireSpot, model.getComputerHits());
            CDinghy.setHealth(CDinghy.getHealth()-1);

        } else if ( Hit( CSubmarine.getStart(), CSubmarine.getEnd(), FireSpot  ) ){
            model.addPointtoArray(FireSpot, model.getComputerHits());
            CSubmarine.setHealth(CSubmarine.getHealth()-1);

        } else{   // No hits on any ships, adds point to array of misses instead
            model.addPointtoArray(FireSpot, model.getComputerMisses());
        }

        // Create two random coordinates for computer to shoot at and make a point object of them
        int shootX = (int )(Math.random() * 10 + 1);
        int shootY = (int )(Math.random() * 10 + 1);
        Point FireSpotComputer = new Point(shootX, shootY);

        while( alreadyShot( FireSpotComputer, model,false) ){
            shootX = (int )(Math.random() * 10 + 1);
            shootY = (int )(Math.random() * 10 + 1);
            FireSpotComputer = new Point(shootX, shootY);
        }

        // Following branch tree checks if a point fired at BY THE COMPUTER has hit a PLAYER ship
        // And adds the point to the array of hits if so
        if( Hit( PAircraftCarrier.getStart(), PAircraftCarrier.getEnd(), FireSpotComputer ) ){
            model.addPointtoArray(FireSpotComputer, model.getPlayerHits());
            PAircraftCarrier.setHealth(PAircraftCarrier.getHealth()-1);

        } else if ( Hit( PBattleship.getStart(), PBattleship.getEnd(), FireSpotComputer  ) ){
            model.addPointtoArray(FireSpotComputer, model.getPlayerHits());
            PBattleship.setHealth(PBattleship.getHealth()-1);

        } else if ( Hit( PClipper.getStart(), PClipper.getEnd(), FireSpotComputer  ) ){
            Point midPoint2 = new Point((PClipper.getStart().getAcross() + PClipper.getEnd().getAcross()) / 2,(PClipper.getStart().getDown() + PClipper.getEnd().getDown()) / 2);
            model.addPointtoArray(midPoint2, model.getPlayerHits());
            model.addPointtoArray(PClipper.getStart(), model.getPlayerHits());
            model.addPointtoArray(PClipper.getEnd(), model.getPlayerHits());
            PClipper.setHealth(PClipper.getHealth()-1);

        } else if ( Hit( PDinghy.getStart(), PDinghy.getEnd(), FireSpotComputer  ) ){
            model.addPointtoArray(FireSpotComputer, model.getPlayerHits());
            PDinghy.setHealth(PDinghy.getHealth()-1);

        } else if ( Hit( PSubmarine.getStart(), PSubmarine.getEnd(), FireSpotComputer  ) ){
            model.addPointtoArray(FireSpotComputer, model.getPlayerHits());
            PSubmarine.setHealth(PSubmarine.getHealth()-1);

        } else{   // No hits on any ships, adds point to array of misses instead
            model.addPointtoArray(FireSpotComputer, model.getPlayerMisses());
        }

        Gson gson = new Gson();
        String jsonobject = gson.toJson(model);
        return jsonobject;
    }







    private static String scan(Request req) {

        // Generate model from json, get coordinates from fire request
        BattleshipModelUpdated model = (BattleshipModelUpdated) getModelUpdatedFromReq(req);

        String X = req.params("row");
        String Y = req.params("col");

        int row = Integer.parseInt(X);
        int col = Integer.parseInt(Y);
        int i = 0;

        // Make point object from coordinates
        Point[] FireSpot = new Point[] {new Point(row, col), new Point((row-1), col), new Point((row+1), col), new Point(row,(col-1)), new Point(row, (col+1))};

        // Grab player and computer ships from current model
        Ship PAircraftCarrier = model.getPlayerAircraftCarrier();
        Ship_Stealth PBattleship = model.getPlayerBattleship();
        Ship_Stealth PSubmarine = model.getPlayerSubmarine();
        Ship_Civilian PClipper = model.getPlayerClipper();
        Ship_Civilian PDinghy = model.getPlayerDinghy();


        Ship CAircraftCarrier = model.getComputerAircraftCarrier();
        Ship_Stealth CBattleship = model.getComputerBattleship();
        Ship_Stealth CSubmarine = model.getComputerSubmarine();
        Ship_Civilian CClipper = model.getComputerClipper();
        Ship_Civilian CDinghy = model.getComputerDinghy();



        //Won't fire unless all ships are placed down
        if(PAircraftCarrier.getStart().getAcross() < 1){
            Gson gson = new Gson();
            String jsonobject = gson.toJson(model);
            return jsonobject;
        }else if(PBattleship.getStart().getAcross() < 1){
            Gson gson = new Gson();
            String jsonobject = gson.toJson(model);
            return jsonobject;
        }else if(PClipper.getStart().getAcross() < 1){
            Gson gson = new Gson();
            String jsonobject = gson.toJson(model);
            return jsonobject;
        }else if(PDinghy.getStart().getAcross() < 1){
            Gson gson = new Gson();
            String jsonobject = gson.toJson(model);
            return jsonobject;
        }else if(PSubmarine.getStart().getAcross() < 1){
            Gson gson = new Gson();
            String jsonobject = gson.toJson(model);
            return jsonobject;
        }


        if(PAircraftCarrier.getHealth() == 0){
            PAircraftCarrier.setHealth(-1);

        }else if(PBattleship.getHealth() == 0){
            PBattleship.setHealth(-1);

        }else if(PClipper.getHealth() == 0){
            PClipper.setHealth(-1);

        }else if(PDinghy.getHealth() == 0){
            PDinghy.setHealth(-1);

        }else if(PSubmarine.getHealth() == 0){
            PSubmarine.setHealth(-1);
        }

        if(CAircraftCarrier.getHealth() == 0){
            CAircraftCarrier.setHealth(-1);

        }else if(CBattleship.getHealth() == 0){
            CBattleship.setHealth(-1);

        }else if(CClipper.getHealth() == 0){
            CClipper.setHealth(-1);

        }else if(CDinghy.getHealth() == 0){
            CDinghy.setHealth(-1);

        }else if(CSubmarine.getHealth() == 0){
            CSubmarine.setHealth(-1);
        }

        // The following branch tree checks if a point fired at
        // BY A PLAYER has hit a COMPUTER ship and adds the point to the array of hits if so
        for(int j=0; j < 5; j++) {
            if (Hit(CAircraftCarrier.getStart(), CAircraftCarrier.getEnd(), FireSpot[j])) {
                model.setScanned(true);

            } else if (Hit(CClipper.getStart(), CClipper.getEnd(), FireSpot[j])) {
                model.setScanned(true);

            } else if (Hit(CDinghy.getStart(), CDinghy.getEnd(), FireSpot[j])) {
                model.setScanned(true);

            }
        }

        // Create two random coordinates for computer to shoot at and make a point object of them
        int shootX = (int )(Math.random() * 10 + 1);
        int shootY = (int )(Math.random() * 10 + 1);
        Point FireSpotComputer = new Point(shootX, shootY);

        while( alreadyShot( FireSpotComputer, model,false) ){
            shootX = (int )(Math.random() * 10 + 1);
            shootY = (int )(Math.random() * 10 + 1);
            FireSpotComputer = new Point(shootX, shootY);
        }

        // Following branch tree checks if a point fired at BY THE COMPUTER has hit a PLAYER ship
        // And adds the point to the array of hits if so
        if( Hit( PAircraftCarrier.getStart(), PAircraftCarrier.getEnd(), FireSpotComputer ) ){
            model.addPointtoArray(FireSpotComputer, model.getPlayerHits());
            PAircraftCarrier.setHealth(PAircraftCarrier.getHealth()-1);

        } else if ( Hit( PBattleship.getStart(), PBattleship.getEnd(), FireSpotComputer  ) ){
            model.addPointtoArray(FireSpotComputer, model.getPlayerHits());
            PBattleship.setHealth(PBattleship.getHealth()-1);

        } else if ( Hit( PClipper.getStart(), PClipper.getEnd(), FireSpotComputer  ) ){
            Point midPoint2 = new Point((PClipper.getStart().getAcross() + PClipper.getEnd().getAcross()) / 2,(PClipper.getStart().getDown() + PClipper.getEnd().getDown()) / 2);
            model.addPointtoArray(midPoint2, model.getPlayerHits());
            model.addPointtoArray(PClipper.getStart(), model.getPlayerHits());
            model.addPointtoArray(PClipper.getEnd(), model.getPlayerHits());
            PClipper.setHealth(PClipper.getHealth()-1);

        } else if ( Hit( PDinghy.getStart(), PDinghy.getEnd(), FireSpotComputer  ) ){
            model.addPointtoArray(FireSpotComputer, model.getPlayerHits());
            PDinghy.setHealth(PDinghy.getHealth()-1);

        } else if ( Hit( PSubmarine.getStart(), PSubmarine.getEnd(), FireSpotComputer  ) ){
            model.addPointtoArray(FireSpotComputer, model.getPlayerHits());
            PSubmarine.setHealth(PSubmarine.getHealth()-1);

        } else{   // No hits on any ships, adds point to array of misses instead
            model.addPointtoArray(FireSpotComputer, model.getPlayerMisses());
        }

        Gson gson = new Gson();
        String jsonobject = gson.toJson(model);
        return jsonobject;
    }

    public static String placeShip(Request req) {

        BattleshipModelNormal model = (BattleshipModelNormal) getModelFromReq(req);
        String id, orientation, row, col, num;
        orientation = req.params("orientation"); //horizontal/vertical
        id = req.params("id");  //name of what ship with which player in front of it
        row = req.params("row");    //row #
        col = req.params("col");    //col #
        num = req.params("num");    //num #

        List PlayerFireMiss = model.getPlayerMisses();
        List PlayerFireHit = model.getPlayerHits();

        int number = Integer.parseInt(num);


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

        int rows = Integer.parseInt(row);
        int column = Integer.parseInt(col);


        Ship current = model.getShipByID(id);

        current.setEnd(0,0);
        current.setStart(0,0);
        current.setHealth(current.getLength());

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

        //If the player sets down a ship the computer sets down the same ship

        Point computerStart;
        Point computerEnd;
        int computer_x = 0;
        int computer_y = 0;
        int horizontal = 0;
        int computer_length_increase = 0;
        int computerLength = current.getLength() - 1;

        if (id.toLowerCase().contains("aircraftcarrier")) {
            CAircraftCarrier.setStart(0, 0);
            CAircraftCarrier.setEnd(0, 0);
            CAircraftCarrier.setHealth(5);

        } else if (id.toLowerCase().contains("battleship")) {
            CBattleship.setStart(0, 0);
            CBattleship.setEnd(0, 0);
            CBattleship.setHealth(4);

        } else if (id.toLowerCase().contains("cruiser")) {
            CCruiser.setStart(0, 0);
            CCruiser.setEnd(0, 0);
            CCruiser.setHealth(3);

        } else if (id.toLowerCase().contains("submarine")) {
            CSubmarine.setStart(0, 0);
            CSubmarine.setEnd(0, 0);
            CSubmarine.setHealth(3);

        } else if (id.toLowerCase().contains("destroyer")) {
            CDestroyer.setStart(0, 0);
            CDestroyer.setEnd(0, 0);
            CDestroyer.setHealth(2);

        }

        int movingPoint = 0, stoppedPoint = 0;

        while(stop == 0) {
            computer_x = (int) (Math.random() * 10 + 1);
            computer_y = (int) (Math.random() * 10 + 1);
            horizontal = (int) (Math.random() * 2 + 1);

            computerStart = new Point(computer_x, computer_y);
            computerEnd = new Point(0, 0);
            stop = 1;

            if (horizontal == 1 && computer_x + computerLength < 11) { //horizontal
                computerEnd = new Point(computer_x + computerLength, computer_y);

                computer_length_increase = computerEnd.getAcross();
                movingPoint = computer_x;
                stoppedPoint = computer_y;

            } else if (horizontal == 2 && computer_y + computerLength < 11) { //vertical
                computerEnd = new Point(computer_x, computer_y + computerLength);

                computer_length_increase = computerEnd.getDown();
                movingPoint = computer_y;
                stoppedPoint = computer_x;
            } else {
                stop = 0;
            }


            if (stop == 1) {
                for (movingPoint = movingPoint - 1; movingPoint < (computer_length_increase + 2); movingPoint++) {
                    if (horizontal == 1) {
                        cord.setAcross(movingPoint);
                        cord.setDown(stoppedPoint);

                    } else {
                        cord.setAcross(stoppedPoint);
                        cord.setDown(movingPoint);
                    }


                    //if ship lands on another ship then
                    if (Hit(CAircraftCarrier.getStart(), CAircraftCarrier.getEnd(), cord)) {
                        stop = 0;
                    } else if (Hit(CBattleship.getStart(), CBattleship.getEnd(), cord)) {
                        stop = 0;
                    } else if (Hit(CCruiser.getStart(), CCruiser.getEnd(), cord)) {
                        stop = 0;
                    } else if (Hit(CDestroyer.getStart(), CDestroyer.getEnd(), cord)) {
                        stop = 0;
                    } else if (Hit(CSubmarine.getStart(), CSubmarine.getEnd(), cord)) {
                        stop = 0;
                    }
                }

                if(stop == 1) {
                    if (id.toLowerCase().contains("aircraftcarrier")) {
                        CAircraftCarrier.setStart(computerStart.getAcross(), computerStart.getDown());
                        CAircraftCarrier.setEnd(computerEnd.getAcross(), computerEnd.getDown());

                    } else if (id.toLowerCase().contains("battleship")) {
                        CBattleship.setStart(computerStart.getAcross(), computerStart.getDown());
                        CBattleship.setEnd(computerEnd.getAcross(), computerEnd.getDown());

                    } else if (id.toLowerCase().contains("cruiser")) {
                        CCruiser.setStart(computerStart.getAcross(), computerStart.getDown());
                        CCruiser.setEnd(computerEnd.getAcross(), computerEnd.getDown());

                    } else if (id.toLowerCase().contains("submarine")) {
                        CSubmarine.setStart(computerStart.getAcross(), computerStart.getDown());
                        CSubmarine.setEnd(computerEnd.getAcross(), computerEnd.getDown());

                    } else if (id.toLowerCase().contains("destroyer")) {
                        CDestroyer.setStart(computerStart.getAcross(), computerStart.getDown());
                        CDestroyer.setEnd(computerEnd.getAcross(), computerEnd.getDown());

                    }
                }
            }
        }

        Gson gson = new Gson();
        return gson.toJson(model);

    }





    //Similar to placeShip, but with firing.
    private static String fireAt(Request req) {

        // Generate model from json, get coordinates from fire request
        BattleshipModelNormal model = (BattleshipModelNormal) getModelFromReq(req);

        String X = req.params("row");
        String Y = req.params("col");
        String Classic = req.params("num");

        int row = Integer.parseInt(X);
        int col = Integer.parseInt(Y);

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


        if(PAircraftCarrier.getHealth() == 0){
            PAircraftCarrier.setHealth(-1);

        }else if(PBattleship.getHealth() == 0){
            PBattleship.setHealth(-1);

        }else if(PCruiser.getHealth() == 0){
            PCruiser.setHealth(-1);

        }else if(PDestroyer.getHealth() == 0){
            PDestroyer.setHealth(-1);

        }else if(PSubmarine.getHealth() == 0){
            PSubmarine.setHealth(-1);
        }

        if(CAircraftCarrier.getHealth() == 0){
            CAircraftCarrier.setHealth(-1);

        }else if(CBattleship.getHealth() == 0){
            CBattleship.setHealth(-1);

        }else if(CCruiser.getHealth() == 0){
            CCruiser.setHealth(-1);

        }else if(CDestroyer.getHealth() == 0){
            CDestroyer.setHealth(-1);

        }else if(CSubmarine.getHealth() == 0){
            CSubmarine.setHealth(-1);
        }

        //Player has shot at this place already
        if( alreadyShot( FireSpot, model, true) ){
            Gson gson = new Gson();
            String jsonobject = gson.toJson(model);
            return jsonobject;
        }

        //Won't fire unless all ships are placed down
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


        // The following branch tree checks if a point fired at
        // BY A PLAYER has hit a COMPUTER ship and adds the point to the array of hits if so
        if( Hit( CAircraftCarrier.getStart(), CAircraftCarrier.getEnd(), FireSpot ) ){
            model.addPointtoArray(FireSpot, model.getComputerHits());
            CAircraftCarrier.setHealth(CAircraftCarrier.getHealth()-1);

        } else if ( Hit( CBattleship.getStart(), CBattleship.getEnd(), FireSpot ) ){
            model.addPointtoArray(FireSpot, model.getComputerHits());
            CBattleship.setHealth(CBattleship.getHealth()-1);

        } else if ( Hit( CCruiser.getStart(), CCruiser.getEnd(), FireSpot  ) ){
            model.addPointtoArray(FireSpot, model.getComputerHits());
            CCruiser.setHealth(CCruiser.getHealth()-1);

        } else if ( Hit( CDestroyer.getStart(), CDestroyer.getEnd(), FireSpot  ) ){
            model.addPointtoArray(FireSpot, model.getComputerHits());
            CDestroyer.setHealth(CDestroyer.getHealth()-1);

        } else if ( Hit( CSubmarine.getStart(), CSubmarine.getEnd(), FireSpot  ) ){
            model.addPointtoArray(FireSpot, model.getComputerHits());
            CSubmarine.setHealth(CSubmarine.getHealth()-1);

        } else{   // No hits on any ships, adds point to array of misses instead
            model.addPointtoArray(FireSpot, model.getComputerMisses());
        }

        // Create two random coordinates for computer to shoot at and make a point object of them
        int shootX = (int )(Math.random() * 10 + 1);
        int shootY = (int )(Math.random() * 10 + 1);
        Point FireSpotComputer = new Point(shootX, shootY);

        while( alreadyShot( FireSpotComputer, model,false) ){
            shootX = (int )(Math.random() * 10 + 1);
            shootY = (int )(Math.random() * 10 + 1);
            FireSpotComputer = new Point(shootX, shootY);
        }

        // Following branch tree checks if a point fired at BY THE COMPUTER has hit a PLAYER ship
        // And adds the point to the array of hits if so
        if( Hit( PAircraftCarrier.getStart(), PAircraftCarrier.getEnd(), FireSpotComputer ) ){
            model.addPointtoArray(FireSpotComputer, model.getPlayerHits());
            PAircraftCarrier.setHealth(PAircraftCarrier.getHealth()-1);

        } else if ( Hit( PBattleship.getStart(), PBattleship.getEnd(), FireSpotComputer  ) ){
            model.addPointtoArray(FireSpotComputer, model.getPlayerHits());
            PBattleship.setHealth(PBattleship.getHealth()-1);

        } else if ( Hit( PCruiser.getStart(), PCruiser.getEnd(), FireSpotComputer  ) ){
            model.addPointtoArray(FireSpotComputer, model.getPlayerHits());
            PCruiser.setHealth(PCruiser.getHealth()-1);

        } else if ( Hit( PDestroyer.getStart(), PDestroyer.getEnd(), FireSpotComputer  ) ){
            model.addPointtoArray(FireSpotComputer, model.getPlayerHits());
            PDestroyer.setHealth(PDestroyer.getHealth()-1);

        } else if ( Hit( PSubmarine.getStart(), PSubmarine.getEnd(), FireSpotComputer  ) ){
            model.addPointtoArray(FireSpotComputer, model.getPlayerHits());
            PSubmarine.setHealth(PSubmarine.getHealth()-1);

        } else{   // No hits on any ships, adds point to array of misses instead
            model.addPointtoArray(FireSpotComputer, model.getPlayerMisses());
        }

        Gson gson = new Gson();
        String jsonobject = gson.toJson(model);
        return jsonobject;
    }






    public static boolean alreadyShot(Point shotPoint, BattleshipModel model, boolean player){
        List<Point> checkHits;
        List<Point> checkMisses;

        int sizeHits;
        int sizeMisses;

        //if player
        if(player) {
            checkHits = model.getComputerHits();
            checkMisses = model.getComputerMisses();

            sizeHits = model.getComputerHits().size();
            sizeMisses = model.getComputerMisses().size();

        }else{
            checkHits = model.getPlayerHits();
            checkMisses = model.getPlayerMisses();

            sizeHits = model.getPlayerHits().size();
            sizeMisses = model.getPlayerMisses().size();

        }

        for(int i = 0; i < sizeHits; i++){
            if(shotPoint.getAcross() == checkHits.get(i).getAcross() && shotPoint.getDown() == checkHits.get(i).getDown()){
                return true;
            }
        }

        for(int i = 0; i < sizeMisses; i++){
            if(shotPoint.getAcross() == checkMisses.get(i).getAcross() && shotPoint.getDown() == checkMisses.get(i).getDown() ){
                return true;
            }
        }

        return false;
    }





    public static boolean Hit(Point shipStart, Point shipEnd, Point shotPoint){

        if(shipStart.getDown() == shipEnd.getDown()){     // if start and end on same y coordinate, ship is horizontal
            int y = shipStart.getDown();
            for (int x = shipStart.getAcross(); x <= shipEnd.getAcross(); x++){  // loop from left to right of ship position

                if(x == shotPoint.getAcross() && y == shotPoint.getDown())
                    return true;   // if the coordinates of current point match shot, you hit!
            }
            return false; // check all points ship lies on, found no match to shot point


        } else if (shotPoint.getAcross() == shipStart.getAcross()) { // if start and end on same x coordinate, ship is vertical
            int x = shipStart.getAcross();
            for (int y = shipStart.getDown(); y <= shipEnd.getDown(); y++) {

                if (x == shotPoint.getAcross() && y == shotPoint.getDown())
                    return true;   // if the coordinates of current point match shot, you hit!
            }
            return false; // check all points ship lies on, found no match to shot point
        }

        return false; // points given are not horizontal or vertical and not valid, can't hit diagonally
    }
}