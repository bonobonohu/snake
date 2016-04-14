package model.strategy.bono.newdirectionprocessors;

import model.Direction;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public class ByFreeEquivalentBestDirections extends NewDirectionProcessor
{
    public ByFreeEquivalentBestDirections(DependencyProvider dependencyProvider,
            boolean testDirectBlocks)
    {
        super(dependencyProvider, testDirectBlocks);
    }

    @Override
    public Direction getNewDirection()
    {
        Direction newDirection = null;

        if (equivalentBestDirections != null
                && equivalentBestDirections.size() > 0) {
            SimpleDirectionContainer<Direction> freeEquivalentBestDirections = equivalentBestDirections
                    .getAsNewObject();
            freeEquivalentBestDirections
                    .removeAll(blockingDirections.getDirections());

            newDirection = processFinalDirection(freeEquivalentBestDirections);

            System.out.println(
                    "Random element from the free equivalent best directions: "
                            + newDirection);
        }

        return newDirection;
    }

}
