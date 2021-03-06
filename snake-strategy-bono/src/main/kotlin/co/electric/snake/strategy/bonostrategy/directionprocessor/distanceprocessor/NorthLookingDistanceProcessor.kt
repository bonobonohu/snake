package co.electric.snake.strategy.bonostrategy.directionprocessor.distanceprocessor

import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import java.util.*

class NorthLookingDistanceProcessor : DistanceProcessor {

    override fun getDistance(direction: Direction, headCoordinate: Coordinate, blockingCoordinate: Coordinate, maxCoordinate: Coordinate): Optional<Int> {
        var distance: Optional<Int> = Optional.empty()
        if (direction == Direction.NORTH) {
            val calculatedDistance = if (headCoordinate.y <= blockingCoordinate.y) {
                getDistanceDirectly(blockingCoordinate.y, headCoordinate.y)
            } else {
                getDistanceClipped(blockingCoordinate.y, headCoordinate.y, maxCoordinate.y)
            }
            distance = Optional.of(calculatedDistance)
        }
        return distance
    }

}
