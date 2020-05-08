package co.electric.snake.strategy.bonostrategy.distanceprocessor

import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import java.util.*

class EastLookingDistanceProcessor : DistanceProcessor {

    override fun getDistance(direction: Direction, actualCoordinate: Coordinate, blockingCoordinate: Coordinate, maxCoordinate: Coordinate): Optional<Int> {
        var distance: Optional<Int> = Optional.empty()
        if (direction == Direction.EAST) {
            val calculatedDistance: Int
            if (actualCoordinate.x <= blockingCoordinate.x) {
                calculatedDistance = getDistanceDirectly(blockingCoordinate.x, actualCoordinate.x)
            } else {
                calculatedDistance = getDistanceClipped(blockingCoordinate.x, actualCoordinate.x, maxCoordinate.x)
            }
            distance = Optional.of(calculatedDistance)
        }
        return distance
    }

}
