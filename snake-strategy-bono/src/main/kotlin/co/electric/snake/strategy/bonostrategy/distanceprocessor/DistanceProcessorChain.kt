package co.electric.snake.strategy.bonostrategy.distanceprocessor

import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import java.util.*

class DistanceProcessorChain(private val distanceProcessors: Set<DistanceProcessor>) {

    fun getDistance(actualDirection: Direction, actualHeadCoordinate: Coordinate, coordinateToInvestigate: Coordinate, maxCoordinate: Coordinate): Int {
        return Optional.ofNullable(distanceProcessors).orElse(emptySet()).stream()
                .map { it.getDistance(actualDirection, actualHeadCoordinate, coordinateToInvestigate, maxCoordinate) }
                .filter(Optional<Int>::isPresent)
                .findFirst().map(Optional<Int>::get)
                .orElse(1000)
    }

}
