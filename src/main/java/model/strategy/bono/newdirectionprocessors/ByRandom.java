package model.strategy.bono.newdirectionprocessors;

import model.Direction;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public class ByRandom extends NewDirectionProcessor
{
    public ByRandom(DependencyProvider dependencyProvider,
            boolean testDirectBlocks)
    {
        super(dependencyProvider, testDirectBlocks);
    }

    @Override
    public Direction getNewDirection()
    {
        Direction newDirection = null;

        SimpleDirectionContainer<Direction> directions = new SimpleDirectionContainer<>();
        directions.add(Direction.SOUTH);
        directions.add(Direction.NORTH);
        directions.add(Direction.EAST);
        directions.add(Direction.WEST);

        newDirection = processFinalDirection(directions);

        return newDirection;
    }

}
