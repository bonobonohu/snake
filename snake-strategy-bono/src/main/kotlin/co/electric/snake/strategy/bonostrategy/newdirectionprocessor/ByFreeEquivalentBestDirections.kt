package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.slf4j.LoggerFactory
import java.util.*

class ByFreeEquivalentBestDirections : NewDirectionProcessor {

    companion object {
        private val LOG = LoggerFactory.getLogger(ByFreeEquivalentBestDirections::class.java)
    }

    override val order = 1

    override fun process(filteredDirections: SimpleDirectionContainer, equivalentBestDirections: SimpleDirectionContainer, blockingDirections: BlockingDirectionContainer): Optional<Direction> {
        val freeEquivalentBestDirections = equivalentBestDirections.getElementsInANewInstance()
        freeEquivalentBestDirections.removeAll(blockingDirections.directions)
        return processFinalDirection(freeEquivalentBestDirections, LOG)
    }

}
