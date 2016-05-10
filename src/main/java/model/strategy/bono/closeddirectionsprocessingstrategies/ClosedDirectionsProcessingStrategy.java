package model.strategy.bono.closeddirectionsprocessingstrategies;

import java.util.Map;

import model.Direction;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public interface ClosedDirectionsProcessingStrategy
{
    SimpleDirectionContainer<Direction> process(
            Map<Direction, Integer> freeCoordinatesCountByDirection);
}
