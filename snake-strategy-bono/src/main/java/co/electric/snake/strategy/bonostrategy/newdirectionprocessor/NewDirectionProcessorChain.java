package co.electric.snake.strategy.bonostrategy.newdirectionprocessor;

import co.electric.snake.framework.model.Direction;
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer;
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class NewDirectionProcessorChain {

    private final Set<NewDirectionProcessor> newDirectionProcessors;

    public NewDirectionProcessorChain(Set<NewDirectionProcessor> newDirectionProcessors) {
        this.newDirectionProcessors = newDirectionProcessors;
    }

    public Direction process(SimpleDirectionContainer filteredDirections, SimpleDirectionContainer equivalentBestDirections, BlockingDirectionContainer blockingDirections) {
        return Optional.ofNullable(newDirectionProcessors).orElse(Collections.emptySet()).stream()
                .sorted()
                .map(newDirectionProcessor -> newDirectionProcessor.process(filteredDirections, equivalentBestDirections, blockingDirections))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

}
