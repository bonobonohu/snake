package co.electric.snake.strategy.bonostrategy.newdirectionprocessors;

import co.electric.snake.framework.model.Direction;
import co.electric.snake.strategy.bonostrategy.directioncontainers.SimpleDirectionContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

public class ByBlockingDistances extends NewDirectionProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(ByBlockingDistances.class);

    public ByBlockingDistances(DependencyProvider dependencyProvider) {
        super(dependencyProvider);
    }

    @Override
    public Direction getNewDirection() {
        Direction newDirection = null;

        Map<Integer, SimpleDirectionContainer> orderedBlockings = blockingDirections.getOrderedBlockings();

        boolean foundNewDirection = false;
        Iterator<Map.Entry<Integer, SimpleDirectionContainer>> orderedBlockingsEntrySetIterator = orderedBlockings
                .entrySet().iterator();
        while (!foundNewDirection && orderedBlockingsEntrySetIterator.hasNext()) {
            Map.Entry<Integer, SimpleDirectionContainer> blockingsTemp = orderedBlockingsEntrySetIterator.next();

            SimpleDirectionContainer blockingDirectionsTemp = blockingsTemp.getValue();

            int numOfTries = 0;
            Direction finalDirection;
            do {
                finalDirection = processFinalDirection(blockingDirectionsTemp);

                if (finalDirection != null) {
                    newDirection = finalDirection;
                    foundNewDirection = true;
                }

                numOfTries++;
            } while (!foundNewDirection
                    && (filteredDirections.contains(finalDirection) && numOfTries < filteredDirections.size()));
        }

        LOG.info("Weighted element by blocking distances: " + newDirection);

        return newDirection;
    }

}
