package model.strategy.bono.closeddirectionsprocessors;

import java.util.Arrays;
import java.util.Map;

import model.Direction;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public class AllButMaximumsStrategy extends ClosedDirectionsProcessor
{
    @Override
    public SimpleDirectionContainer<Direction> getClosedDirections(
            Map<Direction, Integer> freeCoordinatesCountByDirection)
    {
        SimpleDirectionContainer<Direction> allButMaximumDirections = new SimpleDirectionContainer<>();

        Integer maxCount = Integer.MIN_VALUE;
        SimpleDirectionContainer<Direction> maxDirections = new SimpleDirectionContainer<>();

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

        allButMaximumDirections.addAll(Arrays.asList(Direction.values()));
        allButMaximumDirections.removeAll(maxDirections);

        return allButMaximumDirections;
    }
}
