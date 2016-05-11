package model.strategy.bono.newdirectionprocessors;

import model.Direction;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public class ByFreeEquivalentBestDirections extends NewDirectionProcessor
{
    public ByFreeEquivalentBestDirections(DependencyProvider dependencyProvider)
    {
        super(dependencyProvider);
    }

    @Override
    public Direction getNewDirection()
    {
        Direction newDirection = null;

        if (equivalentBestDirections != null
                && equivalentBestDirections.size() > 0) {
            SimpleDirectionContainer freeEquivalentBestDirections = equivalentBestDirections
                    .getAsNewObject();
            freeEquivalentBestDirections
                    .removeAll(blockingDirections.getDirections());

            newDirection = processFinalDirection(freeEquivalentBestDirections);

            printer.print(
                    "Random element from the free equivalent best directions: "
                            + newDirection);
        }

        return newDirection;
    }

}
