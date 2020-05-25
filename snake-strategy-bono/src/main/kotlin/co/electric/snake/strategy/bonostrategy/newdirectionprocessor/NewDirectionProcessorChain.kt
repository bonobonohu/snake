package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import java.util.*

class NewDirectionProcessorChain(private val newDirectionProcessors: Set<NewDirectionProcessor>) {

    companion object {
        private val FALLBACK_DIRECTION = Direction.SOUTH
    }

    fun process(filteredDirections: SimpleDirectionContainer, equivalentBestDirections: SimpleDirectionContainer, blockingDirections: BlockingDirectionContainer): Direction {
        return Optional.ofNullable(newDirectionProcessors).orElse(emptySet()).stream()
                .sorted()
                .map { it.process(filteredDirections, equivalentBestDirections, blockingDirections) }
                .filter(Optional<Direction>::isPresent)
                .findAny().map(Optional<Direction>::get)
                .orElse(FALLBACK_DIRECTION)
    }

}
