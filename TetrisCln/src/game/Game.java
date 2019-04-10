package game;

import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static sun.dc.pr.Rasterizer.TILE_SIZE;

public class Game {

    private GraphicsContext graphicsContext;
    private int[][] grid;
    private int width, height;
    private List<Figure> figures = new ArrayList<>();
    private Figure selected;
    private Socket socket;
    public Game(int width, int height, GraphicsContext g){
        this.graphicsContext = g;
        this.width = width;
        this.height = height;
        grid = new int[width][height];
        try {
            socket = new Socket("localhost", 8090);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render() {
        graphicsContext.clearRect(0, 0, width * TILE_SIZE, height * TILE_SIZE);

        figures.forEach(p -> p.draw(graphicsContext));
    }

    private void placePiece(Piece piece) {
        grid[piece.x][piece.y]++;
    }

    private void removePiece(Piece piece) {
        grid[piece.x][piece.y]--;
    }


    private boolean isOffscreen(Piece piece) {
        return piece.x < 0 || piece.x >= width
                || piece.y < 0 || piece.y >= height ;
    }

    private boolean isValidState() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[x][y] > 1) {
                    return false;
                }
            }
        }
        return true;
    }
    public void update(){
        makeMove(p->p.move(Direction.DOWN),p->p.move(Direction.UP), true);
    }
    public void makeMove(Consumer<Figure> onSuccess, Consumer<Figure> onFail, boolean endMove){
        selected.pieces.forEach(this::removePiece);
        onSuccess.accept(selected);
        boolean offscreen = selected.pieces.stream().anyMatch(this::isOffscreen);

        if(!offscreen){
            selected.pieces.forEach(this::placePiece);
        } else {
            onFail.accept(selected);
            selected.pieces.forEach(this::placePiece);
            if(endMove){
                sweep();
            }
            return;
        }
        if (!isValidState()){
            selected.pieces.forEach(this::removePiece);
            onFail.accept(selected);
            selected.pieces.forEach(this::placePiece);
            if(endMove){
                sweep();
            }
        }
    }

    private void sweep() {
        List<Integer> rows = sweepRows();
        rows.forEach(row -> {
            for (int x = 0; x < width; x++) {
                for (Figure tetromino : figures) {
                    tetromino.detach(x, row);
                }

                grid[x][row]--;
            }
        });

        rows.forEach(row -> {
            figures.stream().forEach(tetromino -> {
                tetromino.pieces.stream()
                        .filter(piece -> piece.y < row)
                        .forEach(piece -> {
                            removePiece(piece);
                            piece.y++;
                            placePiece(piece);
                        });
            });
        });

        spawn();
    }

    private List<Integer> sweepRows() {
        List<Integer> rows = new ArrayList<>();
        outer:
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[x][y] != 1) {
                    continue outer;
                }
            }
            rows.add(y);
        }
        return rows;
    }

    public void spawn() {

        if(socket != null){
            try {
                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();
                os.write(1);
                os.flush();
                int typeFigure = is.read();
                Figure figure = new Figure(typeFigure);
                figure.move(width / 2, 0);
                selected = figure;
                figures.add(figure);
                figure.pieces.forEach(this::placePiece);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!isValidState()){
            System.out.println("Game Over");
            System.exit(0);
        }





    }


}
