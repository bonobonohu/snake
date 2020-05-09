package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.slf4j.Logger
import java.util.*

interface NewDirectionProcessor : Comparable<NewDirectionProcessor> {

    val order: Int

    fun process(filteredDirections: SimpleDirectionContainer, equivalentBestDirections: SimpleDirectionContainer, blockingDirections: BlockingDirectionContainer): Optional<Direction>

    fun processFinalDirection(directionContainer: SimpleDirectionContainer, log: Logger): Optional<Direction> {
        val direction = directionContainer.getRandomElement()
        if (direction.isPresent) {
            log.info("Final Direction: ${direction.get()}")
        }
        return direction
    }

    override operator fun compareTo(other: NewDirectionProcessor): Int {
        return order - other.order
    }

}
