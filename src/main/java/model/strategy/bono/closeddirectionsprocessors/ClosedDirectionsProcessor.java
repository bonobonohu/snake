package model.strategy.bono.closeddirectionsprocessors;

import model.Direction;
import model.strategy.bono.directioncontainers.SimpleDirectionContainer;

import java.util.Map;

public abstract class ClosedDirectionsProcessor {

    public static ClosedDirectionsProcessor getStrategy() {
        return new AllButMaximumsStrategy();
    }

    public abstract SimpleDirectionContainer getClosedDirections(
            Map<Direction, Integer> freeCoordinatesCountByDirection);

}
