package co.electric.snake.strategy.bonostrategy;

import co.electric.snake.framework.model.Coordinate;
import co.electric.snake.framework.model.Direction;

import java.util.*;

public class BlockingDirectionContainer {

    private final Map<Direction, Integer> distanceToDirection = new HashMap<>();
    private final Map<Direction, Coordinate> coordinateToDirection = new HashMap<>();
    private final Map<Coordinate, Integer> distanceToCoordinate = new HashMap<>();
    private final Map<Coordinate, Direction> directionToCoordinate = new HashMap<>();

    public void putData(Direction direction, Coordinate coordinate, int distance) {
        if (!directionHasDistance(direction)) {
            doPut(direction, coordinate, distance);
        } else {
            final int storedDistanceToDirection = getDistanceByDirection(direction);
            if (distance < storedDistanceToDirection) {
                doPut(direction, coordinate, distance);
            }
        }
    }

    public Set<Direction> getDirections() {
        return distanceToDirection.keySet();
    }

    public SimpleDirectionContainer getFarthestBlockingDirections() {
        final Map<Integer, SimpleDirectionContainer> directionToDistances = new TreeMap<>(Collections.reverseOrder());
        distanceToDirection.forEach((direction, distance) -> {
            final SimpleDirectionContainer simpleDirections = Optional.ofNullable(directionToDistances.get(distance)).orElse(new SimpleDirectionContainer());
            simpleDirections.add(direction);
            directionToDistances.put(distance, simpleDirections);
        });
        return directionToDistances.values().stream()
                .findFirst()
                .orElse(new SimpleDirectionContainer());
    }

    private void doPut(Direction direction, Coordinate coordinate, int distance) {
        if (!coordinateHasDirection(coordinate)) {
            putDistanceToDirection(direction, distance);
            putCoordinateToDirection(direction, coordinate);
            putDistanceToCoordinate(coordinate, distance);
            putDirectionToCoordinate(coordinate, direction);
        } else {
            final int storedDistanceToCoordinate = getDistanceByCoordinate(coordinate);
            if (distance <= storedDistanceToCoordinate) {
                removeDataByCoordinate(coordinate);
                putDistanceToDirection(direction, distance);
                putCoordinateToDirection(direction, coordinate);
                putDistanceToCoordinate(coordinate, distance);
                putDirectionToCoordinate(coordinate, direction);
            }
        }
    }

    private void removeDataByCoordinate(Coordinate coordinate) {
        final Direction directionToCoordinate = getDirectionByCoordinate(coordinate);
        removeFromDistanceToDirection(directionToCoordinate);
        removeFromCoordinateToDirection(directionToCoordinate);
        removeFromDistanceToCoordinate(coordinate);
        removeFromDirectionToCoordinate(coordinate);
    }

    private void removeFromDistanceToDirection(Direction direction) {
        distanceToDirection.remove(direction);
    }

    private void removeFromCoordinateToDirection(Direction direction) {
        coordinateToDirection.remove(direction);
    }

    private void removeFromDistanceToCoordinate(Coordinate coordinate) {
        distanceToCoordinate.remove(coordinate);
    }

    private void removeFromDirectionToCoordinate(Coordinate coordinate) {
        directionToCoordinate.remove(coordinate);
    }

    private void putDistanceToDirection(Direction direction, int distance) {
        distanceToDirection.put(direction, distance);
    }

    private void putCoordinateToDirection(Direction direction, Coordinate coordinate) {
        coordinateToDirection.put(direction, coordinate);
    }

    private void putDistanceToCoordinate(Coordinate coordinate, int distance) {
        distanceToCoordinate.put(coordinate, distance);
    }

    private void putDirectionToCoordinate(Coordinate coordinate, Direction direction) {
        directionToCoordinate.put(coordinate, direction);
    }

    private int getDistanceByDirection(Direction direction) {
        return distanceToDirection.get(direction);
    }

    private int getDistanceByCoordinate(Coordinate coordinate) {
        return distanceToCoordinate.get(coordinate);
    }

    private Direction getDirectionByCoordinate(Coordinate coordinate) {
        return directionToCoordinate.get(coordinate);
    }

    private boolean directionHasDistance(Direction direction) {
        return distanceToDirection.containsKey(direction);
    }

    private boolean coordinateHasDirection(Coordinate coordinate) {
        return coordinateToDirection.containsValue(coordinate);
    }

    @Override
    public String toString() {
        return "DirectionData [distanceToDirection=" + distanceToDirection + "]";
    }

}
