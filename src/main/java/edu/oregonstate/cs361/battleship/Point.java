package edu.oregonstate.cs361.battleship;

/**
 * Created by dudeman on 1/30/17.
 */
public class Point {

    //              Constructor
    public Point(int X_Coordinate, int Y_Coordinate) {
        setAcross(X_Coordinate);
        setDown(Y_Coordinate);
    }


    //              Member variables
    int Across;
    int Down;


    //              Getters and Setters
    public int getAcross() {
        return Across;
    }

    public void setAcross(int across) {
        Across = across;
    }

    public int getDown() {
        return Down;
    }

    public void setDown(int down) {
        Down = down;
    }

}
