package model.strategy.bono;

import model.Coordinate;

public class SouthLookingDistanceProcessor extends DistanceProcessor
{
    public int getDistance(Coordinate actualCoordinate,
            Coordinate blockingCoordinate, Coordinate maxCoordinate)
    {
        int distance = 0;

        if (actualCoordinate.getY() > blockingCoordinate.getY()) {
            distance = getDistanceDirectly(actualCoordinate.getY(),
                    blockingCoordinate.getY());
        }
        if (actualCoordinate.getY() < blockingCoordinate.getY()) {
            distance = getDistanceClipped(actualCoordinate.getY(),
                    blockingCoordinate.getY(), maxCoordinate.getY());
        }

        return distance;
    }
}
