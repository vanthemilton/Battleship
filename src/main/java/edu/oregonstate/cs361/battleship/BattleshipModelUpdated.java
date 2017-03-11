package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;
import java.util.List;


public class BattleshipModelUpdated extends BattleshipModel {


    //                  Member variables
    //private Ship playerAircraftCarrier;
    private Ship_Stealth playerBattleship;
    private Ship_Stealth playerSubmarine;
    private Ship_Civilian playerClipper;
    private Ship_Civilian playerDinghy;


    //private Ship computerAircraftCarrier;
    private Ship_Stealth computerBattleship;
    private Ship_Stealth computerSubmarine;
    private Ship_Civilian computerClipper;
    private Ship_Civilian computerDinghy;

    private boolean scanned;

    //                  Constructor
    public BattleshipModelUpdated() {

        playerAircraftCarrier = new Ship("playerAircraftCarrier");
        playerBattleship = new Ship_Stealth("playerBattleship");
        playerSubmarine = new Ship_Stealth("playerSubmarine");
        playerClipper = new Ship_Civilian("playerClipper");
        playerDinghy = new Ship_Civilian("playerDinghy");


        computerAircraftCarrier = new Ship("computerAircraftCarrier");
        computerBattleship = new Ship_Stealth("computerBattleship");
        computerSubmarine = new Ship_Stealth("computerSubmarine");
        computerClipper = new Ship_Civilian("computerClipper");
        computerDinghy = new Ship_Civilian("computerDinghy");

        playerHits = new ArrayList<Point>();
        playerMisses = new ArrayList<Point>();
        computerHits = new ArrayList<Point>();
        computerMisses = new ArrayList<Point>();

        playerArray[1] = playerBattleship;
        playerArray[2] = playerSubmarine;
        playerArray[3] = playerClipper;
        playerArray[4] = playerDinghy;

        computerArray[1] = computerBattleship;
        computerArray[2] = computerSubmarine;
        computerArray[3] = computerClipper;
        computerArray[4] = computerDinghy;

        scanned = false;
    }

    //          Get ship by ID function, assumes user only selects from drop down menu on front end
    public Ship getShipByID(String id) {

        if(id.toLowerCase().contains("computer")) {

            if (id.toLowerCase().contains("aircraftcarrier")) {
                return getComputerAircraftCarrier();

            } else if (id.toLowerCase().contains("clipper")) {
                return getComputerClipper();

            } else if (id.toLowerCase().contains("dinghy")) {
                return getComputerDinghy();

            } else if (id.toLowerCase().contains("battleship")) {
                return getComputerBattleship();

            } else if (id.toLowerCase().contains("submarine") ) {
                return getComputerSubmarine();

            }

        } else {

            if (id.toLowerCase().contains("aircraftcarrier")) {
                return getPlayerAircraftCarrier();

            } else if (id.toLowerCase().contains("clipper")) {
                return getPlayerClipper();

            } else if (id.toLowerCase().contains("dinghy")) {
                return getPlayerDinghy();

            } else if (id.toLowerCase().contains("battleship")) {
                return getPlayerBattleship();

            } else if (id.toLowerCase().contains("submarine") ) {
                return getPlayerSubmarine();

            }

        }

        // DEFAULTS TO THIS IF ID FORMAT IS INCORRECT:
        Ship PizzaShip = new Ship("PizzaShip");
        PizzaShip.setName("PizzaShip");
        return PizzaShip;
    }

    public Ship_Stealth getPlayerBattleship() {
        return playerBattleship;
    }

    public Ship_Stealth getPlayerSubmarine() {
        return playerSubmarine;
    }

    public Ship_Civilian getPlayerClipper() {
        return playerClipper;
    }

    public Ship_Civilian getPlayerDinghy() {
        return playerDinghy;
    }

    public Ship_Stealth getComputerBattleship() {
        return computerBattleship;
    }

    public Ship_Stealth getComputerSubmarine() {
        return computerSubmarine;
    }

    public Ship_Civilian getComputerClipper() {
        return computerClipper;
    }

    public Ship_Civilian getComputerDinghy() {
        return computerDinghy;
    }

    public void setScanned(boolean scanned) {
            this.scanned = scanned;
    }

    public static Ship[] resetArrayUpdated(BattleshipModelUpdated model, boolean player) {
        Ship[] array = new Ship[5];

        if(player){
            array[0] = model.getPlayerAircraftCarrier();
            array[1] = model.getPlayerBattleship();
            array[2] = model.getPlayerSubmarine();
            array[3] = model.getPlayerClipper();
            array[4] = model.getPlayerDinghy();

        } else {
            array[0] = model.getComputerAircraftCarrier();
            array[1] = model.getComputerBattleship();
            array[2] = model.getComputerSubmarine();
            array[3] = model.getComputerClipper();
            array[4] = model.getComputerDinghy();
        }

        return array;
    }
}
