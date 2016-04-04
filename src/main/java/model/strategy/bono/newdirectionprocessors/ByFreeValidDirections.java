package model.strategy.bono.newdirectionprocessors;

import model.Direction;
import model.strategy.bono.directionhandlers.DirectionContainer;

public class ByFreeValidDirections extends NewDirectionProcessor
{
    @Override
    public Direction getNewDirection()
    {
        Direction newDirection = null;

        if (allValidDirections != null && allValidDirections.size() > 0) {
            DirectionContainer<Direction> freeValidDirections = allValidDirections
                    .getAsNewObject();
            freeValidDirections
                    .removeAll(blockingDirectionsData.getDirections());

            newDirection = freeValidDirections.getRandomElement();

            System.out.println("Random element from the free valid directions: "
                    + newDirection);
        }

        return newDirection;
    }
}
