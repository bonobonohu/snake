package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.slf4j.LoggerFactory
import java.util.*

class ByFarthestBlockingDirections : NewDirectionProcessor {

    companion object {
        private val LOG = LoggerFactory.getLogger(ByFarthestBlockingDirections::class.java)
    }

    override val order = 3

    override fun process(filteredDirections: SimpleDirectionContainer, equivalentBestDirections: SimpleDirectionContainer, blockingDirections: BlockingDirectionContainer): Optional<Direction> {
        val farthestBlockingDirections = blockingDirections.getBlockingsOrdered().values.stream()
                .findFirst()
                .orElse(SimpleDirectionContainer())
        return processFinalDirection(farthestBlockingDirections, LOG)
    }

}
