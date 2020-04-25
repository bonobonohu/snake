package model.strategy.bono.newdirectionprocessors;

import model.Direction;
import model.strategy.bono.directioncontainers.SimpleDirectionContainer;

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
