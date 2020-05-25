package co.electric.snake.framework.model

import co.electric.snake.framework.strategy.SnakeStrategy

class TestSnake(private val modifiableArena: ModifiableArena, snakeStrategy: SnakeStrategy, name: String) : ModifiableSnake(modifiableArena, snakeStrategy, name) {

    init {
        bodyItems.clear()
    }

    fun addToBody(bodyItem: Coordinate): TestSnake {
        if (!modifiableArena.isOccupied(bodyItem)) {
            bodyItems.add(bodyItem)
        }
        return this
    }

}
