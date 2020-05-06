package co.electric.snake.strategy.bonostrategy.closeddirectionsprocessors;

import co.electric.snake.framework.model.Arena;
import co.electric.snake.framework.model.Coordinate;
import co.electric.snake.framework.model.Direction;
import co.electric.snake.strategy.bonostrategy.directioncontainers.SimpleDirectionContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class ClosedDirectionsProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(ClosedDirectionsProcessor.class);

    private final Set<Coordinate> freeCoordinatesTemp = new HashSet<>();

    public SimpleDirectionContainer getClosedDirections(Arena arena, Coordinate actualHeadCoordinate) {
        SimpleDirectionContainer closedDirections = new SimpleDirectionContainer();

        Map<Direction, Integer> freeCoordinatesCountByDirection = getFreeCoordinatesCountByDirection(arena, actualHeadCoordinate);

        if (allAreTheSame(freeCoordinatesCountByDirection)) {
            freeCoordinatesCountByDirection.clear();
        }

        closedDirections.addAll(getClosedDirections(freeCoordinatesCountByDirection));

        LOG.info("Closed Directions: " + closedDirections);

        return closedDirections;
    }

    private Map<Direction, Integer> getFreeCoordinatesCountByDirection(Arena arena, Coordinate actualHeadCoordinate) {
        Map<Direction, Integer> freeCoordinatesCountByDirection = new HashMap<>();

        for (Direction actualDirection : Direction.values()) {
            Coordinate nextCoordinate = arena.nextCoordinate(actualHeadCoordinate, actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                Integer freeCoordinatesCountForADirection = getFreeCoordinatesCountForADirection(arena, nextCoordinate);

                freeCoordinatesCountByDirection.put(actualDirection, freeCoordinatesCountForADirection);
            }
        }

        LOG.info("Free Coordinates Count By Direction " + freeCoordinatesCountByDirection);

        return freeCoordinatesCountByDirection;
    }

    private Integer getFreeCoordinatesCountForADirection(Arena arena, Coordinate headCoordinate) {
        freeCoordinatesTemp.clear();

        for (Direction actualDirection : Direction.values()) {
            Coordinate nextCoordinate = arena.nextCoordinate(headCoordinate, actualDirection);

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

        for (Direction actualDirection : Direction.values()) {
            Coordinate nextCoordinate = arena.nextCoordinate(freeCoordinate, actualDirection);

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

    abstract SimpleDirectionContainer getClosedDirections(Map<Direction, Integer> freeCoordinatesCountByDirection);

}
