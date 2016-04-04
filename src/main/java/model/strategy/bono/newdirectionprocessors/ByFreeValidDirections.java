package model.strategy.bono.newdirectionprocessors;

import model.Direction;
import model.strategy.bono.directionhandlers.DirectionContainer;

public class ByFreeValidDirections extends NewDirectionProcessor
{
    @Override
    public Direction getNewDirection()
    {
        Direction newDirection = null;

        DirectionContainer<Direction> freeValidDirections = allValidDirections
                .getAsNewObject();
        freeValidDirections.removeAll(blockingDirectionsData.getDirections());

        if (freeValidDirections.size() > 0) {
            newDirection = freeValidDirections.getRandomElement();

            System.out.println("Random element from the free valid directions: "
                    + newDirection);
        }

        return newDirection;
    }
}
