package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.slf4j.LoggerFactory
import java.util.*

class ByKispalEsABorz : NewDirectionProcessor {

    companion object {
        private val LOG = LoggerFactory.getLogger(ByKispalEsABorz::class.java)
    }

    override val order = 10

    override fun process(filteredSafeDirections: SimpleDirectionContainer, equivalentSafeBestDirections: SimpleDirectionContainer, filteredFreeDirections: SimpleDirectionContainer, equivalentFreeBestDirections: SimpleDirectionContainer, blockingDirections: BlockingDirectionContainer): Optional<Direction> {
        val kispalDirections = SimpleDirectionContainer()
        kispalDirections.add(Direction.SOUTH)
        return processFinalDirection(kispalDirections, LOG)
    }

}
