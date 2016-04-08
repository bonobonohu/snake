package model.strategy.bono.newdirectionprocessors;

import model.Direction;
import model.strategy.bono.directionhandlers.DirectionContainer;

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
            DirectionContainer<Direction> freeEquivalentBestDirections = equivalentBestDirections
                    .getAsNewObject();
            freeEquivalentBestDirections
                    .removeAll(blockingDirectionsDataHandler.getDirections());

            newDirection = processFinalDirection(freeEquivalentBestDirections);

            System.out.println(
                    "Random element from the free equivalent best directions: "
                            + newDirection);
        }

        return newDirection;
    }

}
