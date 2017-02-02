package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelhilton on 1/26/17.
 */

public class BattleshipModel {


    //                  Member variables
    private Ship playerAircraftCarrier;
    private Ship playerBattleship;
    private Ship playerCruiser;
    private Ship playerDestroyer;
    private Ship playerSubmarine;

    private Ship computerAircraftCarrier;
    private Ship computerBattleship;
    private Ship computerCruiser;
    private Ship computerDestroyer;
    private Ship computerSubmarine;

    List<Point> playerHits;
    List<Point> playerMisses;
    List<Point> computerHits;
    List<Point> computerMisses;


    //                  Constructor
    public BattleshipModel () {

        playerAircraftCarrier = new Ship("AircraftCarrier");
        playerBattleship = new Ship("Battleship");
        playerCruiser = new Ship("Cruiser");
        playerDestroyer = new Ship("Destroyer");
        playerSubmarine = new Ship("Submarine");

        playerAircraftCarrier = new Ship("AircraftCarrier");
        playerBattleship = new Ship("Battleship");
        playerCruiser = new Ship("Cruiser");
        playerDestroyer = new Ship("Destroyer");
        playerSubmarine = new Ship("Submarine");

        playerHits = new ArrayList<Point>();
        playerMisses = new ArrayList<Point>();
        computerHits = new ArrayList<Point>();
        computerMisses = new ArrayList<Point>();
    }


    //          Add Point object to an array function
    public boolean addPointtoArray(Point somePoint, List someArray) {

    if (somePoint.getAcross() > 10 || somePoint.getAcross() < 0 || somePoint.getDown() > 10 || somePoint.getDown() < 0)
        return false;

    else {
        someArray.add(somePoint);
        return true;
    }
}


    //          Get ship by ID function, assumes user only selects from drop down menu on front end
    public Ship getShipByID(String id) {

        if(id.toLowerCase().contains("computer")) {

            if (id.toLowerCase().contains("aircraftCarrier")) {
                return computerAircraftCarrier;
            }

            else if (id.toLowerCase().contains("battleship")) {
                return computerBattleship;
            }

            else if (id.toLowerCase().contains("submarine") ) {
                return computerSubmarine;
            }

            else if (id.toLowerCase().contains("cruiser")) {
                return computerCruiser;
            }

            else if (id.toLowerCase().contains("destroyer")) {
                return computerDestroyer;
            }
        }

        else {

            if (id.toLowerCase().contains("aircraftCarrier")) {
                return playerAircraftCarrier;
            }

            else if (id.toLowerCase().contains("battleship")) {
                return playerBattleship;
            }

            else if (id.toLowerCase().contains("submarine") ) {
                return playerSubmarine;
            }

            else if (id.toLowerCase().contains("cruiser")) {
                return playerCruiser;
            }

            else if (id.toLowerCase().contains("destroyer")) {
                return playerDestroyer;
            }
        }

        // DEFAULTS TO THIS IF ID FORMAT IS INCORRECT:
        return computerAircraftCarrier;
    }

    //          Getters and Setters
    public Ship getPlayerAircraftCarrier() {
        return playerAircraftCarrier;
    }

    public void setPlayerAircraftCarrier(Ship playerAircraftCarrier) {
        this.playerAircraftCarrier = playerAircraftCarrier;
    }

    public Ship getPlayerBattleship() {
        return playerBattleship;
    }

    public void setPlayerBattleship(Ship playerBattleship) {
        this.playerBattleship = playerBattleship;
    }

    public Ship getPlayerCruiser() {
        return playerCruiser;
    }

    public void setPlayerCruiser(Ship playerCruiser) {
        this.playerCruiser = playerCruiser;
    }

    public Ship getPlayerDestroyer() {
        return playerDestroyer;
    }

    public void setPlayerDestroyer(Ship playerDestroyer) {
        this.playerDestroyer = playerDestroyer;
    }

    public Ship getPlayerSubmarine() {
        return playerSubmarine;
    }

    public void setPlayerSubmarine(Ship playerSubmarine) {
        this.playerSubmarine = playerSubmarine;
    }

    public Ship getComputerAircraftCarrier() {
        return computerAircraftCarrier;
    }

    public void setComputerAircraftCarrier(Ship computerAircraftCarrier) {
        this.computerAircraftCarrier = computerAircraftCarrier;
    }

    public Ship getComputerBattleship() {
        return computerBattleship;
    }

    public void setComputerBattleship(Ship computerBattleship) {
        this.computerBattleship = computerBattleship;
    }

    public Ship getComputerCruiser() {
        return computerCruiser;
    }

    public void setComputerCruiser(Ship computerCruiser) {
        this.computerCruiser = computerCruiser;
    }

    public Ship getComputerDestroyer() {
        return computerDestroyer;
    }

    public void setComputerDestroyer(Ship computerDestroyer) {
        this.computerDestroyer = computerDestroyer;
    }

    public Ship getComputerSubmarine() {
        return computerSubmarine;
    }

    public void setComputerSubmarine(Ship computerSubmarine) {
        this.computerSubmarine = computerSubmarine;
    }
}
