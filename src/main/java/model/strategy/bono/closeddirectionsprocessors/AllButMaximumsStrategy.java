package model.strategy.bono.closeddirectionsprocessors;

import java.util.Map;

import model.Direction;
import model.strategy.bono.directioncontainers.SimpleDirectionContainer;

public class AllButMaximumsStrategy extends ClosedDirectionsProcessor {
    @Override
    public SimpleDirectionContainer getClosedDirections(Map<Direction, Integer> freeCoordinatesCountByDirection) {
        SimpleDirectionContainer allButMaximumDirections = new SimpleDirectionContainer();

        Integer maxCount = Integer.MIN_VALUE;
        SimpleDirectionContainer maxDirections = new SimpleDirectionContainer();

        for (Direction direction : freeCoordinatesCountByDirection.keySet()) {
            if (freeCoordinatesCountByDirection.get(direction) > maxCount) {
                maxCount = freeCoordinatesCountByDirection.get(direction);
            }
        }

        for (Direction direction : freeCoordinatesCountByDirection.keySet()) {
            if (freeCoordinatesCountByDirection.get(direction) == maxCount) {
                maxDirections.add(direction);
            }
        }

        allButMaximumDirections.addAll(freeCoordinatesCountByDirection.keySet());
        allButMaximumDirections.removeAll(maxDirections);

        return allButMaximumDirections;
    }
}
