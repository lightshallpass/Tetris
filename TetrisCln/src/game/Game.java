package game;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

import static sun.dc.pr.Rasterizer.TILE_SIZE;

public class Game {

    private GraphicsContext graphicsContext;
    private int[][] grid;
    private int width, height;
    private List<Figure> figures = new ArrayList<>();
    private Figure selected;
    public Game(int width, int height, GraphicsContext g){
        this.graphicsContext = g;
        this.width = width;
        this.height = height;
        grid = new int[width][height];
    }

    private void render() {
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
}
