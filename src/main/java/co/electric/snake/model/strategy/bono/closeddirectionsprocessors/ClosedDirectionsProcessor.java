package co.electric.snake.model.strategy.bono.closeddirectionsprocessors;

import co.electric.snake.model.Direction;
import co.electric.snake.model.strategy.bono.directioncontainers.SimpleDirectionContainer;

import java.util.Map;

public abstract class ClosedDirectionsProcessor {

    public static ClosedDirectionsProcessor getStrategy() {
        return new AllButMaximumsStrategy();
    }

    public abstract SimpleDirectionContainer getClosedDirections(
            Map<Direction, Integer> freeCoordinatesCountByDirection);

}
