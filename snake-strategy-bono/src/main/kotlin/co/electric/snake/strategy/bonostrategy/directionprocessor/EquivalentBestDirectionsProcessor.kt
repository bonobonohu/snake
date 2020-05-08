package co.electric.snake.strategy.bonostrategy.directionprocessor

import co.electric.snake.framework.model.Arena
import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Snake
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.slf4j.LoggerFactory
import java.util.*

class EquivalentBestDirectionsProcessor {

    companion object {
        private val LOG = LoggerFactory.getLogger(EquivalentBestDirectionsProcessor::class.java)
    }

    fun getDirections(snake: Snake, arena: Arena, filteredDirections: SimpleDirectionContainer): SimpleDirectionContainer {
        val headCoordinate = snake.getHeadCoordinate()
        val distancesToFood = getDistancesToFood(arena, headCoordinate, filteredDirections)
        val equivalentBestDirections = distancesToFood.values.stream()
                .findFirst()
                .orElse(SimpleDirectionContainer())
        LOG.info("Equivalent Best Directions: $equivalentBestDirections")
        return equivalentBestDirections
    }

    private fun getDistancesToFood(arena: Arena, headCoordinate: Coordinate, filteredDirections: SimpleDirectionContainer): Map<Int, SimpleDirectionContainer> {
        val distancesToFood: MutableMap<Int, SimpleDirectionContainer> = TreeMap()
        val foodCoordinate = arena.getFoodInNewList()[0].coordinate
        val maxCoordinate = arena.maxCoordinate
        filteredDirections.forEach {
            val nextCoordinate = arena.nextCoordinate(headCoordinate, it)
            val distanceToFood = nextCoordinate.minDistance(foodCoordinate, maxCoordinate)
            val directions = Optional.ofNullable(distancesToFood[distanceToFood]).orElse(SimpleDirectionContainer())
            directions.add(it)
            distancesToFood[distanceToFood] = directions
        }
        LOG.info("Distances To Food: $distancesToFood")
        return distancesToFood
    }

}
