package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.slf4j.LoggerFactory
import java.util.*

class ByFreeEquivalentSafeBestDirections : NewDirectionProcessor {

    companion object {
        private val LOG = LoggerFactory.getLogger(ByFreeEquivalentSafeBestDirections::class.java)
    }

    override val order = 1

    override fun process(filteredSafeDirections: SimpleDirectionContainer, equivalentSafeBestDirections: SimpleDirectionContainer, filteredFreeDirections: SimpleDirectionContainer, equivalentFreeBestDirections: SimpleDirectionContainer, blockingDirections: BlockingDirectionContainer): Optional<Direction> {
        val freeEquivalentSafeBestDirections = equivalentSafeBestDirections.getElementsInANewInstance()
        freeEquivalentSafeBestDirections.removeAll(blockingDirections.getDirections())
        return processFinalDirection(freeEquivalentSafeBestDirections, LOG)
    }

}
