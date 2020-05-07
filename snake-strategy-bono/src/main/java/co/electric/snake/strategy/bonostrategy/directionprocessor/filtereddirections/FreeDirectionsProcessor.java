package co.electric.snake.strategy.bonostrategy.directionprocessor.filtereddirections;

import co.electric.snake.framework.model.Arena;
import co.electric.snake.framework.model.Coordinate;
import co.electric.snake.framework.model.Direction;
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FreeDirectionsProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(FreeDirectionsProcessor.class);

    public SimpleDirectionContainer getDirections(Arena arena, Coordinate actualHeadCoordinate) {
        SimpleDirectionContainer freeDirections = new SimpleDirectionContainer();

        for (Direction actualDirection : Direction.values()) {
            Coordinate nextCoordinate = arena.nextCoordinate(actualHeadCoordinate, actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                freeDirections.add(actualDirection);
            }
        }

        LOG.info("Free Directions: " + freeDirections);

        return freeDirections;
    }

}
