package model.strategy;

import java.util.Map;
import java.util.TreeMap;

import model.Arena;
import model.Coordinate;
import model.Direction;
import model.Snake;

public class DefaultStrategy implements SnakeStrategy
{
    @Override
    public Direction nextMove(Snake snake, Arena arena)
    {
        int minDistance = Integer.MAX_VALUE;
        Direction bestDirection = Direction.WEST;

        Coordinate startCoordinate = snake.getHeadCoordinate();
        Coordinate foodCoordinate = arena.getFood().get(0).getCoordinate();

        Map<Direction, Integer> distances = new TreeMap<>();

        for (Direction direction : Direction.values()) {
            Coordinate nextCoordinate = arena.nextCoordinate(startCoordinate,
                    direction);

            int actualDistance = nextCoordinate.minDistance(foodCoordinate,
                    arena.getMaxCoordinate());

            if (minDistance > actualDistance
                    && !arena.isOccupied(nextCoordinate)) {
                minDistance = actualDistance;
                bestDirection = direction;
            }

            distances.put(direction, actualDistance);
        }

        // System.out.println(snake.getName() + " - actualCoordinate: "
        // + snake.getHeadCoordinate());
        // System.out.println(
        // snake.getName() + " - foodCoordinate: " + foodCoordinate);
        // System.out.println(distances);

        return bestDirection;
    }
}
