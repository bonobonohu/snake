package co.electric.snake.strategy.bonostrategy.newdirectionprocessor;

import co.electric.snake.framework.model.Direction;
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer;
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByFreeEquivalentBestDirections implements NewDirectionProcessor {

    private static final int ORDER = 1;

    private static final Logger LOG = LoggerFactory.getLogger(ByFreeEquivalentBestDirections.class);

    @Override
    public Direction process(SimpleDirectionContainer filteredDirections, SimpleDirectionContainer equivalentBestDirections, BlockingDirectionContainer blockingDirections) {
        Direction newDirection = null;

        if (!equivalentBestDirections.isEmpty()) {
            SimpleDirectionContainer freeEquivalentBestDirections = equivalentBestDirections.getElementsInANewInstance();
            freeEquivalentBestDirections.removeAll(blockingDirections.getDirections());

            newDirection = processFinalDirection(freeEquivalentBestDirections, LOG);
        }

        return newDirection;
    }

    @Override
    public int getOrder() {
        return ORDER;
    }

}
