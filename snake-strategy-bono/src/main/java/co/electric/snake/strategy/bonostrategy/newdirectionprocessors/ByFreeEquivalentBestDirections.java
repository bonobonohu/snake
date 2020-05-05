package co.electric.snake.strategy.bonostrategy.newdirectionprocessors;

import co.electric.snake.framework.model.Direction;
import co.electric.snake.strategy.bonostrategy.directioncontainers.SimpleDirectionContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByFreeEquivalentBestDirections extends NewDirectionProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(ByFreeEquivalentBestDirections.class);

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

            LOG.info("Random element from the free equivalent best directions: " + newDirection);
        }

        return newDirection;
    }

}
