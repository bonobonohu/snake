package co.electric.snake.strategy.bonostrategy.newdirectionprocessor;

import co.electric.snake.framework.model.Direction;
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer;
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

public class ByBlockingDistances implements NewDirectionProcessor {

    private static final int ORDER = 3;

    private static final Logger LOG = LoggerFactory.getLogger(ByBlockingDistances.class);

    @Override
    public Direction process(SimpleDirectionContainer filteredDirections, SimpleDirectionContainer equivalentBestDirections, BlockingDirectionContainer blockingDirections) {
        Direction newDirection = null;
        SimpleDirectionContainer foundNewDirections = new SimpleDirectionContainer();

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
                finalDirection = blockingDirectionsTemp.getRandomElement();

                if (finalDirection != null) {
                    foundNewDirections.add(finalDirection);
                    foundNewDirection = true;
                }

                numOfTries++;
            } while (!foundNewDirection
                    && (filteredDirections.contains(finalDirection) && numOfTries < filteredDirections.size()));
        }

        if (!foundNewDirections.isEmpty()) {
            newDirection = processFinalDirection(foundNewDirections, LOG);
        }

        return newDirection;
    }

    @Override
    public int getOrder() {
        return ORDER;
    }

}
