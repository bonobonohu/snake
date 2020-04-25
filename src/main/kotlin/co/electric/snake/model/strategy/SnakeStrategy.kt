package co.electric.snake.model.strategy

import co.electric.snake.model.Arena
import co.electric.snake.model.Direction
import co.electric.snake.model.Snake

interface SnakeStrategy {

    fun nextMove(snake: Snake, arena: Arena): Direction

}
