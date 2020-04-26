package co.electric.snake.strategy.bonostrategy.closeddirectionsprocessors;

import co.electric.snake.framework.model.Direction;
import co.electric.snake.strategy.bonostrategy.directioncontainers.SimpleDirectionContainer;

import java.util.Map;

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
