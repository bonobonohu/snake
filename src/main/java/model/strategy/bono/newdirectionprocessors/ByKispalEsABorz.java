package model.strategy.bono.newdirectionprocessors;

import model.Direction;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public class ByKispalEsABorz extends NewDirectionProcessor
{
    public ByKispalEsABorz(DependencyProvider dependencyProvider)
    {
        super(dependencyProvider);
    }

    @Override
    public Direction getNewDirection()
    {
        Direction newDirection = null;

        SimpleDirectionContainer<Direction> kispalDirections = new SimpleDirectionContainer<>();
        kispalDirections.add(Direction.NORTH);

        newDirection = processFinalDirection(kispalDirections);

        return newDirection;
    }

}
