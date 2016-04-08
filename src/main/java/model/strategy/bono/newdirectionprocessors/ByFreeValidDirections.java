package model.strategy.bono.newdirectionprocessors;

import model.Direction;
import model.strategy.bono.directionhandlers.DirectionContainer;

public class ByFreeValidDirections extends NewDirectionProcessor
{
    public ByFreeValidDirections(DependencyProvider dependencyProvider)
    {
        super(dependencyProvider);
    }

    @Override
    public Direction getNewDirection()
    {
        Direction newDirection = null;

        if (allValidDirections != null && allValidDirections.size() > 0) {
            DirectionContainer<Direction> freeValidDirections = allValidDirections
                    .getAsNewObject();
            freeValidDirections
                    .removeAll(blockingDirections.getDirections());

            newDirection = processFinalDirection(freeValidDirections);

            System.out.println("Random element from the free valid directions: "
                    + newDirection);
        }

        return newDirection;
    }
}
