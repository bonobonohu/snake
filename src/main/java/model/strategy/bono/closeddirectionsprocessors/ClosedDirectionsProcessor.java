package model.strategy.bono.closeddirectionsprocessors;

import java.util.Map;

import model.Direction;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public abstract class ClosedDirectionsProcessor
{
    public static ClosedDirectionsProcessor getStrategy()
    {
        return new MinimumsExceptZerosAndZerosStrategy();
    }

    public abstract SimpleDirectionContainer getClosedDirections(
            Map<Direction, Integer> freeCoordinatesCountByDirection);
}
