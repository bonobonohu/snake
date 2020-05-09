package co.electric.snake.strategy.bonostrategy.newdirectionprocessors;

import co.electric.snake.framework.model.Direction;

public class ByEquivalentBestDirections extends NewDirectionProcessor {

    public ByEquivalentBestDirections(DependencyProvider dependencyProvider) {
        super(dependencyProvider);
    }

    @Override
    public Direction getNewDirection() {
        Direction newDirection = null;

        if (equivalentBestDirections != null && !equivalentBestDirections.isEmpty()) {
            newDirection = processFinalDirection(equivalentBestDirections);

            printer.print("Random element from the equivalent best directions: " + newDirection);
        }

        return newDirection;
    }

}