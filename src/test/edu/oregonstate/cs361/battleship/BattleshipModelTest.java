package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BattleshipModelTest {

    @Test
    public void TestPlayerAircraftCarrier() {

        BattleshipModel test = new BattleshipModel();

        //      Aircraft Carrier
        Ship boat = test.getShipByID("playerAircraftCarrier");
        boat.setEnd(1, 1);
        boat.setStart(1, 1);

        Point X = boat.getStart();
        Point Y = boat.getEnd();

        assertEquals("playerAircraftCarrier", boat.getName());
        assertEquals(5, boat.getLength());
        assertEquals(1, X.getDown());
        assertEquals(1, X.getAcross());
        assertEquals(1, Y.getDown());
        assertEquals(1, Y.getAcross());
    }

    @Test
    public void TestPlayerBattleship() {

        BattleshipModel test = new BattleshipModel();

        //      Battleship
        Ship boat = test.getShipByID("playerBattleship");
        boat.setEnd(1, 1);
        boat.setStart(1, 1);

        Point X = boat.getStart();
        Point Y = boat.getEnd();

        assertEquals("playerBattleship", boat.getName());
        assertEquals(4, boat.getLength());
        assertEquals(1, X.getDown());
        assertEquals(1, X.getAcross());
        assertEquals(1, Y.getDown());
        assertEquals(1, Y.getAcross());

    }

    @Test
    public void TestPlayerSubmarine() {

        BattleshipModel test = new BattleshipModel();

        //      Submarine
        Ship boat = test.getShipByID("playerSubmarine");
        boat.setEnd(1, 1);
        boat.setStart(1, 1);

        Point X = boat.getStart();
        Point Y = boat.getEnd();

        assertEquals("playerSubmarine", boat.getName());
        assertEquals(3, boat.getLength());
        assertEquals(1, X.getDown());
        assertEquals(1, X.getAcross());
        assertEquals(1, Y.getDown());
        assertEquals(1, Y.getAcross());

    }

    @Test
    public void TestPlayerCruiser() {

        BattleshipModel test = new BattleshipModel();

        //      Cruiser
        Ship boat = test.getShipByID("playerCruiser");
        boat.setEnd(1, 1);
        boat.setStart(1, 1);

        Point X = boat.getStart();
        Point Y = boat.getEnd();

        assertEquals("playerCruiser", boat.getName());
        assertEquals(3, boat.getLength());
        assertEquals(1, X.getDown());
        assertEquals(1, X.getAcross());
        assertEquals(1, Y.getDown());
        assertEquals(1, Y.getAcross());

    }

    @Test
    public void TestPlayerDestroyer() {

        BattleshipModel test = new BattleshipModel();

        //      Destroyer
        Ship boat = test.getShipByID("playerDestroyer");
        boat.setEnd(1, 1);
        boat.setStart(1, 1);

        Point X = boat.getStart();
        Point Y = boat.getEnd();

        assertEquals("playerDestroyer", boat.getName());
        assertEquals(2, boat.getLength());
        assertEquals(1, X.getDown());
        assertEquals(1, X.getAcross());
        assertEquals(1, Y.getDown());
        assertEquals(1, Y.getAcross());

    }

    @Test
    public void TestComputerAircraftCarrier() {

        BattleshipModel test = new BattleshipModel();

        //      Aircraft Carrier
        Ship boat = test.getShipByID("computerAircraftCarrier");
        boat.setEnd(1, 1);
        boat.setStart(1, 1);

        Point X = boat.getStart();
        Point Y = boat.getEnd();

        assertEquals("computerAircraftCarrier", boat.getName());
        assertEquals(5, boat.getLength());
        assertEquals(1, X.getDown());
        assertEquals(1, X.getAcross());
        assertEquals(1, Y.getDown());
        assertEquals(1, Y.getAcross());
    }

    @Test
    public void TestComputerBattleship() {

        BattleshipModel test = new BattleshipModel();

        //      Battleship
        Ship boat = test.getShipByID("computerBattleship");
        boat.setEnd(1, 1);
        boat.setStart(1, 1);

        Point X = boat.getStart();
        Point Y = boat.getEnd();

        assertEquals("computerBattleship", boat.getName());
        assertEquals(4, boat.getLength());
        assertEquals(1, X.getDown());
        assertEquals(1, X.getAcross());
        assertEquals(1, Y.getDown());
        assertEquals(1, Y.getAcross());

    }

    @Test
    public void TestComputerSubmarine() {

        BattleshipModel test = new BattleshipModel();

        //      Submarine
        Ship boat = test.getShipByID("computerSubmarine");
        boat.setEnd(1, 1);
        boat.setStart(1, 1);

        Point X = boat.getStart();
        Point Y = boat.getEnd();

        assertEquals("computerSubmarine", boat.getName());
        assertEquals(3, boat.getLength());
        assertEquals(1, X.getDown());
        assertEquals(1, X.getAcross());
        assertEquals(1, Y.getDown());
        assertEquals(1, Y.getAcross());

    }

    @Test
    public void TestComputerCruiser() {

        BattleshipModel test = new BattleshipModel();

        //      Cruiser
        Ship boat = test.getShipByID("computerCruiser");
        boat.setEnd(1, 1);
        boat.setStart(1, 1);

        Point X = boat.getStart();
        Point Y = boat.getEnd();

        assertEquals("computerCruiser", boat.getName());
        assertEquals(3, boat.getLength());
        assertEquals(1, X.getDown());
        assertEquals(1, X.getAcross());
        assertEquals(1, Y.getDown());
        assertEquals(1, Y.getAcross());

    }

    @Test
    public void TestComputerDestroyer() {

        BattleshipModel test = new BattleshipModel();

        //      Destroyer
        Ship boat = test.getShipByID("computerDestroyer");
        boat.setEnd(1, 1);
        boat.setStart(1, 1);

        Point X = boat.getStart();
        Point Y = boat.getEnd();

        assertEquals("computerDestroyer", boat.getName());
        assertEquals(2, boat.getLength());
        assertEquals(1, X.getDown());
        assertEquals(1, X.getAcross());
        assertEquals(1, Y.getDown());
        assertEquals(1, Y.getAcross());

    }

}

