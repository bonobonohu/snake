package co.electric.snake.strategy.bonostrategy.directionprocessor

import co.electric.snake.framework.model.Arena
import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Snake

fun getBlockingSnake(arena: Arena, blockingCoordinate: Coordinate): Snake {
    return arena.getSnakesInNewList().stream()
            .filter { snake -> snake.getBodyItemsInNewList().stream().anyMatch { it == blockingCoordinate } }
            .findAny()
            .orElse(null)
}

fun getBlockingTailLength(blockingSnake: Snake, blockingCoordinate: Coordinate): Int {
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

fun isBlockingRisk(blockingTailLength: Int, distanceToBlock: Int): Boolean {
    return blockingTailLength >= distanceToBlock
}
