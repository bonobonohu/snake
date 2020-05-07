package co.electric.snake.strategy.bonostrategy.directionprocessor.filtereddirections;

import co.electric.snake.framework.model.Arena;
import co.electric.snake.framework.model.Coordinate;
import co.electric.snake.framework.model.Snake;
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer;
import co.electric.snake.strategy.bonostrategy.directionprocessor.filtereddirections.closeddirections.ClosedDirectionsProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilteredDirectionsProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(FilteredDirectionsProcessor.class);

    private final FreeDirectionsProcessor freeDirectionsProcessor;
    private final ClosedDirectionsProcessor closedDirectionsProcessor;

    public FilteredDirectionsProcessor(FreeDirectionsProcessor freeDirectionsProcessor, ClosedDirectionsProcessor closedDirectionsProcessor) {
        this.freeDirectionsProcessor = freeDirectionsProcessor;
        this.closedDirectionsProcessor = closedDirectionsProcessor;
    }

    public SimpleDirectionContainer getDirections(Snake snake, Arena arena) {
        Coordinate actualHeadCoordinate = snake.getHeadCoordinate();

        SimpleDirectionContainer freeDirections = freeDirectionsProcessor.getDirections(arena, actualHeadCoordinate);
        SimpleDirectionContainer closedDirections = closedDirectionsProcessor.getDirections(arena, actualHeadCoordinate);

        SimpleDirectionContainer filteredDirections = freeDirections.getElementsInANewInstance();
        filteredDirections.removeAll(closedDirections);

        LOG.info("Filtered Directions: " + filteredDirections);

        return filteredDirections;
    }

}
