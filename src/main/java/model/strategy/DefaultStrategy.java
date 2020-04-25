package model.strategy;

import model.Arena;
import model.Coordinate;
import model.Direction;
import model.Snake;

public class DefaultStrategy implements SnakeStrategy {

    @Override
    public Direction nextMove(Snake snake, Arena arena) {
        final Coordinate startCoordinate = snake.getHeadCoordinate();
        final Coordinate foodCoordinate = arena.getFood().get(0).getCoordinate();

        int minDistance = Integer.MAX_VALUE;
        Direction bestDirection = Direction.WEST;

        for (Direction direction : Direction.values()) {
            Coordinate nextCoordinate = arena.nextCoordinate(startCoordinate, direction);

            int actualDistance = nextCoordinate.minDistance(foodCoordinate, arena.getMaxCoordinate());

            if (minDistance > actualDistance && !arena.isOccupied(nextCoordinate)) {
                minDistance = actualDistance;
                bestDirection = direction;
            }
        }

        return bestDirection;
    }

}
