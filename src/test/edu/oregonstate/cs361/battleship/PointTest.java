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

class PointTest {

    @Test
    public void testAcross() {
        Point Testing = new Point();
        assertEquals(0,Testing.getAcross());
    }

    @Test
    public void testDown(){
        Point Testing = new Point();
        assertEquals(0,Testing.getDown());
    }

    @Test
    public void testAcross2(){
        Point Testing = new Point();
        Testing.setAcross(1);
        assertEquals(1,Testing.getAcross());
    }

    @Test
    public void testDown2(){
        Point Testing = new Point();
        Testing.setDown(1);
        assertEquals(1,Testing.getDown());
    }
}