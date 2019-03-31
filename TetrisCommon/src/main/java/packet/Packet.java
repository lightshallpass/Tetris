package packet;

public class Packet {

    private int figureType;

    public Packet(int figureType, int x, int y) {
        this.figureType = figureType;

    }

    public int getFigureType() {
        return figureType;
    }

    public void setFigureType(int figureType) {
        this.figureType = figureType;
    }


}
