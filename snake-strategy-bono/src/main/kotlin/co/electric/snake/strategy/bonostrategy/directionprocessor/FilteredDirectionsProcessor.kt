package co.electric.snake.strategy.bonostrategy.directionprocessor

import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.slf4j.LoggerFactory

class FilteredDirectionsProcessor {

    companion object {
        private val LOG = LoggerFactory.getLogger(FilteredDirectionsProcessor::class.java)
    }

    fun getDirections(directionsToUse: SimpleDirectionContainer, closedDirections: SimpleDirectionContainer): SimpleDirectionContainer {
        val filteredDirections = directionsToUse.getElementsInANewInstance()
        filteredDirections.removeAll(closedDirections)
        LOG.info("Filtered Directions: $filteredDirections")
        return filteredDirections
    }

}
