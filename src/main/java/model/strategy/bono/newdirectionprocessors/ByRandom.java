package model.strategy.bono.newdirectionprocessors;

import model.Direction;

public class ByRandom extends NewDirectionProcessor
{
    public ByRandom(DependencyProvider dependencyProvider)
    {
        super(dependencyProvider);
    }

    @Override
    public Direction getNewDirection()
    {
        Direction newDirection = null;

        newDirection = processFinalDirection(filteredDirections);

        return newDirection;
    }

}