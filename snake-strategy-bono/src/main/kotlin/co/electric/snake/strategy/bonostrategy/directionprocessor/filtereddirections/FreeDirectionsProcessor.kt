package co.electric.snake.strategy.bonostrategy.directionprocessor.filtereddirections

import co.electric.snake.framework.model.Arena
import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.slf4j.LoggerFactory

class FreeDirectionsProcessor {

    companion object {
        private val LOG = LoggerFactory.getLogger(FreeDirectionsProcessor::class.java)
    }

    fun getDirections(arena: Arena, headCoordinate: Coordinate): SimpleDirectionContainer {
        val freeDirections = SimpleDirectionContainer()
        Direction.values().forEach {
            if (!arena.isOccupied(arena.nextCoordinate(headCoordinate, it))) {
                freeDirections.add(it)
            }
        }
        LOG.info("Free Directions: $freeDirections")
        return freeDirections
    }

}
