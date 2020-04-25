package model;

import model.strategy.SnakeStrategy;

public class ModifiableSnake extends Snake {

    public ModifiableSnake(ModifiableArena arena, SnakeStrategy strategy, String name) {
        super(arena, strategy, name);
    }

    public void move() {
        Coordinate nextCoordinate = decideNextCoordinate();
        moveTo(nextCoordinate);
    }

}
