package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.slf4j.LoggerFactory

class ByFreeFilteredDirections : NewDirectionProcessor {

    companion object {
        private val LOG = LoggerFactory.getLogger(ByFreeFilteredDirections::class.java)
    }

    override val order = 2

    override fun process(filteredDirections: SimpleDirectionContainer, equivalentBestDirections: SimpleDirectionContainer, blockingDirections: BlockingDirectionContainer): Direction? {
        var newDirection: Direction? = null
        if (!filteredDirections.isEmpty()) {
            val freeFilteredDirections = filteredDirections.getElementsInANewInstance()
            freeFilteredDirections.removeAll(blockingDirections.directions)
            newDirection = processFinalDirection(freeFilteredDirections, LOG)
        }
        return newDirection
    }

}
