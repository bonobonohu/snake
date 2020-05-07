package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import java.util.*

class NewDirectionProcessorChain(private val newDirectionProcessors: Set<NewDirectionProcessor>) {

    fun process(filteredDirections: SimpleDirectionContainer, equivalentBestDirections: SimpleDirectionContainer, blockingDirections: BlockingDirectionContainer): Direction? {
        return Optional.ofNullable(newDirectionProcessors).orElse(emptySet()).stream()
                .sorted()
                .map { newDirectionProcessor: NewDirectionProcessor -> newDirectionProcessor.process(filteredDirections, equivalentBestDirections, blockingDirections) }
                .filter { obj: Direction? -> Objects.nonNull(obj) }
                .findFirst()
                .orElse(Direction.SOUTH)
    }

}
