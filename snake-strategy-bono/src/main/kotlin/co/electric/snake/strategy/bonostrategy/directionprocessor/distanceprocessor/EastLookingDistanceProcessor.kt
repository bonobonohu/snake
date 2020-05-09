package co.electric.snake.strategy.bonostrategy.directionprocessor.distanceprocessor

import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import java.util.*

class EastLookingDistanceProcessor : DistanceProcessor {

    override fun getDistance(direction: Direction, headCoordinate: Coordinate, blockingCoordinate: Coordinate, maxCoordinate: Coordinate): Optional<Int> {
        var distance: Optional<Int> = Optional.empty()
        if (direction == Direction.EAST) {
            val calculatedDistance: Int
            if (headCoordinate.x <= blockingCoordinate.x) {
                calculatedDistance = getDistanceDirectly(blockingCoordinate.x, headCoordinate.x)
            } else {
                calculatedDistance = getDistanceClipped(blockingCoordinate.x, headCoordinate.x, maxCoordinate.x)
            }
            distance = Optional.of(calculatedDistance)
        }
        return distance
    }

}
