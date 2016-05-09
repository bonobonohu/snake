package model.strategy.bono.newdirectionprocessors;

import model.Direction;

public class ByFilteredDirections extends NewDirectionProcessor
{
    public ByFilteredDirections(DependencyProvider dependencyProvider)
    {
        super(dependencyProvider);
    }

    @Override
    public Direction getNewDirection()
    {
        Direction newDirection = null;

        if (filteredDirections != null && filteredDirections.size() > 0) {
            newDirection = processFinalDirection(filteredDirections);

            System.out.println("Random element from the filtered directions: "
                    + newDirection);
        }

        return newDirection;
    }
}
