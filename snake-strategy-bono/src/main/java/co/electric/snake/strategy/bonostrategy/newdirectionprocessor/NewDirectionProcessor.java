package co.electric.snake.strategy.bonostrategy.newdirectionprocessor;

import co.electric.snake.framework.model.Direction;
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer;
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public interface NewDirectionProcessor extends Comparable<NewDirectionProcessor> {

    Direction process(SimpleDirectionContainer filteredDirections, SimpleDirectionContainer equivalentBestDirections, BlockingDirectionContainer blockingDirections);

    int getOrder();

    default Direction processFinalDirection(SimpleDirectionContainer directionContainer, Logger log) {
        final Direction direction = directionContainer.getRandomElement();
        log.info("Final Direction: " + direction);
        return direction;
    }

    default int compareTo(@NotNull NewDirectionProcessor comparedTo) {
        return this.getOrder() - comparedTo.getOrder();
    }

}
