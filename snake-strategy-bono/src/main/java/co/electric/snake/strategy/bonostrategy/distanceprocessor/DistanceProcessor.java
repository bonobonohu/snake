package co.electric.snake.strategy.bonostrategy.distanceprocessor;

import co.electric.snake.framework.model.Coordinate;
import co.electric.snake.framework.model.Direction;

public abstract class DistanceProcessor {

    public static DistanceProcessor getStrategy(Direction direction) {
        switch (direction) {
            case NORTH:
                return new NorthLookingDistanceProcessor();
            case SOUTH:
                return new SouthLookingDistanceProcessor();
            case EAST:
                return new EastLookingDistanceProcessor();
            case WEST:
                return new WestLookingDistanceProcessor();
            default:
                return new NorthLookingDistanceProcessor();
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
