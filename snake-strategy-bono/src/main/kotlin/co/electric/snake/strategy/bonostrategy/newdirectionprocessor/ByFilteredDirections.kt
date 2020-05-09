package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.slf4j.LoggerFactory
import java.util.*

class ByFilteredDirections : NewDirectionProcessor {

    companion object {
        private val LOG = LoggerFactory.getLogger(ByFilteredDirections::class.java)
    }

    override val order = 5

    override fun process(filteredDirections: SimpleDirectionContainer, equivalentBestDirections: SimpleDirectionContainer, blockingDirections: BlockingDirectionContainer): Optional<Direction> {
        return processFinalDirection(filteredDirections, LOG)
    }

}
