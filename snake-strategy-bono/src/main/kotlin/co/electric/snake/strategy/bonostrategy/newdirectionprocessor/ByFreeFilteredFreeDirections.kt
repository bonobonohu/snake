package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.slf4j.LoggerFactory
import java.util.*

class ByFreeFilteredFreeDirections : NewDirectionProcessor {

    companion object {
        private val LOG = LoggerFactory.getLogger(ByFreeFilteredFreeDirections::class.java)
    }

    override val order = 7

    override fun process(filteredSafeDirections: SimpleDirectionContainer, equivalentSafeBestDirections: SimpleDirectionContainer, filteredFreeDirections: SimpleDirectionContainer, equivalentFreeBestDirections: SimpleDirectionContainer, blockingDirections: BlockingDirectionContainer): Optional<Direction> {
        val freeFilteredFreeDirections = filteredFreeDirections.getElementsInANewInstance()
        freeFilteredFreeDirections.removeAll(blockingDirections.getDirections())
        return processFinalDirection(freeFilteredFreeDirections, LOG)
    }

}
