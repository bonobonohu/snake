package co.electric.snake.strategy.bonostrategy.directionprocessor.distanceprocessor

import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import java.util.*

interface DistanceProcessor {

    fun getDistance(direction: Direction, headCoordinate: Coordinate, blockingCoordinate: Coordinate, maxCoordinate: Coordinate): Optional<Int>

    fun getDistanceDirectly(biggerCoordinateFactor: Int, smallerCoordinateFactor: Int): Int {
        return biggerCoordinateFactor - smallerCoordinateFactor
    }

    fun getDistanceClipped(directCoordinateFactor: Int, clipperCoordinateFactor: Int, maxCoordinateFactor: Int): Int {
        return (directCoordinateFactor + (maxCoordinateFactor - clipperCoordinateFactor))
    }

}
