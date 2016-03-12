package model.strategy;

import java.util.List;

import model.Arena;
import model.Coordinate;
import model.Direction;
import model.Food;
import model.Snake;

public class BonoStrategy implements SnakeStrategy
{
    @Override
    public Direction nextMove(Snake snake, Arena arena)
    {
        Direction newDirection = null;

        List<Food> foods = arena.getFood();
        Food food = foods.get(0);
        Coordinate foodCoordinate = food.getCoordinate();

        List<Coordinate> myCoordinates = snake.getBodyItems();
        Coordinate myCoordinate = myCoordinates.get(0);

        Coordinate startCoordinate = snake.getHeadCoordinate();

        if (myCoordinate.getX() < foodCoordinate.getX()) {
            newDirection = Direction.EAST;
        }
        if (myCoordinate.getX() > foodCoordinate.getX()) {
            newDirection = Direction.WEST;
        }
        if (myCoordinate.getX() == foodCoordinate.getX()) {
            if (myCoordinate.getY() < foodCoordinate.getY()) {
                newDirection = Direction.NORTH;
            }
            if (myCoordinate.getY() > foodCoordinate.getY()) {
                newDirection = Direction.SOUTH;
            }
        }

        // @todo do the job!
        System.out.println("Think again how many tries needed!");
        System.exit(0);
        int numOfTries = 0;
        Coordinate nextCoordinate;
        do {
            if (newDirection == Direction.EAST) {
                newDirection = Direction.SOUTH;
            } else if (newDirection == Direction.WEST) {
                newDirection = Direction.NORTH;
            } else if (newDirection == Direction.SOUTH) {
                newDirection = Direction.WEST;
            } else if (newDirection == Direction.NORTH) {
                newDirection = Direction.EAST;
            }

            nextCoordinate = arena.nextCoordinate(startCoordinate,
                    newDirection);
            numOfTries++;
        } while (arena.isOccupied(nextCoordinate) && numOfTries < 3);

        return newDirection;
    }
}
