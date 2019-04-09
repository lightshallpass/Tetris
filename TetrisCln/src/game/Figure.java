package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static sun.dc.pr.Rasterizer.TILE_SIZE;

public class Figure {

    public int x, y;

    private int figureType;


    private Color color;
    private List<Piece> pieces;

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public Color getColor() {
        return color;
    }

    public Figure(int figureType){
        this.pieces = new ArrayList<>();
        Piece first = null;
        Piece second = null;
        Piece third = null;
        Piece fourth = null;
        switch(figureType){
            case 0:
                color = Color.BLUE;
                first = new Piece(this, 0, Direction.RIGHT);
                second = new Piece(this, 1, Direction.LEFT);
                third = new Piece(this, 1, Direction.RIGHT);
                fourth = new Piece(this, 1, Direction.DOWN);
                break;
            case 1:
                first = new Piece(this, 0, Direction.RIGHT);
                second = new Piece(this, 1, Direction.RIGHT);
                third = new Piece(this, 1, Direction.DOWN);
                fourth = new Piece(this, 1, Direction.DOWN, Direction.RIGHT);
                color = Color.RED;
                break;
            case 2:
                first = new Piece(this, 0, Direction.RIGHT);
                second = new Piece(this, 1, Direction.DOWN);
                third = new Piece(this, 2, Direction.DOWN);
                fourth = new Piece(this, 3, Direction.DOWN);
                color = Color.GREEN;
                break;
            case 3:
                first = new Piece(this, 0, Direction.RIGHT);
                second = new Piece(this, 1, Direction.LEFT);
                third = new Piece(this, 1, Direction.RIGHT, Direction.DOWN);
                fourth = new Piece(this, 1, Direction.DOWN);
                color = Color.YELLOW;
                break;
            case 4:
                first = new Piece(this, 0, Direction.RIGHT);
                second = new Piece(this, 1, Direction.UP);
                third = new Piece(this, 2, Direction.UP);
                fourth = new Piece(this, 1, Direction.LEFT);
                color = Color.GREY;
                break;
            case 5:
                first = new Piece(this, 0, Direction.RIGHT);
                second = new Piece(this, 1, Direction.UP);
                third = new Piece(this, 2, Direction.UP);
                fourth = new Piece(this, 1, Direction.RIGHT);
                color = Color.BLACK;
                break;
            case 6:
                first = new Piece(this, 0, Direction.RIGHT);
                second = new Piece(this, 1, Direction.RIGHT);
                third = new Piece(this, 1, Direction.LEFT, Direction.DOWN);
                fourth = new Piece(this, 1, Direction.DOWN);
                color = Color.DEEPPINK;
                break;
        }
        this.pieces.addAll(Arrays.asList(first,second,third,fourth));
    }


    public void move(int dx, int dy){
        x += dx;
        y += dy;
        pieces.forEach(p ->{
            p.x += dx;
            p.y = dy;
        });
    }

    public void move(Direction direction){
        move(direction.x,direction.y);
    }

    public void draw(GraphicsContext g) {
        g.setFill(color);
        pieces.forEach(p -> g.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE));
    }

    public void rotateBack() {
        pieces.forEach(p -> p.setDirection(p.directions.stream().map(Direction::prev).collect(Collectors.toList()).toArray(new Direction[0])));
    }

    public void rotate() {
        pieces.forEach(p -> p.setDirection(p.directions.stream().map(Direction::next).collect(Collectors.toList()).toArray(new Direction[0])));
    }

    public void detach(int x, int y) {
        pieces.removeIf(p -> p.x == x && p.y == y);
    }


}
