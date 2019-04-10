package game;

import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

public class Piece {


    public int x, y, distance;
    private Figure figure;
    public List<Direction> directions;

    public Piece(Figure figure, int distance, Direction... directions){
        this.distance = distance;
        this.directions = Arrays.asList(directions);
        setParent(figure);
    }

    public void setParent(Figure parent){
        this.figure = parent;
        refresh();
    }

    public void setDirection(Direction... directions){
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
        x = figure.x + dx;
        y = figure.y + dy;
    }

    public List<Direction> getDirections() {
        return directions;
    }






}
