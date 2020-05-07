package co.electric.snake.strategy.bonostrategy.newdirectionprocessor;

import co.electric.snake.framework.model.Direction;
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer;
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByKispalEsABorz implements NewDirectionProcessor {

    private static final int ORDER = 5;

    private static final Logger LOG = LoggerFactory.getLogger(ByKispalEsABorz.class);

    @Override
    public Direction process(SimpleDirectionContainer filteredDirections, SimpleDirectionContainer equivalentBestDirections, BlockingDirectionContainer blockingDirections) {
        Direction newDirection;

        SimpleDirectionContainer kispalDirections = new SimpleDirectionContainer();
        kispalDirections.add(Direction.SOUTH);

        newDirection = processFinalDirection(kispalDirections, LOG);

        return newDirection;
    }

    @Override
    public int getOrder() {
        return ORDER;
    }

}
