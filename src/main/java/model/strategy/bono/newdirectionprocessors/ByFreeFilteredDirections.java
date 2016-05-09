package model.strategy.bono.newdirectionprocessors;

import model.Direction;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public class ByFreeFilteredDirections extends NewDirectionProcessor
{
    public ByFreeFilteredDirections(DependencyProvider dependencyProvider)
    {
        super(dependencyProvider);
    }

    @Override
    public Direction getNewDirection()
    {
        Direction newDirection = null;

        if (filteredDirections != null && filteredDirections.size() > 0) {
            SimpleDirectionContainer<Direction> freeFilteredDirections = filteredDirections
                    .getAsNewObject();
            freeFilteredDirections
                    .removeAll(blockingDirections.getDirections());

            newDirection = processFinalDirection(freeFilteredDirections);

            System.out.println(
                    "Random element from the free filtered directions: "
                            + newDirection);
        }

        return newDirection;
    }
}