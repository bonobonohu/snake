package co.electric.snake.strategy.bonostrategy.newdirectionprocessors;

import co.electric.snake.framework.model.Direction;
import co.electric.snake.strategy.bonostrategy.directioncontainers.SimpleDirectionContainer;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class ByBlockingDistances extends NewDirectionProcessor {

    public ByBlockingDistances(DependencyProvider dependencyProvider) {
        super(dependencyProvider);
    }

    @Override
    public Direction getNewDirection() {
        Direction newDirection = null;

        Map<Integer, SimpleDirectionContainer> orderedBlockings = new TreeMap<>(Collections.reverseOrder());

        orderedBlockings = blockingDirections.getOrderedBlockings();

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

        printer.print("Weighted element by blocking distances: " + newDirection);

        return newDirection;
    }

}
