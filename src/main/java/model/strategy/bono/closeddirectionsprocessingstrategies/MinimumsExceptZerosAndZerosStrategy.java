package model.strategy.bono.closeddirectionsprocessingstrategies;

import java.util.Map;

import model.Direction;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public class MinimumsExceptZerosAndZerosStrategy
        implements ClosedDirectionsProcessingStrategy
{
    @Override
    public SimpleDirectionContainer<Direction> process(
            Map<Direction, Integer> freeCoordinatesCountByDirection)
    {
        SimpleDirectionContainer<Direction> minimumExceptZeroAndZeroDirections = new SimpleDirectionContainer<>();

        SimpleDirectionContainer<Direction> zeroDirections = (new ZerosStrategy())
                .process(freeCoordinatesCountByDirection);

        minimumExceptZeroAndZeroDirections.addAll(zeroDirections);

        Integer minCount = Integer.MAX_VALUE;
        SimpleDirectionContainer<Direction> minDirections = new SimpleDirectionContainer<>();

        for (Direction direction : freeCoordinatesCountByDirection.keySet()) {
            if (freeCoordinatesCountByDirection.get(direction) != 0
                    && freeCoordinatesCountByDirection
                            .get(direction) < minCount) {
                minCount = freeCoordinatesCountByDirection.get(direction);
            }
        }

        for (Direction direction : freeCoordinatesCountByDirection.keySet()) {
            if (freeCoordinatesCountByDirection.get(direction) == minCount) {
                minDirections.add(direction);
            }
        }

        minimumExceptZeroAndZeroDirections.addAll(minDirections);

        return minimumExceptZeroAndZeroDirections;
    }
}