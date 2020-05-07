package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.slf4j.Logger

interface NewDirectionProcessor : Comparable<NewDirectionProcessor> {

    val order: Int

    fun process(filteredDirections: SimpleDirectionContainer, equivalentBestDirections: SimpleDirectionContainer, blockingDirections: BlockingDirectionContainer): Direction?

    fun processFinalDirection(directionContainer: SimpleDirectionContainer, log: Logger): Direction? {
        val direction = directionContainer.randomElement
        if (direction != null) {
            log.info("Final Direction: $direction")
        }
        return direction
    }

    override operator fun compareTo(comparedTo: NewDirectionProcessor): Int {
        return order - comparedTo.order
    }

}
