package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dudeman on 2/28/17.
 */
public class BattleshipModelNormal extends BattleshipModel {

    //                  Member variables
    //private Ship playerAircraftCarrier;
    private Ship playerBattleship;
    private Ship playerSubmarine;
    private Ship playerCruiser;
    private Ship playerDestroyer;


    //private Ship computerAircraftCarrier;
    private Ship computerBattleship;
    private Ship computerSubmarine;
    private Ship computerCruiser;
    private Ship computerDestroyer;

    //                  Constructor
    public BattleshipModelNormal() {

        playerAircraftCarrier = new Ship("playerAircraftCarrier");
        playerBattleship = new Ship("playerBattleship");
        playerSubmarine = new Ship("playerSubmarine");
        playerCruiser = new Ship("playerCruiser");
        playerDestroyer = new Ship("playerDestroyer");


        computerAircraftCarrier = new Ship("computerAircraftCarrier");
        computerBattleship = new Ship("computerBattleship");
        computerSubmarine = new Ship("computerSubmarine");
        computerCruiser = new Ship("computerCruiser");
        computerDestroyer = new Ship("computerDestroyer");

        playerHits = new ArrayList<Point>();
        playerMisses = new ArrayList<Point>();
        computerHits = new ArrayList<Point>();
        computerMisses = new ArrayList<Point>();

        playerArray[1] = playerBattleship;
        playerArray[2] = playerSubmarine;
        playerArray[3] = playerCruiser;
        playerArray[4] = playerDestroyer;

        computerArray[1] = computerBattleship;
        computerArray[2] = computerSubmarine;
        computerArray[3] = computerCruiser;
        computerArray[4] = computerDestroyer;
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

            else if (id.toLowerCase().contains("cruiser")) {
                return getComputerCruiser();
            }

            else if (id.toLowerCase().contains("destroyer")) {
                return getComputerDestroyer();
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

            else if (id.toLowerCase().contains("cruiser")) {
                return getPlayerCruiser();
            }

            else if (id.toLowerCase().contains("destroyer")) {
                return getPlayerDestroyer();
            }
        }

        // DEFAULTS TO THIS IF ID FORMAT IS INCORRECT:
        Ship PizzaShip = new Ship("PizzaShip");
        PizzaShip.setName("PizzaShip");
        return PizzaShip;
    }

    //              Getters
    public Ship getPlayerBattleship() {
        return playerBattleship;
    }

    public Ship getPlayerSubmarine() {
        return playerSubmarine;
    }

    public Ship getPlayerCruiser() {
        return playerCruiser;
    }

    public Ship getPlayerDestroyer() {
        return playerDestroyer;
    }

    public Ship getComputerBattleship() {
        return computerBattleship;
    }

    public Ship getComputerSubmarine() {
        return computerSubmarine;
    }

    public Ship getComputerCruiser() {
        return computerCruiser;
    }

    public Ship getComputerDestroyer() {
        return computerDestroyer;
    }

    public static Ship[] resetArrayNormal(BattleshipModelNormal model, boolean player) {
        Ship[] array = new Ship[5];

        if(player){
            array[0] = model.getPlayerAircraftCarrier();
            array[1] = model.getPlayerBattleship();
            array[2] = model.getPlayerSubmarine();
            array[3] = model.getPlayerCruiser();
            array[4] = model.getPlayerDestroyer();

        } else {
            array[0] = model.getComputerAircraftCarrier();
            array[1] = model.getComputerBattleship();
            array[2] = model.getComputerSubmarine();
            array[3] = model.getComputerCruiser();
            array[4] = model.getComputerDestroyer();
        }

        return array;
    }

}
