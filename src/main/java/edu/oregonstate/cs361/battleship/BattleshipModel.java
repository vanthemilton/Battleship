package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;
import java.util.List;


public class BattleshipModel {


    //                  Member variables
    protected Ship playerAircraftCarrier;

    protected Ship computerAircraftCarrier;

    // all ships for player or computer are collected in these lists
    protected List<Ship> playerShips;
    protected List<Ship> computerShips;

    protected List<Point> playerHits;
    protected List<Point> playerMisses;
    protected List<Point> computerHits;
    protected List<Point> computerMisses;




    //                  Constructor
    public BattleshipModel () {

        playerAircraftCarrier = new Ship("playerAircraftCarrier");

        computerAircraftCarrier = new Ship("computerAircraftCarrier");

        playerHits = new ArrayList<Point>();
        playerMisses = new ArrayList<Point>();
        computerHits = new ArrayList<Point>();
        computerMisses = new ArrayList<Point>();

        playerShips = new ArrayList<Ship>();
        playerShips.add(playerAircraftCarrier);

        computerShips = new ArrayList<Ship>();
        computerShips.add(computerAircraftCarrier);

    }


    //          Add Point object to an array function
    protected boolean addPointtoArray(Point somePoint, List someArray) {

        if (somePoint.getAcross() > 10 || somePoint.getAcross() < 1 || somePoint.getDown() > 10 || somePoint.getDown() < 1)

            return false;

        else {

            someArray.add(somePoint);
            return true;

        }
    }

    //          Get ship by ID function, assumes user only selects from drop down menu on front end
    /*public Ship getShipByID(String id) {

        if(id.toLowerCase().contains("computer")) {

            if (id.toLowerCase().contains("aircraftcarrier")) {
                return getComputerAircraftCarrier();
            }

        }

        else {

            if (id.toLowerCase().contains("aircraftcarrier")) {
                return getPlayerAircraftCarrier();
            }

        }

        // DEFAULTS TO THIS IF ID FORMAT IS INCORRECT:
        Ship PizzaShip = new Ship("PizzaShip");
        PizzaShip.setName("PizzaShip");
        return PizzaShip;
    }*/


    //          Getters
    protected Ship getPlayerAircraftCarrier() {
        return playerAircraftCarrier;
    }

    protected Ship getComputerAircraftCarrier() {
        return computerAircraftCarrier;
    }

    public List<Point> getPlayerHits() {
        return playerHits;
    }

    public List<Point> getPlayerMisses() {
        return playerMisses;
    }

    public List<Point> getComputerHits() {
        return computerHits;
    }

    public List<Point> getComputerMisses() {
        return computerMisses;
    }
}
