package co.electric.snake.strategy.bonostrategy.directionprocessor.filtereddirections.closeddirections;

import co.electric.snake.framework.model.Direction;
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer;

import java.util.Map;

public class AllButMaximumsClosedDirectionsProcessor extends ClosedDirectionsProcessor {

    @Override
    SimpleDirectionContainer getDirections(Map<Direction, Integer> freeCoordinatesCountByDirection) {
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
