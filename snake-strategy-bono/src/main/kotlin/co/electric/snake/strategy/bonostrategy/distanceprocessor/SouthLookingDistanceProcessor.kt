package co.electric.snake.strategy.bonostrategy.distanceprocessor

import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import java.util.*

class SouthLookingDistanceProcessor : DistanceProcessor {

    override fun getDistance(direction: Direction, headCoordinate: Coordinate, blockingCoordinate: Coordinate, maxCoordinate: Coordinate): Optional<Int> {
        var distance: Optional<Int> = Optional.empty()
        if (direction == Direction.SOUTH) {
            val calculatedDistance: Int
            if (headCoordinate.y >= blockingCoordinate.y) {
                calculatedDistance = getDistanceDirectly(headCoordinate.y, blockingCoordinate.y)
            } else {
                calculatedDistance = getDistanceClipped(headCoordinate.y, blockingCoordinate.y, maxCoordinate.y)
            }
            distance = Optional.of(calculatedDistance)
        }
        return distance
    }

}
