package model.strategy.bono;

import model.Coordinate;

public class NorthLookingDistanceProcessor extends DistanceProcessor
{
    public int getDistance(Coordinate actualCoordinate,
            Coordinate blockingCoordinate, Coordinate maxCoordinate)
    {
        int distance = 0;

        if (actualCoordinate.getY() < blockingCoordinate.getY()) {
            distance = getDistanceDirectly(blockingCoordinate.getY(),
                    actualCoordinate.getY());
        }
        if (actualCoordinate.getY() > blockingCoordinate.getY()) {
            distance = getDistanceClipped(blockingCoordinate.getY(),
                    actualCoordinate.getY(), maxCoordinate.getY());
        }

        return distance;
    }
}
