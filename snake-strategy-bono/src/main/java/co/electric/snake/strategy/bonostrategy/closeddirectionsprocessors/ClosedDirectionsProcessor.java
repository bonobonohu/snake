package co.electric.snake.strategy.bonostrategy.closeddirectionsprocessors;

import co.electric.snake.framework.model.Direction;
import co.electric.snake.strategy.bonostrategy.directioncontainers.SimpleDirectionContainer;

import java.util.Map;

public abstract class ClosedDirectionsProcessor {

    public static ClosedDirectionsProcessor getStrategy() {
        return new AllButMaximumsStrategy();
    }

    public abstract SimpleDirectionContainer getClosedDirections(
            Map<Direction, Integer> freeCoordinatesCountByDirection);

}
