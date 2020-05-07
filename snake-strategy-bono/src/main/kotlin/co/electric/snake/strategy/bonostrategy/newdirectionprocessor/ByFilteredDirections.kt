package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.slf4j.LoggerFactory

class ByFilteredDirections : NewDirectionProcessor {

    companion object {
        private val LOG = LoggerFactory.getLogger(ByFilteredDirections::class.java)
    }

    override val order = 4

    override fun process(filteredDirections: SimpleDirectionContainer, equivalentBestDirections: SimpleDirectionContainer, blockingDirections: BlockingDirectionContainer): Direction? {
        var newDirection: Direction? = null
        if (!filteredDirections.isEmpty()) {
            newDirection = processFinalDirection(filteredDirections, LOG)
        }
        return newDirection
    }

}
