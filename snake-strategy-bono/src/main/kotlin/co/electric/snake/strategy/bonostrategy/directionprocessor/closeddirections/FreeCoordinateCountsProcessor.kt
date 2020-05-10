package co.electric.snake.strategy.bonostrategy.directionprocessor.closeddirections

import co.electric.snake.framework.model.Arena
import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import co.electric.snake.framework.model.Snake
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.slf4j.LoggerFactory
import java.util.*
import kotlin.collections.HashSet

class FreeCoordinateCountsProcessor {

    companion object {
        private val LOG = LoggerFactory.getLogger(FreeCoordinateCountsProcessor::class.java)
    }

    fun getFreeCoordinateCountsByDirections(snake: Snake, arena: Arena, freeDirections: SimpleDirectionContainer): MutableMap<Direction, Int> {
        val freeCoordinateCountsByDirections: MutableMap<Direction, Int> = EnumMap(Direction::class.java)
        val headCoordinate = snake.getHeadCoordinate()
        freeDirections.forEach {
            val freeCoordinates: MutableSet<Coordinate> = HashSet()
            processFreeCoordinates(arena, arena.nextCoordinate(headCoordinate, it), freeCoordinates)
            freeCoordinateCountsByDirections[it] = freeCoordinates.size
        }
        LOG.info("Free Coordinate Counts By Directions $freeCoordinateCountsByDirections")
        return freeCoordinateCountsByDirections
    }

    private fun processFreeCoordinates(arena: Arena, nextFreeCoordinate: Coordinate, freeCoordinates: MutableSet<Coordinate>) {
        if (!freeCoordinates.contains(nextFreeCoordinate)) {
            freeCoordinates.add(nextFreeCoordinate)
            Direction.values().forEach {
                val nextCoordinate = arena.nextCoordinate(nextFreeCoordinate, it)
                if (!arena.isOccupied(nextCoordinate)) {
                    processFreeCoordinates(arena, nextCoordinate, freeCoordinates)
                }
            }
        }
    }

}
