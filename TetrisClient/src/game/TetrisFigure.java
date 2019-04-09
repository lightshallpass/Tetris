package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TetrisFigure {

    private int x, y, figureType;
    private Color  color;
    private List<Square> squares;

    public List<Square> getSquares() {
        return squares;
    }

    public TetrisFigure(Color color, int figureType){
        this.color = color;
        squares = new ArrayList<>();
        switch(figureType){
            case 0:
                squares.add(new Square(1, Direction.DOWN));
                squares.add(new Square(2,Direction.RIGHT));
                squares.add(new Square(3,Direction.RIGHT));
                squares.add(new Square(2,Direction.DOWN));

                break;
            case 1:
                squares.add(new Square(1, Direction.DOWN));
                squares.add(new Square(2,Direction.RIGHT));
                squares.add(new Square(3,Direction.RIGHT));
                squares.add(new Square(2,Direction.DOWN));
                break;
            case 2:
                squares.add(new Square(1, Direction.DOWN));
                squares.add(new Square(2,Direction.RIGHT));
                squares.add(new Square(3,Direction.RIGHT));
                squares.add(new Square(2,Direction.DOWN));
                break;
            case 3:
                squares.add(new Square(1, Direction.DOWN));
                squares.add(new Square(2,Direction.RIGHT));
                squares.add(new Square(3,Direction.RIGHT));
                squares.add(new Square(2,Direction.DOWN));
                break;
            case 4:
                squares.add(new Square(1, Direction.DOWN));
                squares.add(new Square(2,Direction.RIGHT));
                squares.add(new Square(3,Direction.RIGHT));
                squares.add(new Square(2,Direction.DOWN));
                break;
            case 5:
                squares.add(new Square(1, Direction.DOWN));
                squares.add(new Square(2,Direction.RIGHT));
                squares.add(new Square(3,Direction.RIGHT));
                squares.add(new Square(2,Direction.DOWN));
                break;
            case 6:
                squares.add(new Square(1, Direction.DOWN));
                squares.add(new Square(2,Direction.RIGHT));
                squares.add(new Square(3,Direction.RIGHT));
                squares.add(new Square(2,Direction.DOWN));
                break;
                default:
                    squares.add(new Square(0, Direction.DOWN));
                    squares.add(new Square(1,Direction.RIGHT));
                    squares.add(new Square(2,Direction.RIGHT));
                    squares.add(new Square(1,Direction.DOWN));
                    break;
        }
    }

    public TetrisFigure(Color color, Square... squares){
        this.color = color;
        this.squares = new ArrayList<>(Arrays.asList(squares));
        for(Square s: this.squares){
            s.setParent(this);
        }
    }

    public void move(int dx, int dy){
        x += dx;
        y += dy;

        squares.forEach(p -> {
            p.setX(p.getX() + dx);
            p.setY(p.getY() + dy);
        });
    }

    public void move(Direction direction){
        move(direction.x, direction.y);
    }

    public void draw(GraphicsContext g, int TILE_SIZE, int GRID_WIDTH, int GRID_HEIGHT){
        g.setFill(color);
        squares.forEach( p -> {
            g.fillRect(p.getX() * TILE_SIZE,p.getY() * TILE_SIZE,TILE_SIZE,TILE_SIZE);
        });

    }

    public void rotate(){
        squares.forEach(p -> p.setDirections(p.getDirections().stream().map(Direction::next).collect(Collectors.toList()).toArray(new Direction[0])));
    }

    public void rotateBack() {
        squares.forEach(p -> p.setDirections(p.getDirections().stream().map(Direction::prev).collect(Collectors.toList()).toArray(new Direction[0])));
    }

    public void detach(int x, int y){
        squares.removeIf(p -> p.getX() == x && p.getY() == y);
    }



    public TetrisFigure copy(){
        return new TetrisFigure(color, squares.stream()
        .map(Square::copy).collect(Collectors.toList()).toArray(new Square[0]));
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
