package co.electric.snake.model;

import co.electric.snake.model.strategy.SnakeStrategy;

public class ModifiableSnake extends Snake {

    public ModifiableSnake(ModifiableArena arena, SnakeStrategy strategy, String name) {
        super(arena, strategy, name);
    }

    public void move() {
        final Coordinate nextCoordinate = decideNextCoordinate();
        moveTo(nextCoordinate);
    }

}