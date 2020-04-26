package co.electric.snake.strategy.bonostrategy.distanceprocessors;

import co.electric.snake.framework.model.Coordinate;
import co.electric.snake.framework.model.Direction;

public abstract class DistanceProcessor {

    public static DistanceProcessor getStrategy(Direction direction) {
        switch (direction) {
            case NORTH:
                return new NorthLookingDistanceStrategy();
            case SOUTH:
                return new SouthLookingDistanceStrategy();
            case EAST:
                return new EastLookingDistanceStrategy();
            case WEST:
                return new WestLookingDistanceStrategy();
            default:
                return new NorthLookingDistanceStrategy();
        }
    }

    public abstract int getDistance(Coordinate actualCoordinate,
                                    Coordinate blockingCoordinate, Coordinate maxCoordinate);

    protected int getDistanceDirectly(int biggerCoordinateFactor,
                                      int smallerCoordinateFactor) {
        return biggerCoordinateFactor - smallerCoordinateFactor;
    }

    protected int getDistanceClipped(int directCoordinateFactor,
                                     int clipperCoordinateFactor, int maxCoordinateFactor) {
        return directCoordinateFactor
                + (maxCoordinateFactor - clipperCoordinateFactor);
    }

}
