package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;
import java.util.List;


public class BattleshipModelUpdated extends BattleshipModel {


    //                  Member variables
    private Ship playerAircraftCarrier;
    private Ship_Stealth playerBattleship;
    private Ship_Stealth playerSubmarine;
    private Ship_Civilian playerClipper;
    private Ship_Civilian playerDinghy;


    private Ship computerAircraftCarrier;
    private Ship_Stealth computerBattleship;
    private Ship_Stealth computerSubmarine;
    private Ship_Civilian computerClipper;
    private Ship_Civilian computerDinghy;

    // all ships for player or computer are collected in these lists
    private List<Ship> playerShips;
    private List<Ship> computerShips;

    private List<Point> playerHits;
    private List<Point> playerMisses;
    private List<Point> computerHits;
    private List<Point> computerMisses;


    //                  Constructor
    public BattleshipModelUpdated() {

        playerAircraftCarrier = new Ship("playerAircraftCarrier");
        playerBattleship = new Ship_Stealth("playerBattleship");
        playerSubmarine = new Ship_Stealth("playerSubmarine");
        playerClipper = new Ship_Civilian("playerCruiser");
        playerDinghy = new Ship_Civilian("playerDestroyer");


        computerAircraftCarrier = new Ship("playerAircraftCarrier");
        computerBattleship = new Ship_Stealth("playerBattleship");
        computerSubmarine = new Ship_Stealth("playerSubmarine");
        computerClipper = new Ship_Civilian("playerCruiser");
        computerDinghy = new Ship_Civilian("playerDestroyer");

        playerHits = new ArrayList<Point>();
        playerMisses = new ArrayList<Point>();
        computerHits = new ArrayList<Point>();
        computerMisses = new ArrayList<Point>();

        playerShips = new ArrayList<Ship>();
        playerShips.add(playerAircraftCarrier);
        playerShips.add(playerBattleship);
        playerShips.add(playerClipper);
        playerShips.add(playerDinghy);
        playerShips.add(playerSubmarine);

        computerShips = new ArrayList<Ship>();
        computerShips.add(computerAircraftCarrier);
        computerShips.add(computerBattleship);
        computerShips.add(computerClipper);
        computerShips.add(computerDinghy);
        computerShips.add(computerSubmarine);
    }


    //          Add Point object to an array function
    public boolean addPointtoArray(Point somePoint, List someArray) {

        if (somePoint.getAcross() > 10 || somePoint.getAcross() < 1 || somePoint.getDown() > 10 || somePoint.getDown() < 1)

            return false;

        else {

            someArray.add(somePoint);
            return true;

        }
    }

    //          Get ship by ID function, assumes user only selects from drop down menu on front end
    public Ship getShipByID(String id) {

        if(id.toLowerCase().contains("computer")) {

            if (id.toLowerCase().contains("aircraftcarrier")) {
                return getComputerAircraftCarrier();
            }

            else if (id.toLowerCase().contains("battleship")) {
                return getComputerBattleship();
            }

            else if (id.toLowerCase().contains("submarine") ) {
                return getComputerSubmarine();
            }

            else if (id.toLowerCase().contains("clipper")) {
                return getComputerClipper();
            }

            else if (id.toLowerCase().contains("dinghy")) {
                return getComputerDinghy();
            }
        }

        else {

            if (id.toLowerCase().contains("aircraftcarrier")) {
                return getPlayerAircraftCarrier();
            }

            else if (id.toLowerCase().contains("battleship")) {
                return getPlayerBattleship();
            }

            else if (id.toLowerCase().contains("submarine") ) {
                return getPlayerSubmarine();
            }

            else if (id.toLowerCase().contains("clipper")) {
                return getPlayerClipper();
            }

            else if (id.toLowerCase().contains("dinghy")) {
                return getPlayerDinghy();
            }
        }

        // DEFAULTS TO THIS IF ID FORMAT IS INCORRECT:
        Ship PizzaShip = new Ship("PizzaShip");
        PizzaShip.setName("PizzaShip");
        return PizzaShip;
    }


    //          Getters and Setters


    public Ship_Civilian getPlayerClipper() {
        return playerClipper;
    }

    public void setPlayerClipper(Ship_Civilian playerClipper) {
        this.playerClipper = playerClipper;
    }

    public Ship_Civilian getPlayerDinghy() {
        return playerDinghy;
    }

    public void setPlayerDinghy(Ship_Civilian playerDinghy) {
        this.playerDinghy = playerDinghy;
    }

    public Ship_Civilian getComputerClipper() {
        return computerClipper;
    }

    public void setComputerClipper(Ship_Civilian computerClipper) {
        this.computerClipper = computerClipper;
    }

    public Ship_Civilian getComputerDinghy() {
        return computerDinghy;
    }

    public void setComputerDinghy(Ship_Civilian computerDinghy) {
        this.computerDinghy = computerDinghy;
    }
}
