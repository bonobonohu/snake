package model.strategy.bono.directionhandlers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import model.Coordinate;
import model.Direction;

public class BlockingDirectionContainer
{
    private Map<Direction, Integer> distanceToDirection = new HashMap<>();
    private Map<Direction, Coordinate> coordinateToDirection = new HashMap<>();
    private Map<Coordinate, Integer> distanceToCoordinate = new HashMap<>();
    private Map<Coordinate, Direction> directionToCoordinate = new HashMap<>();

    public void putData(Direction direction, Coordinate coordinate, int distance)
    {
        if (directionHasDistance(direction)) {
            int storedDistanceToDirection = getDistanceByDirection(direction);
            if (distance < storedDistanceToDirection) {
                if (coordinateHasDirection(coordinate)) {
                    int storedDistanceToCoordinate = getDistanceByCoordinate(coordinate);

                    if (distance <= storedDistanceToCoordinate) {
                        removeDataByCoordinate(coordinate);

                        putDistanceToDirection(direction, distance);
                        putCoordinateToDirection(direction, coordinate);
                        putDistanceToCoordinate(coordinate, distance);
                        putDirectionToCoordinate(coordinate, direction);
                    }
                } else {
                    putDistanceToDirection(direction, distance);
                    putCoordinateToDirection(direction, coordinate);
                    putDistanceToCoordinate(coordinate, distance);
                    putDirectionToCoordinate(coordinate, direction);
                }
            }
        } else {
            if (coordinateHasDirection(coordinate)) {
                int storedDistanceToCoordinate = getDistanceByCoordinate(coordinate);

                if (distance <= storedDistanceToCoordinate) {
                    removeDataByCoordinate(coordinate);

                    putDistanceToDirection(direction, distance);
                    putCoordinateToDirection(direction, coordinate);
                    putDistanceToCoordinate(coordinate, distance);
                    putDirectionToCoordinate(coordinate, direction);
                }
            } else {
                putDistanceToDirection(direction, distance);
                putCoordinateToDirection(direction, coordinate);
                putDistanceToCoordinate(coordinate, distance);
                putDirectionToCoordinate(coordinate, direction);
            }
        }
    }

    private void removeDataByCoordinate(Coordinate coordinate)
    {
        Direction directionToCoordinate = getDirectionByCoordinate(coordinate);

        removeFromDistanceToDirection(directionToCoordinate);
        removeFromCoordinateToDirection(directionToCoordinate);
        removeFromDistanceToCoordinate(coordinate);
        removeFromDirectionToCoordinate(coordinate);

    }

    private void removeFromDistanceToDirection(Direction direction)
    {
        distanceToDirection.remove(direction);
    }

    private void removeFromCoordinateToDirection(Direction direction)
    {
        coordinateToDirection.remove(direction);
    }

    private void removeFromDistanceToCoordinate(Coordinate coordinate)
    {
        distanceToCoordinate.remove(coordinate);
    }

    private void removeFromDirectionToCoordinate(Coordinate coordinate)
    {
        directionToCoordinate.remove(coordinate);
    }

    private void putDistanceToDirection(Direction direction, int distance)
    {
        distanceToDirection.put(direction, distance);
    }

    private void putCoordinateToDirection(Direction direction, Coordinate coordinate)
    {
        coordinateToDirection.put(direction, coordinate);
    }

    private void putDistanceToCoordinate(Coordinate coordinate, int distance)
    {
        distanceToCoordinate.put(coordinate, distance);
    }

    private void putDirectionToCoordinate(Coordinate coordinate, Direction direction)
    {
        directionToCoordinate.put(coordinate, direction);
    }

    private int getDistanceByDirection(Direction direction)
    {
        return distanceToDirection.get(direction);
    }

    private int getDistanceByCoordinate(Coordinate coordinate)
    {
        return distanceToCoordinate.get(coordinate);
    }

    private Direction getDirectionByCoordinate(Coordinate coordinate)
    {
        return directionToCoordinate.get(coordinate);
    }

    public Set<Direction> getDirections()
    {
        return distanceToDirection.keySet();
    }

    private boolean directionHasDistance(Direction direction)
    {
        return distanceToDirection.containsKey(direction);
    }

    private boolean coordinateHasDirection(Coordinate coordinate)
    {
        return coordinateToDirection.containsValue(coordinate);
    }

    public int size()
    {
        return distanceToDirection.size();
    }

    public Map<Integer, SimpleDirectionContainer> getOrderedBlockings()
    {
        Map<Integer, SimpleDirectionContainer> orderedBlockings = new TreeMap<>(Collections.reverseOrder());

        for (Map.Entry<Direction, Integer> entry : distanceToDirection.entrySet()) {
            if (orderedBlockings.containsKey(entry.getValue())) {
                SimpleDirectionContainer directionsTemp = orderedBlockings.get(entry.getValue());
                directionsTemp.add(entry.getKey());

                orderedBlockings.put(entry.getValue(), directionsTemp);
            } else {
                SimpleDirectionContainer directionsTemp = new SimpleDirectionContainer();
                directionsTemp.add(entry.getKey());

                orderedBlockings.put(entry.getValue(), directionsTemp);
            }
        }

        return orderedBlockings;
    }

    public String toString()
    {
        return "DirectionData [distanceToDirection=" + distanceToDirection + ", coordinateToDirection="
                + coordinateToDirection + "]";
    }
}
