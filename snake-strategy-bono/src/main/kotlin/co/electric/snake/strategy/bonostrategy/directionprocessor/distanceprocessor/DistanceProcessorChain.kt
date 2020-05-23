package co.electric.snake.strategy.bonostrategy.directionprocessor.distanceprocessor

import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import java.util.*

class DistanceProcessorChain(private val distanceProcessors: Set<DistanceProcessor>) {

    companion object {
        private const val FALLBACK_DISTANCE = 0
    }

    fun getDistance(direction: Direction, headCoordinate: Coordinate, blockingCoordinate: Coordinate, maxCoordinate: Coordinate): Int {
        return Optional.ofNullable(distanceProcessors).orElse(emptySet()).stream()
                .map { it.getDistance(direction, headCoordinate, blockingCoordinate, maxCoordinate) }
                .filter(Optional<Int>::isPresent)
                .findFirst().map(Optional<Int>::get)
                .orElse(FALLBACK_DISTANCE)
    }

}
