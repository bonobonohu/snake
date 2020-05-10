package co.electric.snake.strategy.bonostrategy.directionprocessor

import co.electric.snake.framework.model.Arena
import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import co.electric.snake.framework.model.Snake
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.slf4j.LoggerFactory

class SafeDirectionsProcessor {

    companion object {
        private const val SAFE_THRESHOLD = 2

        private val LOG = LoggerFactory.getLogger(SafeDirectionsProcessor::class.java)
    }

    fun getDirections(snake: Snake, arena: Arena): SimpleDirectionContainer {
        val safeDirections = SimpleDirectionContainer()
        val unsafeDirections = SimpleDirectionContainer()
        val headCoordinate = snake.getHeadCoordinate()
        Direction.values().forEach {
            var nextCoordinate = headCoordinate
            var isSafe = true
            forLabel@ for (i in 0 until SAFE_THRESHOLD) {
                nextCoordinate = arena.nextCoordinate(nextCoordinate, it)
                if (arena.isOccupied(nextCoordinate) || !hasEnoughSpace(nextCoordinate, it, i + 1, arena)) {
                    isSafe = false
                    break@forLabel
                }
            }
            if (isSafe) {
                safeDirections.add(it)
            } else {
                unsafeDirections.add(it)
            }
        }
        LOG.info("Safe Directions: $safeDirections")
        LOG.info("Unsafe Directions: $unsafeDirections")
        return unsafeDirections
    }

    private fun hasEnoughSpace(coordinate: Coordinate, direction: Direction, stepsToTake: Int, arena: Arena): Boolean {
        var freeCoordinatesCount = 0
        var nextCoordinate = coordinate
        getNeighbouringDirections(direction).forEach {
            forLabel@ for (i in 0 until SAFE_THRESHOLD) {
                nextCoordinate = arena.nextCoordinate(nextCoordinate, it)
                if (
                        !arena.isOccupied(nextCoordinate)
                        || !isBlockingRisk(nextCoordinate, stepsToTake + i + 1, arena)
                ) {
                    freeCoordinatesCount++
                } else {
                    break@forLabel
                }
            }
        }
        return freeCoordinatesCount >= SAFE_THRESHOLD
    }

    // private fun hasEnoughSpaceOriginal(coordinate: Coordinate, direction: Direction, stepsToTake: Int, arena: Arena): Boolean {
    //     var freeCoordinatesCount = 0
    //     var nextCoordinate = coordinate
    //     run breakLabel@{
    //         getNeighbouringDirections(direction).forEach {
    //             forLabel@ for (i in 0 until SAFE_THRESHOLD) {
    //                 nextCoordinate = arena.nextCoordinate(nextCoordinate, it)
    //                 if (
    //                         !arena.isOccupied(nextCoordinate)
    //                         || !isBlockingRisk(nextCoordinate, stepsToTake + i + 1, arena)
    //                 ) {
    //                     freeCoordinatesCount++
    //                     if (freeCoordinatesCount == SAFE_THRESHOLD) {
    //                         return@breakLabel
    //                     }
    //                 } else {
    //                     break@forLabel
    //                 }
    //             }
    //         }
    //     }
    //     return freeCoordinatesCount >= SAFE_THRESHOLD
    // }

    private fun getNeighbouringDirections(direction: Direction): SimpleDirectionContainer {
        val neighBouringDirections = SimpleDirectionContainer()
        val verticalDirections = setOf(Direction.NORTH, Direction.SOUTH)
        val horizontalDirections = setOf(Direction.EAST, Direction.WEST)
        when (direction) {
            Direction.NORTH -> neighBouringDirections.addAll(horizontalDirections)
            Direction.SOUTH -> neighBouringDirections.addAll(horizontalDirections)
            Direction.EAST -> neighBouringDirections.addAll(verticalDirections)
            Direction.WEST -> neighBouringDirections.addAll(verticalDirections)
        }
        return neighBouringDirections
    }

    private fun isBlockingRisk(coordinate: Coordinate, distanceToBlock: Int, arena: Arena): Boolean {
        val blockingSnake = getBlockingSnake(arena, coordinate)
        val blockingTailLength = getBlockingTailLength(blockingSnake, coordinate)
        return isBlockingRisk(blockingTailLength, distanceToBlock)
    }

}
