package game;

import java.util.Arrays;
import java.util.List;

public class Square {

    private int x, y;
    private List<Direction> directions;
    private TetrisFigure parent;
    private int distance;

    public Square(int distance, Direction... directions){
        this.distance = distance;
        this.directions = Arrays.asList(directions);
    }


    public void setParent(TetrisFigure parent){
        this.parent = parent;
        refresh();
    }

    public void setDirections(Direction... directions){
        this.directions = Arrays.asList(directions);
        refresh();
    }

    private void refresh(){
        int dx = 0;
        int dy = 0;

        for (Direction d: directions){
            dx += distance * d.x;
            dy += distance * d.y;
        }
        x = parent.getX() + dx;
        y = parent.getY() + dy;

    }


    public List<Direction> getDirections() {
        return directions;
    }


    public Square copy() {
        return new Square(distance, directions.toArray(new Direction[0]));
    }



    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}
