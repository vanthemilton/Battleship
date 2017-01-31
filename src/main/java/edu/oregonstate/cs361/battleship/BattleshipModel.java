package edu.oregonstate.cs361.battleship;

/**
 * Created by michaelhilton on 1/26/17.
 */

public class BattleshipModel {

    //                  Constructor
    public BattleshipModel (String model, Point startPoint, Point endPoint) {

        setType(model);

        // size is based on model
        switch (model) {
            case "aircraftCarrier": setSize(5);
                break;
            case "battleship": setSize(4);
                break;
            case "cruiser": setSize(3);
                break;
            case "destroyer": setSize(2);
                break;
            case "submarine": setSize(3);
                break;

            default: break;
        }

        start = startPoint;
        end = endPoint;

    }


    //                  Member variables
    private String type;
    private int size;

    // Locational data
    private Point start;
    private Point end;


    //                  Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String name) {
        this.type = name;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }


    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }


    public int test() {
        return 1;
    }
}
