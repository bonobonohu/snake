package co.electric.snake.strategy.bonostrategy

import co.electric.snake.framework.model.Direction
import java.util.*

class SimpleDirectionContainer : HashSet<Direction> {

    constructor() : super()
    constructor(collection: Collection<Direction>) : super(collection)

    companion object {
        private val RANDOM = Random()
    }

    fun getElementsInANewInstance(): SimpleDirectionContainer {
        return SimpleDirectionContainer(this)
    }

    fun getRandomElement(): Direction? {
        return if (isNotEmpty()) elementAt(RANDOM.nextInt(size)) else null
    }

}
