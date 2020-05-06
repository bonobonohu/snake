package co.electric.snake.strategy.bonostrategy.closeddirectionsprocessors;

import co.electric.snake.framework.model.Direction;
import co.electric.snake.strategy.bonostrategy.directioncontainers.SimpleDirectionContainer;

import java.util.Map;

public class MinimumsExceptZerosAndZerosClosedDirectionsProcessor extends ClosedDirectionsProcessor {

    @Override
    SimpleDirectionContainer getClosedDirections(Map<Direction, Integer> freeCoordinatesCountByDirection) {
        SimpleDirectionContainer minimumExceptZeroAndZeroDirections = new SimpleDirectionContainer();

        SimpleDirectionContainer zeroDirections = getZeroDirections(freeCoordinatesCountByDirection);

        SimpleDirectionContainer minimumExceptZeroDirections = getMinimumExceptZeroDirections(freeCoordinatesCountByDirection);

        minimumExceptZeroAndZeroDirections.addAll(zeroDirections);

        if (freeCoordinatesCountByDirection.size() - zeroDirections.size() > minimumExceptZeroDirections.size()) {
            minimumExceptZeroAndZeroDirections.addAll(minimumExceptZeroDirections);
        }

        return minimumExceptZeroAndZeroDirections;
    }

    private SimpleDirectionContainer getZeroDirections(Map<Direction, Integer> freeCoordinatesCountByDirection) {
        SimpleDirectionContainer zeroDirections = new SimpleDirectionContainer();

        for (Direction direction : freeCoordinatesCountByDirection.keySet()) {
            if (freeCoordinatesCountByDirection.get(direction) == 0) {
                zeroDirections.add(direction);
            }
        }

        return zeroDirections;
    }

    private SimpleDirectionContainer getMinimumExceptZeroDirections(
            Map<Direction, Integer> freeCoordinatesCountByDirection) {
        SimpleDirectionContainer minimumExceptZeroDirections = new SimpleDirectionContainer();

        Integer minCount = Integer.MAX_VALUE;

        for (Direction direction : freeCoordinatesCountByDirection.keySet()) {
            if (freeCoordinatesCountByDirection.get(direction) != 0
                    && freeCoordinatesCountByDirection.get(direction) < minCount) {
                minCount = freeCoordinatesCountByDirection.get(direction);
            }
        }

        for (Direction direction : freeCoordinatesCountByDirection.keySet()) {
            if (freeCoordinatesCountByDirection.get(direction) == minCount) {
                minimumExceptZeroDirections.add(direction);
            }
        }

        return minimumExceptZeroDirections;
    }

}
