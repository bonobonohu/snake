package model.strategy.bono.closeddirectionsprocessingstrategies;

import java.util.Map;

import model.Direction;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public class ZerosStrategy implements ClosedDirectionsProcessingStrategy
{
    @Override
    public SimpleDirectionContainer<Direction> process(
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
}