package co.electric.snake.strategy.bonostrategy.directionprocessor

import co.electric.snake.framework.model.Arena
import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import co.electric.snake.framework.model.Snake
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import co.electric.snake.strategy.bonostrategy.directionprocessor.distanceprocessor.DistanceProcessorChain
import org.slf4j.LoggerFactory

class BlockingDirectionsProcessor(private val distanceProcessorChain: DistanceProcessorChain) {

    companion object {
        private const val SAFE_THRESHOLD = 25

        private val LOG = LoggerFactory.getLogger(BlockingDirectionsProcessor::class.java)
    }

    fun getDirections(snake: Snake, arena: Arena, directionsToUse: SimpleDirectionContainer): BlockingDirectionContainer {
        val blockingDirections = BlockingDirectionContainer()
        val headCoordinate = snake.getHeadCoordinate()
        val maxCoordinate = arena.maxCoordinate
        directionsToUse.forEach {
            var coordinateToInvestigate = headCoordinate
            val maxCoordinateForDirection = getMaxCoordinateForDirection(maxCoordinate, it)
            for (i in 0 until maxCoordinateForDirection) {
                coordinateToInvestigate = arena.nextCoordinate(coordinateToInvestigate, it)
                if (arena.isOccupied(coordinateToInvestigate)) {
                    val blockingSnake = getBlockingSnake(arena, coordinateToInvestigate)
                    val blockingTailLength = getBlockingTailLength(blockingSnake, coordinateToInvestigate)
                    val distanceToBlock = getDistanceToBlock(it, headCoordinate, coordinateToInvestigate, maxCoordinate)
                    if (distanceToBlock <= SAFE_THRESHOLD && isBlockingRisk(blockingTailLength, distanceToBlock)) {
                        blockingDirections.putData(it, coordinateToInvestigate, distanceToBlock)
                    }
                }
            }
        }
        LOG.info("Blocking Directions: $blockingDirections")
        return blockingDirections
    }

    private fun getMaxCoordinateForDirection(maxCoordinate: Coordinate, direction: Direction): Int {
        return if (setOf(Direction.NORTH, Direction.SOUTH).contains(direction)) {
            maxCoordinate.x - 1
        } else {
            maxCoordinate.y - 1
        }
    }

    private fun getBlockingSnake(arena: Arena, blockingCoordinate: Coordinate): Snake {
        return arena.getSnakesInNewList().stream()
                .filter { snake -> snake.getBodyItemsInNewList().stream().anyMatch { it == blockingCoordinate } }
                .findAny()
                .orElse(null)
    }

    private fun getBlockingTailLength(blockingSnake: Snake, blockingCoordinate: Coordinate): Int {
        var blockingTailLength = 0
        var reachedTheBlockingPart = false
        blockingSnake.getBodyItemsInNewList().forEach {
            if (it == blockingCoordinate) {
                reachedTheBlockingPart = true
            }
            if (reachedTheBlockingPart) {
                blockingTailLength++
            }
        }
        return blockingTailLength
    }

    private fun getDistanceToBlock(direction: Direction, headCoordinate: Coordinate, blockingCoordinate: Coordinate, maxCoordinate: Coordinate): Int {
        return distanceProcessorChain.getDistance(direction, headCoordinate, blockingCoordinate, maxCoordinate)
    }

    private fun isBlockingRisk(blockingTailLength: Int, distanceToBlock: Int): Boolean {
        return blockingTailLength >= distanceToBlock
    }

}
