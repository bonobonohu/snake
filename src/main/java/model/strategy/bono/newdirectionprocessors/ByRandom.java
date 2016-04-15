package model.strategy.bono.newdirectionprocessors;

import model.Direction;

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

        newDirection = processFinalDirection(allValidDirections);

        return newDirection;
    }

}
