package model.strategy.bono.newdirectionprocessors;

import model.Direction;
import model.strategy.bono.directionhandlers.DirectionContainer;

public class ByFreeEquivalentBestDirections extends NewDirectionProcessor
{
    @Override
    public Direction getNewDirection()
    {
        Direction newDirection = null;

        if (equivalentBestDirections != null
                && equivalentBestDirections.size() > 0) {
            DirectionContainer<Direction> freeEquivalentBestDirections = equivalentBestDirections
                    .getAsNewObject();
            freeEquivalentBestDirections
                    .removeAll(blockingDirectionsData.getDirections());

            newDirection = freeEquivalentBestDirections.getRandomElement();

            System.out.println(
                    "Random element from the free equivalent best directions: "
                            + newDirection);
        }

        return newDirection;
    }

}
