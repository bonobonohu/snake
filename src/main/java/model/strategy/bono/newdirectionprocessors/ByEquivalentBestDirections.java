package model.strategy.bono.newdirectionprocessors;

import model.Direction;

public class ByEquivalentBestDirections extends NewDirectionProcessor
{
    public ByEquivalentBestDirections(DependencyProvider dependencyProvider)
    {
        super(dependencyProvider);
    }

    @Override
    public Direction getNewDirection()
    {
        Direction newDirection = null;

        if (equivalentBestDirections != null
                && equivalentBestDirections.size() > 0) {
            newDirection = processFinalDirection(equivalentBestDirections);

            System.out.println(
                    "Random element from the equivalent best directions: "
                            + newDirection);
        }

        return newDirection;
    }

}
