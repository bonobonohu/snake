package co.electric.snake.model

import co.electric.snake.model.strategy.SnakeStrategy

class ModifiableSnake(arena: ModifiableArena, strategy: SnakeStrategy, name: String) : Snake(arena, strategy, name) {

    fun move() {
        val nextCoordinate = decideNextCoordinate()
        moveTo(nextCoordinate)
    }

}
