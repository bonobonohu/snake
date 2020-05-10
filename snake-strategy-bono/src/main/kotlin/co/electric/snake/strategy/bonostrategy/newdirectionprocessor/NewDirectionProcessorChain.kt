package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import java.util.*

class NewDirectionProcessorChain(private val newDirectionProcessors: Set<NewDirectionProcessor>) {

    companion object {

        private val FALLBACK_DIRECTION = Direction.SOUTH

    }

    fun process(filteredSafeDirections: SimpleDirectionContainer, equivalentSafeBestDirections: SimpleDirectionContainer, filteredFreeDirections: SimpleDirectionContainer, equivalentFreeBestDirections: SimpleDirectionContainer, blockingDirections: BlockingDirectionContainer): Direction {
        return Optional.ofNullable(newDirectionProcessors).orElse(emptySet()).stream()
                .sorted()
                .map { it.process(filteredSafeDirections, equivalentSafeBestDirections, filteredFreeDirections, equivalentFreeBestDirections, blockingDirections) }
                .filter(Optional<Direction>::isPresent)
                .findFirst().map(Optional<Direction>::get)
                .orElse(FALLBACK_DIRECTION)
    }

}
