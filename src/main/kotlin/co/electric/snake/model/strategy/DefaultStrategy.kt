package co.electric.snake.model.strategy

import co.electric.snake.model.Arena
import co.electric.snake.model.Direction
import co.electric.snake.model.Snake
import java.util.*

class DefaultStrategy : SnakeStrategy {

    override fun nextMove(snake: Snake, arena: Arena): Direction {
        val startCoordinate = snake.getHeadCoordinate()
        val foodCoordinate = arena.food[0].coordinate
        var minDistance = Int.MAX_VALUE
        var bestDirection: Direction = Direction.WEST
        Arrays.stream(Direction.values()).forEach { direction ->
            val nextCoordinate = arena.nextCoordinate(startCoordinate, direction)
            val actualDistance = nextCoordinate.minDistance(foodCoordinate, arena.maxCoordinate)
            if (minDistance > actualDistance && !arena.isOccupied(nextCoordinate)) {
                minDistance = actualDistance
                bestDirection = direction
            }
        }
        return bestDirection
    }

}
