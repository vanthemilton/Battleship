package edu.oregonstate.cs361.battleship;

/**
 * Created by michaelhilton on 1/26/17.
 */

public class BattleshipModel {

    //                  Constructor
    public BattleshipModel (String model, int startHorizontal, int startVertical, int endHorizontal, int endVertical) {

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

        setStartX(startHorizontal);
        setStartY(startVertical);

        setEndX(endHorizontal);
        setEndY(endVertical);

    }


    //                  Member variables
    private String type;
    private int size;

    // Locational data
    private int startX;
    private int startY;
    private int endX;
    private int endY;


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

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }



    public int test() {
        return 1;
    }
}
