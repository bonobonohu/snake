package model.strategy.bono.closeddirectionsprocessingstrategies;

import java.util.Map;

import model.Direction;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public class MinimumsStrategy extends ClosedDirectionsProcessor
{
    @Override
    public SimpleDirectionContainer<Direction> getClosedDirections(
            Map<Direction, Integer> freeCoordinatesCountByDirection)
    {
        SimpleDirectionContainer<Direction> minimumDirections = new SimpleDirectionContainer<>();

        Integer minCount = Integer.MAX_VALUE;
        SimpleDirectionContainer<Direction> minDirections = new SimpleDirectionContainer<>();

        for (Direction direction : freeCoordinatesCountByDirection.keySet()) {
            if (freeCoordinatesCountByDirection.get(direction) < minCount) {
                minCount = freeCoordinatesCountByDirection.get(direction);
            }
        }

        for (Direction direction : freeCoordinatesCountByDirection.keySet()) {
            if (freeCoordinatesCountByDirection.get(direction) == minCount) {
                minDirections.add(direction);
            }
        }

        minimumDirections.addAll(minDirections);

        return minimumDirections;
    }
}
