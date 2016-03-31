package model.strategy.bono;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import model.Coordinate;
import model.Direction;

public class DirectionData
{
    private Map<Direction, Integer> distanceToDirection = new HashMap<>();
    private Map<Direction, Coordinate> coordinateToDirection = new HashMap<>();
    private Map<Coordinate, Integer> distanceToCoordinate = new HashMap<>();
    private Map<Coordinate, Direction> directionToCoordinate = new HashMap<>();

    public void putData(Direction direction, int distance,
            Coordinate coordinate)
    {
        if (directionHasDistance(direction)) {
            int storedDistanceToDirection = getDistanceByDirection(direction);
            if (distance < storedDistanceToDirection) {
                if (coordinateHasDirection(coordinate)) {
                    int storedDistanceToCoordinate = getDistanceByCoordinate(
                            coordinate);

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
                int storedDistanceToCoordinate = getDistanceByCoordinate(
                        coordinate);

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

    private void putCoordinateToDirection(Direction direction,
            Coordinate coordinate)
    {
        coordinateToDirection.put(direction, coordinate);
    }

    private void putDistanceToCoordinate(Coordinate coordinate, int distance)
    {
        distanceToCoordinate.put(coordinate, distance);
    }

    private void putDirectionToCoordinate(Coordinate coordinate,
            Direction direction)
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

    public Set<Map.Entry<Direction, Integer>> getDistanceToDirectionsEntrySet()
    {
        return distanceToDirection.entrySet();
    }

    public String toString()
    {
        return "DirectionData [distanceToDirection=" + distanceToDirection
                + ", coordinateToDirection=" + coordinateToDirection + "]";
    }
}
