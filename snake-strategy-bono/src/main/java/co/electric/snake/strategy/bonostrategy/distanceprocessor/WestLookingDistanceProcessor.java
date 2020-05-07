package co.electric.snake.strategy.bonostrategy.distanceprocessor;

import co.electric.snake.framework.model.Coordinate;

public class WestLookingDistanceProcessor extends DistanceProcessor {

    public int getDistance(Coordinate actualCoordinate,
                           Coordinate blockingCoordinate, Coordinate maxCoordinate) {
        int distance = 0;

        if (actualCoordinate.getX() > blockingCoordinate.getX()) {
            distance = getDistanceDirectly(actualCoordinate.getX(),
                    blockingCoordinate.getX());
        }
        if (actualCoordinate.getX() < blockingCoordinate.getX()) {
            distance = getDistanceClipped(actualCoordinate.getX(),
                    blockingCoordinate.getX(), maxCoordinate.getX());
        }

        return distance;
    }

}
