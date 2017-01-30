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

import static org.junit.jupiter.api.Assertions.*;
import static spark.Spark.awaitInitialization;

class BattleshipModelTest {

    @Test
    public void testTest() {
        BattleshipModel ship = new BattleshipModel(SSMcBoatyBoat, 10, 1, 1, 10, 1);
        assertEquals(1, ship.test());
    }

}

