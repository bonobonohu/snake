package co.electric.snake.strategy.bonostrategy.newdirectionprocessors;

import co.electric.snake.framework.model.Direction;
import co.electric.snake.strategy.bonostrategy.directioncontainers.SimpleDirectionContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByFreeFilteredDirections extends NewDirectionProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(ByFreeFilteredDirections.class);

    public ByFreeFilteredDirections(DependencyProvider dependencyProvider) {
        super(dependencyProvider);
    }

    @Override
    public Direction getNewDirection() {
        Direction newDirection = null;

        if (filteredDirections != null && !filteredDirections.isEmpty()) {
            SimpleDirectionContainer freeFilteredDirections = filteredDirections.getElementsInANewInstance();
            freeFilteredDirections.removeAll(blockingDirections.getDirections());

            newDirection = processFinalDirection(freeFilteredDirections);

            LOG.info("Random element from the free filtered directions: " + newDirection);
        }

        return newDirection;
    }

}
