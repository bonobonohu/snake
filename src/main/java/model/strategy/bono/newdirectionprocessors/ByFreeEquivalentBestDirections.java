package model.strategy.bono.newdirectionprocessors;

import model.Direction;
import model.strategy.bono.directionhandlers.DirectionContainer;

public class ByFreeEquivalentBestDirections extends NewDirectionProcessor
{
    @Override
    public Direction getNewDirection()
    {
        Direction newDirection = null;

        DirectionContainer<Direction> freeEquivalentBestDirections = equivalentBestDirections
                .getAsNewObject();
        freeEquivalentBestDirections
                .removeAll(blockingDirectionsData.getDirections());

        if (freeEquivalentBestDirections.size() > 0) {
            newDirection = freeEquivalentBestDirections.getRandomElement();

            System.out.println(
                    "Random element from the free equivalent best directions: "
                            + newDirection);
        }

        return newDirection;
    }

}
