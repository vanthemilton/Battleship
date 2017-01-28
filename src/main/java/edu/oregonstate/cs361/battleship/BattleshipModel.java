package edu.oregonstate.cs361.battleship;

/**
 * Created by michaelhilton on 1/26/17.
 */

public class BattleshipModel {

    //                  Constructor
    public BattleshipModel (String type, int length, int startHorizontal, int startVertical, int endHorizontal, int endVertical) {

        setName(type);
        setSize(length);

        setStartAcross(startHorizontal);
        setStartDown(startVertical);

        setEndAcross(endHorizontal);
        setEndDown(endVertical);
    }


    //                  Member variables

    private String name;
    private int size;

    // Locational data
    private int startAcross;
    private int startDown;
    private int endAcross;
    private int endDown;


    //                  Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartAcross() {
        return startAcross;
    }

    public void setStartAcross(int startAcross) {
        this.startAcross = startAcross;
    }

    public int getStartDown() {
        return startDown;
    }

    public void setStartDown(int startDown) {
        this.startDown = startDown;
    }

    public int getEndAcross() {
        return endAcross;
    }

    public void setEndAcross(int endAcross) {
        this.endAcross = endAcross;
    }

    public int getEndDown() {
        return endDown;
    }

    public void setEndDown(int endDown) {
        this.endDown = endDown;
    }
}
