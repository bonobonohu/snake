package co.electric.snake.strategy.bonostrategy.distanceprocessor

import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import java.util.*

class NorthLookingDistanceProcessor : DistanceProcessor {

    override fun getDistance(direction: Direction, actualCoordinate: Coordinate, blockingCoordinate: Coordinate, maxCoordinate: Coordinate): Optional<Int> {
        var distance: Optional<Int> = Optional.empty()
        if (direction == Direction.NORTH) {
            val calculatedDistance: Int
            if (actualCoordinate.y <= blockingCoordinate.y) {
                calculatedDistance = getDistanceDirectly(blockingCoordinate.y, actualCoordinate.y)
            } else {
                calculatedDistance = getDistanceClipped(blockingCoordinate.y, actualCoordinate.y, maxCoordinate.y)
            }
            distance = Optional.of(calculatedDistance)
        }
        return distance
    }

}
