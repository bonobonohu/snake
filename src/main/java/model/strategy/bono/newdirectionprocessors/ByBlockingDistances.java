package model.strategy.bono.newdirectionprocessors;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import model.Direction;
import model.strategy.bono.directionhandlers.DirectionContainer;

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

        Map<Integer, DirectionContainer<Direction>> orderedBlockings = new TreeMap<>(
                Collections.reverseOrder());

        for (Map.Entry<Direction, Integer> entry : blockingDirectionsDataHandler
                .getDistanceToDirectionsEntrySet()) {
            if (orderedBlockings.containsKey(entry.getValue())) {
                DirectionContainer<Direction> directionsTemp = orderedBlockings
                        .get(entry.getValue());
                directionsTemp.add(entry.getKey());

                orderedBlockings.put(entry.getValue(), directionsTemp);
            } else {
                DirectionContainer<Direction> directionsTemp = new DirectionContainer<>();
                directionsTemp.add(entry.getKey());

                orderedBlockings.put(entry.getValue(), directionsTemp);
            }
        }

        boolean foundNewDirection = false;
        Iterator<Map.Entry<Integer, DirectionContainer<Direction>>> orderedBlockingsEntrySetIterator = orderedBlockings
                .entrySet().iterator();
        while (!foundNewDirection
                && orderedBlockingsEntrySetIterator.hasNext()) {
            Map.Entry<Integer, DirectionContainer<Direction>> blockingsTemp = orderedBlockingsEntrySetIterator
                    .next();

            DirectionContainer<Direction> blockingDirectionsTemp = blockingsTemp
                    .getValue();

            int numOfTries = 0;
            Direction randomDirection;
            do {
                randomDirection = blockingDirectionsTemp.getRandomElement();

                if (randomDirection != null) {
                    newDirection = randomDirection;
                    foundNewDirection = true;
                }

                numOfTries++;
            } while (!foundNewDirection
                    || (allValidDirections.contains(randomDirection)
                            && numOfTries < allValidDirections.size()));
        }

        System.out.println(
                "Weighted element by blocking distances: " + newDirection);

        return newDirection;
    }
}
