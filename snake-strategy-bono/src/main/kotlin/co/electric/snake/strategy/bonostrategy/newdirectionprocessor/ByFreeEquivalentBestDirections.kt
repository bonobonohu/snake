package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.slf4j.LoggerFactory

class ByFreeEquivalentBestDirections : NewDirectionProcessor {

    companion object {
        private val LOG = LoggerFactory.getLogger(ByFreeEquivalentBestDirections::class.java)
    }

    override val order = 1

    override fun process(filteredDirections: SimpleDirectionContainer, equivalentBestDirections: SimpleDirectionContainer, blockingDirections: BlockingDirectionContainer): Direction? {
        var newDirection: Direction? = null
        if (!equivalentBestDirections.isEmpty()) {
            val freeEquivalentBestDirections = equivalentBestDirections.elementsInANewInstance
            freeEquivalentBestDirections.removeAll(blockingDirections.directions)
            newDirection = processFinalDirection(freeEquivalentBestDirections, LOG)
        }
        return newDirection
    }

}
