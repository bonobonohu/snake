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
        putDistance(direction, distance);
        putCoordinate(direction, coordinate);
        putDistanceToCoordinate(coordinate, distance);
        putDirectionToCoordinate(coordinate, direction);
    }

    private void putDistance(Direction direction, int distance)
    {
        distanceToDirection.put(direction, distance);
    }

    private void putCoordinate(Direction direction, Coordinate coordinate)
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

    public int getDistance(Direction direction)
    {
        return distanceToDirection.get(direction);
    }

    public Coordinate getCoordinate(Direction direction)
    {
        return coordinateToDirection.get(direction);
    }

    public Set<Direction> getDirections()
    {
        return distanceToDirection.keySet();
    }

    public boolean hasDistance(Direction direction)
    {
        return distanceToDirection.containsKey(direction);
    }

    public boolean hasCoordinate(Direction direction)
    {
        return coordinateToDirection.containsKey(direction);
    }

    public int size()
    {
        return distanceToDirection.size();
    }

    public Set<Map.Entry<Direction, Integer>> entrySet()
    {
        return distanceToDirection.entrySet();
    }
}
