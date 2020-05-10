package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.slf4j.LoggerFactory
import java.util.*

class ByFreeEquivalentFreeBestDirections : NewDirectionProcessor {

    companion object {
        private val LOG = LoggerFactory.getLogger(ByFreeEquivalentFreeBestDirections::class.java)
    }

    override val order = 6

    override fun process(filteredSafeDirections: SimpleDirectionContainer, equivalentSafeBestDirections: SimpleDirectionContainer, filteredFreeDirections: SimpleDirectionContainer, equivalentFreeBestDirections: SimpleDirectionContainer, blockingDirections: BlockingDirectionContainer): Optional<Direction> {
        val freeEquivalentFreeBestDirections = equivalentFreeBestDirections.getElementsInANewInstance()
        freeEquivalentFreeBestDirections.removeAll(blockingDirections.getDirections())
        return processFinalDirection(freeEquivalentFreeBestDirections, LOG)
    }

}
