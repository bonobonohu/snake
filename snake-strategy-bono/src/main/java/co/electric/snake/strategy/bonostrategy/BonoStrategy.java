package co.electric.snake.strategy.bonostrategy;

import co.electric.snake.framework.model.Arena;
import co.electric.snake.framework.model.Direction;
import co.electric.snake.framework.model.Snake;
import co.electric.snake.framework.strategy.SnakeStrategy;
import co.electric.snake.strategy.bonostrategy.directionprocessor.BlockingDirectionsProcessor;
import co.electric.snake.strategy.bonostrategy.directionprocessor.EquivalentBestDirectionsProcessor;
import co.electric.snake.strategy.bonostrategy.directionprocessor.filtereddirections.FilteredDirectionsProcessor;
import co.electric.snake.strategy.bonostrategy.newdirectionprocessor.NewDirectionProcessorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BonoStrategy implements SnakeStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(BonoStrategy.class);

    private final FilteredDirectionsProcessor filteredDirectionsProcessor;
    private final EquivalentBestDirectionsProcessor equivalentBestDirectionsProcessor;
    private final BlockingDirectionsProcessor blockingDirectionsProcessor;
    private final NewDirectionProcessorChain newDirectionProcessorChain;

    public BonoStrategy(FilteredDirectionsProcessor filteredDirectionsProcessor, EquivalentBestDirectionsProcessor equivalentBestDirectionsProcessor, BlockingDirectionsProcessor blockingDirectionsProcessor, NewDirectionProcessorChain newDirectionProcessorChain) {
        this.filteredDirectionsProcessor = filteredDirectionsProcessor;
        this.equivalentBestDirectionsProcessor = equivalentBestDirectionsProcessor;
        this.blockingDirectionsProcessor = blockingDirectionsProcessor;
        this.newDirectionProcessorChain = newDirectionProcessorChain;
    }

    @Override
    public Direction nextMove(Snake snake, Arena arena) {
        LOG.info("--- BEGIN " + snake.getName() + " ---");
        LOG.info("Length: " + snake.getLength());
        LOG.info("Food: " + arena.getFoodInNewList().get(0).getCoordinate());
        LOG.info("Head: " + snake.getHeadCoordinate());

        Direction newDirection = process(snake, arena);

        LOG.info("--- END " + snake.getName() + " ---");

        return newDirection;
    }

    private Direction process(Snake snake, Arena arena) {
        SimpleDirectionContainer filteredDirections = filteredDirectionsProcessor.getDirections(snake, arena);
        SimpleDirectionContainer equivalentBestDirections = equivalentBestDirectionsProcessor.getDirections(snake, arena, filteredDirections);
        BlockingDirectionContainer blockingDirections = blockingDirectionsProcessor.getDirections(snake, arena, filteredDirections);

        return newDirectionProcessorChain.process(filteredDirections, equivalentBestDirections, blockingDirections);
    }

}
