package model.strategy.bono.newdirectionprocessors;

import model.Direction;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public class ByKispalEsABorz extends NewDirectionProcessor
{
    public ByKispalEsABorz(DependencyProvider dependencyProvider,
            boolean testDirectBlocks)
    {
        super(dependencyProvider, testDirectBlocks);
    }

    @Override
    public Direction getNewDirection()
    {
        Direction newDirection = null;

        SimpleDirectionContainer<Direction> kispalDirections = new SimpleDirectionContainer<>();
        kispalDirections.add(Direction.SOUTH);
        kispalDirections.add(Direction.NORTH);
        kispalDirections.add(Direction.EAST);
        kispalDirections.add(Direction.WEST);

        newDirection = processFinalDirection(kispalDirections);

        return newDirection;
    }

}
