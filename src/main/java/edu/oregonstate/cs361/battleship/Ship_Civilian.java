package edu.oregonstate.cs361.battleship;

/**
 * Created by dudeman on 2/28/17.
 */
public class Ship_Civilian extends Ship {

    //          Member Variables
  /*  private String name;
    private Point start;
    private Point end;
    private int length;
    private int health;*/

    //          Constructor
    public Ship_Civilian (String name) {

        setName(name);

        // size is based on name of ship, if unrecognized ship type the function sets size to 0
        if (name.toLowerCase().contains("clipper")) {
            setLength(3);
            setHealth(1);

            start = new Point();
            end = new Point();
        }

        else if (name.toLowerCase().contains("dinghy")) {
            setLength(1);
            setHealth(1);

            start = new Point();
            end = new Point();
        }

        else {
            setLength(0);
            setHealth(0);

            start = new Point();
            end = new Point();
        }

        //start = new Point();
        //end = new Point();

    }
}
