package co.electric.snake.strategy.bonostrategy.newdirectionprocessor;

import co.electric.snake.framework.model.Direction;
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer;
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByFreeFilteredDirections implements NewDirectionProcessor {

    private static final int ORDER = 2;

    private static final Logger LOG = LoggerFactory.getLogger(ByFreeFilteredDirections.class);

    @Override
    public Direction process(SimpleDirectionContainer filteredDirections, SimpleDirectionContainer equivalentBestDirections, BlockingDirectionContainer blockingDirections) {
        Direction newDirection = null;

        if (!filteredDirections.isEmpty()) {
            SimpleDirectionContainer freeFilteredDirections = filteredDirections.getElementsInANewInstance();
            freeFilteredDirections.removeAll(blockingDirections.getDirections());

            newDirection = processFinalDirection(freeFilteredDirections, LOG);
        }

        return newDirection;
    }

    @Override
    public int getOrder() {
        return ORDER;
    }

}
