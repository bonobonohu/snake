package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.slf4j.LoggerFactory
import java.util.*

class ByEquivalentFreeBestDirections : NewDirectionProcessor {

    companion object {
        private val LOG = LoggerFactory.getLogger(ByEquivalentFreeBestDirections::class.java)
    }

    override val order = 8

    override fun process(filteredSafeDirections: SimpleDirectionContainer, equivalentSafeBestDirections: SimpleDirectionContainer, filteredFreeDirections: SimpleDirectionContainer, equivalentFreeBestDirections: SimpleDirectionContainer, blockingDirections: BlockingDirectionContainer): Optional<Direction> {
        return processFinalDirection(equivalentFreeBestDirections, LOG)
    }

}
