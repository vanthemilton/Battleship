package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import spark.Request;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

/**
 * Created by dudeman on 1/31/17.
 */
public class Ship {


    //          Member Variables
    String name;
    int length;
    Point start;
    Point end;

    //          Constructor
    public Ship (String name) {

        setName(name);

        // size is based on name of ship, if unrecognized ship type the function sets size to 0
        if (name.toLowerCase().contains("aircraftcarrier")) {
            setLength(5);
        }

        else if (name.toLowerCase().contains("battleship")) {
            setLength(4);
        }

        else if (name.toLowerCase().contains("submarine") || name.toLowerCase().contains("cruiser")) {
            setLength(3);
        }

        else if (name.toLowerCase().contains("destroyer"))
            setLength(2);

        else
            setLength(0);


       start = new Point();
       end = new Point();

    }


    //          Setters and Getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;               // shouldn't need to resize an existing ship, but here it is
    }

    public Point getStart() {
        return start;
    }

    public void setStart(int x, int y) {   // Setting Start and End points reqs passing x and y coordinates
        this.start = new Point(x, y);
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(int x, int y) {
        this.end = new Point(x, y);
    }

    // RETURN ANY SHIP OBJECT DIRECTLY
    //public Ship getShip() {
        //return this;
    //}

}
