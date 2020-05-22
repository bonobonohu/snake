package co.electric.snake.strategy.bonostrategy

import co.electric.snake.framework.model.Direction
import java.util.*

class SimpleDirectionContainer : HashSet<Direction> {

    companion object {
        private val RANDOM = Random()
    }

    constructor() : super()
    constructor(collection: Collection<Direction>) : super(collection)

    fun getElementsInANewInstance(): SimpleDirectionContainer {
        return SimpleDirectionContainer(this)
    }

    fun getRandomElement(): Optional<Direction> {
        return Optional.ofNullable(elementAtOrNull(if (size > 0) RANDOM.nextInt(size) else 0))
    }

}
