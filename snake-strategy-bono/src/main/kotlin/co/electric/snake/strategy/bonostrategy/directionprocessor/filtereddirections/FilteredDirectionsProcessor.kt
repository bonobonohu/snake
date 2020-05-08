package co.electric.snake.strategy.bonostrategy.directionprocessor.filtereddirections

import co.electric.snake.framework.model.Arena
import co.electric.snake.framework.model.Snake
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import co.electric.snake.strategy.bonostrategy.directionprocessor.filtereddirections.closeddirections.ClosedDirectionsProcessor
import org.slf4j.LoggerFactory

class FilteredDirectionsProcessor(private val freeDirectionsProcessor: FreeDirectionsProcessor, private val closedDirectionsProcessor: ClosedDirectionsProcessor) {

    companion object {
        private val LOG = LoggerFactory.getLogger(FilteredDirectionsProcessor::class.java)
    }

    fun getDirections(snake: Snake, arena: Arena): SimpleDirectionContainer {
        val headCoordinate = snake.getHeadCoordinate()
        val freeDirections = freeDirectionsProcessor.getDirections(arena, headCoordinate)
        val closedDirections = closedDirectionsProcessor.getDirections(arena, headCoordinate)
        val filteredDirections = freeDirections.getElementsInANewInstance()
        filteredDirections.removeAll(closedDirections)
        LOG.info("Filtered Directions: $filteredDirections")
        return filteredDirections
    }

}
