package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.slf4j.LoggerFactory

class ByKispalEsABorz : NewDirectionProcessor {

    companion object {
        private val LOG = LoggerFactory.getLogger(ByKispalEsABorz::class.java)
    }

    override val order = 5

    override fun process(filteredDirections: SimpleDirectionContainer, equivalentBestDirections: SimpleDirectionContainer, blockingDirections: BlockingDirectionContainer): Direction? {
        val newDirection: Direction?
        val kispalDirections = SimpleDirectionContainer()
        kispalDirections.add(Direction.SOUTH)
        newDirection = processFinalDirection(kispalDirections, LOG)
        return newDirection
    }

}
