package model.strategy.bono.distanceprocessors;

import model.Coordinate;

public class EastLookingDistanceStrategy extends DistanceProcessor
{
    public int getDistance(Coordinate actualCoordinate,
            Coordinate blockingCoordinate, Coordinate maxCoordinate)
    {
        int distance = 0;

        if (actualCoordinate.getX() < blockingCoordinate.getX()) {
            distance = getDistanceDirectly(blockingCoordinate.getX(),
                    actualCoordinate.getX());
        }
        if (actualCoordinate.getX() > blockingCoordinate.getX()) {
            distance = getDistanceClipped(blockingCoordinate.getX(),
                    actualCoordinate.getX(), maxCoordinate.getX());
        }

        return distance;
    }
}
