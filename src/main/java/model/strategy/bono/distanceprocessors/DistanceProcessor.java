package model.strategy.bono.distanceprocessors;

import model.Coordinate;
import model.Direction;

public abstract class DistanceProcessor
{
    public abstract int getDistance(Coordinate actualCoordinate,
            Coordinate blockingCoordinate, Coordinate maxCoordinate);

    public static DistanceProcessor getDistanceProcessor(Direction direction)
    {
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

    protected int getDistanceDirectly(int biggerCoordinateFactor,
            int smallerCoordinateFactor)
    {
        return biggerCoordinateFactor - smallerCoordinateFactor;
    }

    protected int getDistanceClipped(int directCoordinateFactor,
            int clipperCoordinateFactor, int maxCoordinateFactor)
    {
        return directCoordinateFactor
                + (maxCoordinateFactor - clipperCoordinateFactor);
    }
}
