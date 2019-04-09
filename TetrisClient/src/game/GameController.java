package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class GameController {

    private Socket socket;

    private int TILE_SIZE = 40;
    private int GRID_WIDTH = 15;
    private  int GRID_HEIGHT = 20;

    private TetrisFigure selected;

    private List<TetrisFigure> figures = new ArrayList<>();

    private GraphicsContext graphicsContext;

    private int[][] grid;

    public void makeMove(Consumer<TetrisFigure> onSuccess, Consumer<TetrisFigure> onFail, boolean endMove){
      selected.getSquares().forEach(this::removeSquare);
      onSuccess.accept(selected);
      boolean offscreen = selected.getSquares().stream().anyMatch(this::isOffscreen);
      if (!offscreen) {
          selected.getSquares().forEach(this::placeSquare);
      } else {
          onFail.accept(selected);
          selected.getSquares().forEach(this::placeSquare);
          if (endMove) {
              sweep();
          }
          return;
        }

        if (!isValidState()) {
            selected.getSquares().forEach(this::removeSquare);

            onFail.accept(selected);

            selected.getSquares().forEach(this::placeSquare);

            if (endMove) {
                sweep();
            }
        }

    }

    private void sweep() {
        List<Integer> rows = sweepRows();
        rows.forEach(row -> {
            for (int x = 0; x < GRID_WIDTH; x++) {
                for (TetrisFigure tetromino : figures) {
                    tetromino.detach(x, row);
                }

                grid[x][row]--;
            }
        });

        rows.forEach(row -> {
            figures.stream().forEach(tetromino -> {
                tetromino.getSquares().stream()
                        .filter(piece -> piece.getY() < row)
                        .forEach(piece -> {
                            removeSquare(piece);
                            piece.setY(piece.getY() + 1);
                            placeSquare(piece);
                        });
            });
        });

        spawn();
    }

    private List<Integer> sweepRows() {
        List<Integer> rows = new ArrayList<>();

        outer:
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                if (grid[x][y] != 1) {
                    continue outer;
                }
            }

            rows.add(y);
        }

        return rows;
    }


    public void spawn() {
        int figuretype = 0;
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(1);
            outputStream.flush();
            figuretype = socket.getInputStream().read();

        } catch (IOException e) {
            e.printStackTrace();
        }
        TetrisFigure figure = new TetrisFigure(Color.BLUE,figuretype);
        figure.move(GRID_WIDTH / 2, 17);

        selected = figure;

        figures.add(figure);
        figure.getSquares().forEach(this::placeSquare);

        if (!isValidState()) {
            System.out.println("Game Over");
            System.exit(0);
        }
    }

    private boolean isValidState() {
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                if (grid[x][y] > 1) {
                    return false;
                }
            }
        }

        return true;
    }

    public void render(){
        graphicsContext.clearRect(0,0,GRID_WIDTH * TILE_SIZE,GRID_HEIGHT * TILE_SIZE);
        figures.forEach(p -> p.draw(graphicsContext,TILE_SIZE,GRID_WIDTH, GRID_HEIGHT));
    }
    public void update() {
        makeMove(p -> p.move(Direction.DOWN), p -> p.move(Direction.UP), true);
    }

    private void placeSquare(Square square){
        grid[square.getX()][square.getY()]++;
    }

    private void removeSquare(Square square){
        grid[square.getX()][square.getY()]--;
    }

    private boolean isOffscreen(Square square){
        return square.getX() < 0 || square.getX() >= GRID_WIDTH || square.getY() < 0 || square.getY() >= GRID_HEIGHT;
    }

    public GameController(Socket socket, GraphicsContext graphicsContext, int GRID_HEIGHT, int GRID_WIDTH) {
        this.socket = socket;
        this.graphicsContext = graphicsContext;
        this.GRID_HEIGHT = GRID_HEIGHT;
        this.GRID_WIDTH = GRID_WIDTH;
        grid = new int[GRID_WIDTH][GRID_HEIGHT];
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getGRID_WIDTH() {
        return GRID_WIDTH;
    }

    public void setGRID_WIDTH(int GRID_WIDTH) {
        this.GRID_WIDTH = GRID_WIDTH;
    }

    public int getGRID_HEIGHT() {
        return GRID_HEIGHT;
    }

    public void setGRID_HEIGHT(int GRID_HEIGHT) {
        this.GRID_HEIGHT = GRID_HEIGHT;
    }
}
