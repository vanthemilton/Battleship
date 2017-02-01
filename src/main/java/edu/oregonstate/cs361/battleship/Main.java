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

    public Main(){
        setPlayerHits(0);
        setPlayerMisses(0);

        setComputerHits(0);
        setComputerMisses(0);
    }

    //Member variables
    private int playerMisses;
    private int playerHits;

    private int computerMisses;
    private int computerHits;

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
        String id, orientation, row, col;
        id = req.params("id");  //name of what ship with which player in front of it
        orientation = req.params("orientation"); //horizontal/vertical
        row = req.params("row");    //row #
        col = req.params("col");    //col #

        int rows = Integer.parseInt(row);
        int column = Integer.parseInt(col);

        Ship current = model.getShipByID(id);
        current.setStart(rows,column);

        int size = current.getLength();

        if(orientation.equals("horizontal")){
            current.setEnd(rows + size, column);
        }else{
            current.setEnd(rows,column - size);
        }

        Gson gson = new Gson();
        return gson.toJson(model);
    }

    //Similar to placeShip, but with firing.
    private static String fireAt(Request req) {
        BattleshipModel ship = getModelFromReq(req);
        String X = req.params("row");
        String Y = req.params("col");

        int row = Integer.parseInt(X);
        int col = Integer.parseInt(Y);

        Point FireSpot = new Point(row,col);

        //See if the computer or the player is firing
        //Go into ship and ...

        //See if hit
            //add a Point to the Hit Point array file
        //else Miss
            //adds a Point to the Miss Point array file

        Gson gson = new Gson();
        String jsonobject = gson.toJson(ship);
        return jsonobject;
    }

    //////////////////////////////////////////////////////////////////
    //Player gets and sets for hitting and missing.
    public int getPlayerMisses(){
        return playerMisses;
    }

    public int getPlayerHits(){
        return playerHits;
    }

    public void setPlayerMisses(int a){
        this.playerMisses = a;
    }

    public void setPlayerHits(int a){
        this.playerHits = a;
    }
    //////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////
    //Computer gets and sets for hitting and missing.
    public int getComputerMisses(){
        return computerMisses;
    }

    public int getComputerHits(){
        return computerHits;
    }

    public void setComputerMisses(int a){
        this.computerMisses = a;
    }

    public void setComputerHits(int a){
        this.computerHits = a;
    }
    //////////////////////////////////////////////////////////////////

}