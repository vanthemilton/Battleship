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
import java.io.OutputStream;


import static edu.oregonstate.cs361.battleship.Main.Hit;
import static edu.oregonstate.cs361.battleship.Main.placeShip;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static spark.Spark.awaitInitialization;


/**
 * Created by michaelhilton on 1/26/17.
 */
class MainTest {

    @BeforeAll
    public static void beforeClass() {

        Main.main(null);
        awaitInitialization();
    }

    @AfterAll
    public static void afterClass() {
        Spark.stop();
    }

    @Test
    public void testGetModel() {
        TestResponse res = request("GET", "/model", null);

        BattleshipModel test = new BattleshipModel();
        Gson gson = new Gson();
        String check = gson.toJson(test);

        assertEquals(200, res.status);
        assertEquals(check,res.body);
    }

    @Test
    public void testPlaceBattleshipH() {
        BattleshipModel model = new BattleshipModel();
        Gson gson = new Gson();
        String jason = gson.toJson(model);
        TestResponse res = request("POST", "/placeShip/battleship/1/1/horizontal", jason);
        assertEquals(res.status, 200);

    }

    @Test
    public void testPlaceBattleshipV() {
        BattleshipModel model = new BattleshipModel();
        Gson gson = new Gson();
        String jason = gson.toJson(model);
        TestResponse res = request("POST", "/placeShip/battleShip/1/1/vertical", jason);
        assertEquals(res.status, 200);

        TestResponse res2 = request("POST", "/placeShip/AircraftCarrier/2/1/vertical", jason);
        assertEquals(res2.status, 200);

        TestResponse res3 = request("POST", "/placeShip/Cruiser/1/1/vertical", jason);
        assertEquals(res3.status, 200);
    }

    @Test
    public void testPlaceBattleshipVWrong() {
        BattleshipModel model = new BattleshipModel();
        Gson gson = new Gson();
        String jason = gson.toJson(model);
        TestResponse res = request("POST", "/placeShip/battleShip/11/1/vertical", jason);
        assertEquals(res.status, 200);

    }


    @Test
    public void testFireAt() {
        BattleshipModel model = new BattleshipModel();
        model.getPlayerAircraftCarrier().setStart(1,1);
        model.getPlayerAircraftCarrier().setEnd(1,5);
        model.getPlayerBattleship().setStart(2,1);
        model.getPlayerBattleship().setEnd(2,4);
        model.getPlayerCruiser().setStart(3,1);
        model.getPlayerCruiser().setEnd(3,3);
        model.getPlayerDestroyer().setStart(4,1);
        model.getPlayerDestroyer().setEnd(4,2);
        model.getPlayerSubmarine().setStart(5,1);
        model.getPlayerSubmarine().setEnd(5,3);

        model.getComputerAircraftCarrier().setStart(1,1);
        model.getComputerAircraftCarrier().setEnd(1,4);
        model.getComputerBattleship().setStart(2,1);
        model.getComputerBattleship().setEnd(2,4);
        model.getComputerCruiser().setStart(3,1);
        model.getComputerCruiser().setEnd(3,3);
        model.getComputerDestroyer().setStart(4,1);
        model.getComputerDestroyer().setEnd(4,2);
        model.getComputerSubmarine().setStart(5,1);
        model.getComputerSubmarine().setEnd(5,3);

        Gson gson = new Gson();
        String jason = gson.toJson(model);

        for(int i = 0;i<100;i++) {
            TestResponse res = request("POST", "/fire/1/1", jason);
            assertEquals(res.status, 200);
        }

        TestResponse res1 = request("POST", "/fire/2/2", jason);
        assertEquals(res1.status, 200);

        TestResponse res2 = request("POST", "/fire/4/4", jason);
        assertEquals(res2.status, 200);

        TestResponse res3 = request("POST", "/fire/6/6", jason);
        assertEquals(res3.status, 200);
    }

    @Test
    public void testHitH() {
        Point shipStart = new Point(1, 1);
        Point shipEnd = new Point(1, 5);
        Point shot = new Point(1, 3);
        assertEquals(true, Hit(shipStart, shipEnd, shot));

    }


    @Test
    public void testMissH() {
        Point shipStart = new Point(1, 1);
        Point shipEnd = new Point(1, 5);
        Point shot = new Point(3, 3);
        assertEquals(false, Hit(shipStart, shipEnd, shot));

    }

    @Test
    public void testHitV() {
        Point shipStart = new Point(1, 1);
        Point shipEnd = new Point(5, 1);
        Point shot = new Point(3, 1);
        assertEquals(true, Hit(shipStart, shipEnd, shot));

    }

    @Test
    public void testMissV() {
        Point shipStart = new Point(1, 1);
        Point shipEnd = new Point(5, 1);
        Point shot = new Point(3, 3);
        assertEquals(false, Hit(shipStart, shipEnd, shot));

    }

    private TestResponse request(String method, String path, String body) {
        try {
            URL url = new URL("http://localhost:4567" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            if(body != null) {
                connection.setDoInput(true);
                byte[] outputInBytes = body.getBytes("UTF-8");
                OutputStream os = connection.getOutputStream();
                os.write(outputInBytes);
            }
            connection.connect();
            body = IOUtils.toString(connection.getInputStream());
            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    private static class TestResponse {

        public final String body;
        public final int status;

        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }

        public Map<String,String> json() {
            return new Gson().fromJson(body, HashMap.class);
        }
    }


}