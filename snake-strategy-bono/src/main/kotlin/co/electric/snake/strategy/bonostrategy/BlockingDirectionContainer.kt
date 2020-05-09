package co.electric.snake.strategy.bonostrategy

import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import java.util.*
import kotlin.collections.HashMap

class BlockingDirectionContainer {

    private val distanceToDirection: MutableMap<Direction, Int> = EnumMap(Direction::class.java)
    private val coordinateToDirection: MutableMap<Direction, Coordinate> = EnumMap(Direction::class.java)
    private val distanceToCoordinate: MutableMap<Coordinate, Int> = HashMap()
    private val directionToCoordinate: MutableMap<Coordinate, Direction> = HashMap()

    fun putData(direction: Direction, coordinate: Coordinate, distance: Int) {
        if (!directionHasDistance(direction)) {
            doPutData(direction, coordinate, distance)
        } else {
            val storedDistanceToDirection = getDistanceByDirection(direction)
            if (distance < storedDistanceToDirection) {
                doPutData(direction, coordinate, distance)
            }
        }
    }

    fun getDirections(): Set<Direction> {
        return distanceToDirection.keys
    }

    fun getFarthestBlockingDirections(): SimpleDirectionContainer {
        val directionToDistances: MutableMap<Int, SimpleDirectionContainer> = TreeMap(Collections.reverseOrder())
        distanceToDirection.forEach { (direction: Direction, distance: Int) ->
            val simpleDirections = Optional.ofNullable(directionToDistances[distance]).orElse(SimpleDirectionContainer())
            simpleDirections.add(direction)
            directionToDistances[distance] = simpleDirections
        }
        return directionToDistances.values.stream()
                .findFirst()
                .orElse(SimpleDirectionContainer())
    }

    private fun doPutData(direction: Direction, coordinate: Coordinate, distance: Int) {
        if (!coordinateHasDirection(coordinate)) {
            putDistanceToDirection(direction, distance)
            putCoordinateToDirection(direction, coordinate)
            putDistanceToCoordinate(coordinate, distance)
            putDirectionToCoordinate(coordinate, direction)
        } else {
            val storedDistanceToCoordinate = getDistanceByCoordinate(coordinate)
            if (distance <= storedDistanceToCoordinate) {
                removeDataByCoordinate(coordinate)
                putDistanceToDirection(direction, distance)
                putCoordinateToDirection(direction, coordinate)
                putDistanceToCoordinate(coordinate, distance)
                putDirectionToCoordinate(coordinate, direction)
            }
        }
    }

    private fun directionHasDistance(direction: Direction): Boolean {
        return distanceToDirection.containsKey(direction)
    }

    private fun coordinateHasDirection(coordinate: Coordinate): Boolean {
        return coordinateToDirection.containsValue(coordinate)
    }

    private fun getDistanceByDirection(direction: Direction): Int {
        return Optional.ofNullable(distanceToDirection[direction]).orElse(Int.MAX_VALUE)
    }

    private fun getDistanceByCoordinate(coordinate: Coordinate): Int {
        return Optional.ofNullable(distanceToCoordinate[coordinate]).orElse(Int.MAX_VALUE)
    }

    private fun getDirectionByCoordinate(coordinate: Coordinate): Direction? {
        return directionToCoordinate[coordinate]
    }

    private fun putDistanceToDirection(direction: Direction, distance: Int) {
        distanceToDirection[direction] = distance
    }

    private fun putCoordinateToDirection(direction: Direction, coordinate: Coordinate) {
        coordinateToDirection[direction] = coordinate
    }

    private fun putDistanceToCoordinate(coordinate: Coordinate, distance: Int) {
        distanceToCoordinate[coordinate] = distance
    }

    private fun putDirectionToCoordinate(coordinate: Coordinate, direction: Direction) {
        directionToCoordinate[coordinate] = direction
    }

    private fun removeDataByCoordinate(coordinate: Coordinate) {
        val directionToCoordinate = getDirectionByCoordinate(coordinate)
        removeFromDistanceToDirection(directionToCoordinate)
        removeFromCoordinateToDirection(directionToCoordinate)
        removeFromDistanceToCoordinate(coordinate)
        removeFromDirectionToCoordinate(coordinate)
    }

    private fun removeFromDistanceToDirection(direction: Direction?) {
        distanceToDirection.remove(direction)
    }

    private fun removeFromCoordinateToDirection(direction: Direction?) {
        coordinateToDirection.remove(direction)
    }

    private fun removeFromDistanceToCoordinate(coordinate: Coordinate) {
        distanceToCoordinate.remove(coordinate)
    }

    private fun removeFromDirectionToCoordinate(coordinate: Coordinate) {
        directionToCoordinate.remove(coordinate)
    }

    override fun toString(): String {
        return "DirectionData [distanceToDirection=$distanceToDirection]"
    }

}
