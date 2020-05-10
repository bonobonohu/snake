package co.electric.snake.strategy.bonostrategy.directionprocessor.closeddirections

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.slf4j.LoggerFactory

class ClosedDirectionsCollector {

    companion object {
        private val LOG = LoggerFactory.getLogger(ClosedDirectionsCollector::class.java)
    }

    fun getDirections(freeCoordinateCountsByDirections: Map<Direction, Int>): SimpleDirectionContainer {
        val closedDirections = SimpleDirectionContainer()
        var maxCount = -1
        freeCoordinateCountsByDirections.values.forEach {
            if (it > maxCount) {
                maxCount = it
            }
        }
        val maxDirections = SimpleDirectionContainer()
        freeCoordinateCountsByDirections.keys.forEach {
            if (maxCount == freeCoordinateCountsByDirections[it]) {
                maxDirections.add(it)
            }
        }
        closedDirections.addAll(freeCoordinateCountsByDirections.keys)
        closedDirections.removeAll(maxDirections)
        LOG.info("Closed Directions: $closedDirections")
        return closedDirections
    }

}
