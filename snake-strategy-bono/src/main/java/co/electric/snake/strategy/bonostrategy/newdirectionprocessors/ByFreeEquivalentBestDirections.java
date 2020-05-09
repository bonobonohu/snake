package co.electric.snake.strategy.bonostrategy.newdirectionprocessors;

import co.electric.snake.framework.model.Direction;
import co.electric.snake.strategy.bonostrategy.directioncontainers.SimpleDirectionContainer;

public class ByFreeEquivalentBestDirections extends NewDirectionProcessor {

    public ByFreeEquivalentBestDirections(DependencyProvider dependencyProvider) {
        super(dependencyProvider);
    }

    @Override
    public Direction getNewDirection() {
        Direction newDirection = null;

        if (equivalentBestDirections != null && !equivalentBestDirections.isEmpty()) {
            SimpleDirectionContainer freeEquivalentBestDirections = equivalentBestDirections.getAsNewObject();
            freeEquivalentBestDirections.removeAll(blockingDirections.getDirections());

            newDirection = processFinalDirection(freeEquivalentBestDirections);

            printer.print("Random element from the free equivalent best directions: " + newDirection);
        }

        return newDirection;
    }

}