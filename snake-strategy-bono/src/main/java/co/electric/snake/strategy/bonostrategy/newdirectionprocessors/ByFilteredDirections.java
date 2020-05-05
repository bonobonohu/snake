package co.electric.snake.strategy.bonostrategy.newdirectionprocessors;

import co.electric.snake.framework.model.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByFilteredDirections extends NewDirectionProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(ByFilteredDirections.class);

    public ByFilteredDirections(DependencyProvider dependencyProvider) {
        super(dependencyProvider);
    }

    @Override
    public Direction getNewDirection() {
        Direction newDirection = null;

        if (filteredDirections != null && !filteredDirections.isEmpty()) {
            newDirection = processFinalDirection(filteredDirections);

            LOG.info("Random element from the filtered directions: " + newDirection);
        }

        return newDirection;
    }

}
