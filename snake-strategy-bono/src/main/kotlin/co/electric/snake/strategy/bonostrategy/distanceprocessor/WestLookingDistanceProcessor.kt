package co.electric.snake.strategy.bonostrategy.distanceprocessor

import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import java.util.*

class WestLookingDistanceProcessor : DistanceProcessor {

    override fun getDistance(direction: Direction, headCoordinate: Coordinate, blockingCoordinate: Coordinate, maxCoordinate: Coordinate): Optional<Int> {
        var distance: Optional<Int> = Optional.empty()
        if (direction == Direction.WEST) {
            val calculatedDistance: Int
            if (headCoordinate.x >= blockingCoordinate.x) {
                calculatedDistance = getDistanceDirectly(headCoordinate.x, blockingCoordinate.x)
            } else {
                calculatedDistance = getDistanceClipped(headCoordinate.x, blockingCoordinate.x, maxCoordinate.x)
            }
            distance = Optional.of(calculatedDistance)
        }
        return distance
    }

}
