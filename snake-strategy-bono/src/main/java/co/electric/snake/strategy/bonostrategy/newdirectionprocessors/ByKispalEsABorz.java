package co.electric.snake.strategy.bonostrategy.newdirectionprocessors;

import co.electric.snake.framework.model.Direction;
import co.electric.snake.strategy.bonostrategy.directioncontainers.SimpleDirectionContainer;

public class ByKispalEsABorz extends NewDirectionProcessor {

    public ByKispalEsABorz(DependencyProvider dependencyProvider) {
        super(dependencyProvider);
    }

    @Override
    public Direction getNewDirection() {
        Direction newDirection = null;

        SimpleDirectionContainer kispalDirections = new SimpleDirectionContainer();
        kispalDirections.add(Direction.NORTH);

        newDirection = processFinalDirection(kispalDirections);

        return newDirection;
    }

}