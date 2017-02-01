package edu.oregonstate.cs361.battleship;

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

    Point[] playerHits = new Point[100];
    Point[] playerMisses = new Point[100];
    Point[] computerHits = new Point[100];
    Point[] computerMisses = new Point[100];


    //                  Constructor
    public BattleshipModel (String model, Point startPoint, Point endPoint) {

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

    }


    //          Add Point object to an array
    public boolean addPointtoArray(Point somePoint, Point[] someArray) {

    if (somePoint.getAcross() > 10 || somePoint.getAcross() < 0 || somePoint.getDown() > 10 || somePoint.getDown() < 0)
        return false;

    else {
        someArray.add(somePoint);       // NEED TO FIGURE OUT IF THERE IS A PUSH EQU TO ARRAYS IN JAVA
    }
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
