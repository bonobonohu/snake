package model.strategy.bono.closeddirectionsprocessors;

import java.util.Map;

import model.Direction;
import model.strategy.bono.directioncontainers.SimpleDirectionContainer;

public class MinimumsExceptZerosAndZerosStrategy extends ClosedDirectionsProcessor {
    @Override
    public SimpleDirectionContainer getClosedDirections(Map<Direction, Integer> freeCoordinatesCountByDirection) {
        SimpleDirectionContainer minimumExceptZeroAndZeroDirections = new SimpleDirectionContainer();

        SimpleDirectionContainer zeroDirections = getZeroDirections(freeCoordinatesCountByDirection);

        SimpleDirectionContainer minimumExceptZeroDirections = getMinimumExceptZeroDirections(
                freeCoordinatesCountByDirection);

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
