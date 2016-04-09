package model.strategy.bono.newdirectionprocessors;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import model.Direction;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public class ByBlockingDistances extends NewDirectionProcessor
{
    public ByBlockingDistances(DependencyProvider dependencyProvider)
    {
        super(dependencyProvider);
    }

    @Override
    public Direction getNewDirection()
    {
        Direction newDirection = null;

        Map<Integer, SimpleDirectionContainer<Direction>> orderedBlockings = new TreeMap<>(
                Collections.reverseOrder());

        orderedBlockings = blockingDirections.getOrderedBlockings();

        boolean foundNewDirection = false;
        Iterator<Map.Entry<Integer, SimpleDirectionContainer<Direction>>> orderedBlockingsEntrySetIterator = orderedBlockings
                .entrySet().iterator();
        while (!foundNewDirection
                && orderedBlockingsEntrySetIterator.hasNext()) {
            Map.Entry<Integer, SimpleDirectionContainer<Direction>> blockingsTemp = orderedBlockingsEntrySetIterator
                    .next();

            SimpleDirectionContainer<Direction> blockingDirectionsTemp = blockingsTemp
                    .getValue();

            int numOfTries = 0;
            Direction finalDirection;
            do {
                finalDirection = processFinalDirection(blockingDirectionsTemp);
                // finalDirection = blockingDirectionsTemp.getRandomElement();

                if (finalDirection != null) {
                    newDirection = finalDirection;
                    foundNewDirection = true;
                }

                numOfTries++;
            } while (!foundNewDirection
                    && (allValidDirections.contains(finalDirection)
                            && numOfTries < allValidDirections.size()));
        }

        System.out.println(
                "Weighted element by blocking distances: " + newDirection);

        return newDirection;
    }
}
