package co.electric.snake.framework.model

import co.electric.snake.framework.strategy.SnakeStrategy

class ModifiableSnake(arena: ModifiableArena, strategy: SnakeStrategy, name: String) : Snake(arena, strategy, name) {

    fun move() {
        val nextCoordinate = decideNextCoordinate()
        moveTo(nextCoordinate)
    }

}
