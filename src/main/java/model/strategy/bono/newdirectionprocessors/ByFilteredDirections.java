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

        if (!filteredDirections.isEmpty()) {
            newDirection = processFinalDirection(filteredDirections);

            printer.print("Random element from the filtered directions: "
                    + newDirection);
        }

        return newDirection;
    }
}
