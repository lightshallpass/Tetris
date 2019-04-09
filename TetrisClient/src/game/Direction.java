package game;

public enum Direction {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0);





    int x, y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Direction prev(){
        int next = ordinal() - 1;
        if (next == -1){
            next = Direction.values().length - 1;
        }
        return Direction.values()[next];
    }

    public Direction next(){
        int next = ordinal() + 1;
        if (next == Direction.values().length){
            next = 0;
        }
        return Direction.values()[next];
    }


}
