package model.strategy.bono.closeddirectionsprocessingstrategies;

import java.util.Map;

import model.Direction;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public abstract class ClosedDirectionsProcessor
{
    public static ClosedDirectionsProcessor getStrategy()
    {

        return new MinimumsExceptZerosAndZerosStrategy();
    }

    public abstract SimpleDirectionContainer<Direction> process(
            Map<Direction, Integer> freeCoordinatesCountByDirection);
}
