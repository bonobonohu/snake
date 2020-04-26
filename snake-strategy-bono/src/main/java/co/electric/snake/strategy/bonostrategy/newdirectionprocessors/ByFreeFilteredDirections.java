package co.electric.snake.strategy.bonostrategy.newdirectionprocessors;

import co.electric.snake.framework.model.Direction;
import co.electric.snake.strategy.bonostrategy.directioncontainers.SimpleDirectionContainer;

public class ByFreeFilteredDirections extends NewDirectionProcessor {

    public ByFreeFilteredDirections(DependencyProvider dependencyProvider) {
        super(dependencyProvider);
    }

    @Override
    public Direction getNewDirection() {
        Direction newDirection = null;

        if (filteredDirections != null && !filteredDirections.isEmpty()) {
            SimpleDirectionContainer freeFilteredDirections = filteredDirections.getAsNewObject();
            freeFilteredDirections.removeAll(blockingDirections.getDirections());

            newDirection = processFinalDirection(freeFilteredDirections);

            printer.print("Random element from the free filtered directions: " + newDirection);
        }

        return newDirection;
    }

}
