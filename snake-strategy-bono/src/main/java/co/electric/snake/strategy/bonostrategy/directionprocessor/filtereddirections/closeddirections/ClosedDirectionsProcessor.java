package co.electric.snake.strategy.bonostrategy.directionprocessor.filtereddirections.closeddirections;

import co.electric.snake.framework.model.Arena;
import co.electric.snake.framework.model.Coordinate;
import co.electric.snake.framework.model.Direction;
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class ClosedDirectionsProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(ClosedDirectionsProcessor.class);

    private final Set<Coordinate> freeCoordinatesTemp = new HashSet<>();

    public SimpleDirectionContainer getDirections(Arena arena, Coordinate headCoordinate) {
        SimpleDirectionContainer closedDirections = new SimpleDirectionContainer();

        Map<Direction, Integer> freeCoordinatesCountByDirection = getFreeCoordinatesCountByDirection(arena, headCoordinate);

        if (allAreTheSame(freeCoordinatesCountByDirection)) {
            freeCoordinatesCountByDirection.clear();
        }

        closedDirections.addAll(getDirections(freeCoordinatesCountByDirection));

        LOG.info("Closed Directions: " + closedDirections);

        return closedDirections;
    }

    private Map<Direction, Integer> getFreeCoordinatesCountByDirection(Arena arena, Coordinate headCoordinate) {
        Map<Direction, Integer> freeCoordinatesCountByDirection = new HashMap<>();

        for (Direction direction : Direction.values()) {
            Coordinate nextCoordinate = arena.nextCoordinate(headCoordinate, direction);

            if (!arena.isOccupied(nextCoordinate)) {
                Integer freeCoordinatesCountForADirection = getFreeCoordinatesCountForADirection(arena, nextCoordinate);

                freeCoordinatesCountByDirection.put(direction, freeCoordinatesCountForADirection);
            }
        }

        LOG.info("Free Coordinates Count By Direction " + freeCoordinatesCountByDirection);

        return freeCoordinatesCountByDirection;
    }

    private Integer getFreeCoordinatesCountForADirection(Arena arena, Coordinate headCoordinate) {
        freeCoordinatesTemp.clear();

        for (Direction direction : Direction.values()) {
            Coordinate nextCoordinate = arena.nextCoordinate(headCoordinate, direction);

            if (!arena.isOccupied(nextCoordinate)) {
                processFreeCoordinatesTemp(arena, nextCoordinate);
            }
        }

        return freeCoordinatesTemp.size();
    }

    private void processFreeCoordinatesTemp(Arena arena, Coordinate freeCoordinate) {
        if (freeCoordinatesTemp.contains(freeCoordinate)) {
            return;
        } else {
            freeCoordinatesTemp.add(freeCoordinate);
        }

        for (Direction direction : Direction.values()) {
            Coordinate nextCoordinate = arena.nextCoordinate(freeCoordinate, direction);

            if (!arena.isOccupied(nextCoordinate)) {
                processFreeCoordinatesTemp(arena, nextCoordinate);
            }
        }
    }

    private boolean allAreTheSame(Map<Direction, Integer> freeCoordinatesCountByDirection) {
        boolean allTheSame = true;

        Integer theCount = Integer.MIN_VALUE;
        for (Direction direction : freeCoordinatesCountByDirection.keySet()) {
            if (theCount == Integer.MIN_VALUE) {
                theCount = freeCoordinatesCountByDirection.get(direction);
            } else if (!theCount.equals(freeCoordinatesCountByDirection.get(direction))) {
                allTheSame = false;
                break;
            }
        }

        return allTheSame;
    }

    abstract SimpleDirectionContainer getDirections(Map<Direction, Integer> freeCoordinatesCountByDirection);

}
