package model.strategy.bono.closeddirectionsprocessors;

import java.util.Map;

import model.Direction;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public class MinimumsExceptZerosAndZerosStrategy
        extends ClosedDirectionsProcessor
{
    @Override
    public SimpleDirectionContainer<Direction> getClosedDirections(
            Map<Direction, Integer> freeCoordinatesCountByDirection)
    {
        SimpleDirectionContainer<Direction> minimumExceptZeroAndZeroDirections = new SimpleDirectionContainer<>();

        SimpleDirectionContainer<Direction> zeroDirections = getZeroDirections(
                freeCoordinatesCountByDirection);

        SimpleDirectionContainer<Direction> minimumExceptZeroDirections = getMinimumExceptZeroDirections(
                freeCoordinatesCountByDirection);

        minimumExceptZeroAndZeroDirections.addAll(zeroDirections);

        if (freeCoordinatesCountByDirection.size()
                - zeroDirections.size() > minimumExceptZeroDirections.size()) {
            minimumExceptZeroAndZeroDirections
                    .addAll(minimumExceptZeroDirections);
        }

        return minimumExceptZeroAndZeroDirections;
    }

    private SimpleDirectionContainer<Direction> getZeroDirections(
            Map<Direction, Integer> freeCoordinatesCountByDirection)
    {
        SimpleDirectionContainer<Direction> zeroDirections = new SimpleDirectionContainer<>();

        for (Direction direction : freeCoordinatesCountByDirection.keySet()) {
            if (freeCoordinatesCountByDirection.get(direction) == 0) {
                zeroDirections.add(direction);
            }
        }

        return zeroDirections;
    }

    private SimpleDirectionContainer<Direction> getMinimumExceptZeroDirections(
            Map<Direction, Integer> freeCoordinatesCountByDirection)
    {
        SimpleDirectionContainer<Direction> minimumExceptZeroDirections = new SimpleDirectionContainer<>();

        Integer minCount = Integer.MAX_VALUE;

        for (Direction direction : freeCoordinatesCountByDirection.keySet()) {
            if (freeCoordinatesCountByDirection.get(direction) != 0
                    && freeCoordinatesCountByDirection
                            .get(direction) < minCount) {
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
