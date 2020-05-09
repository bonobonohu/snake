package co.electric.snake.strategy.bonostrategy.directionprocessor.filtereddirections.closeddirections

import co.electric.snake.framework.model.Arena
import co.electric.snake.framework.model.Direction
import co.electric.snake.framework.model.Snake
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer

class ClosedDirectionsProcessor(private val freeCoordinateCountsProcessor: FreeCoordinateCountsProcessor, private val closedDirectionsCollector: ClosedDirectionsCollector) {

    fun getDirections(snake: Snake, arena: Arena, freeDirections: SimpleDirectionContainer): SimpleDirectionContainer {
        val closedDirections = SimpleDirectionContainer()
        val freeCoordinateCountsByDirections = freeCoordinateCountsProcessor.getFreeCoordinateCountsByDirections(snake, arena, freeDirections)
        if (allCountsAreTheSame(freeCoordinateCountsByDirections)) {
            freeCoordinateCountsByDirections.clear()
        }
        closedDirections.addAll(closedDirectionsCollector.getDirections(freeCoordinateCountsByDirections))
        return closedDirections
    }

    private fun allCountsAreTheSame(freeCoordinateCountsByDirections: Map<Direction, Int>): Boolean {
        var allCountsAreTheSame = true
        val initialCount = -1
        var theCount = initialCount
        freeCoordinateCountsByDirections.values.forEach {
            if (theCount == initialCount) {
                theCount = it
            } else if (theCount != it) {
                allCountsAreTheSame = false
            }
        }
        return allCountsAreTheSame
    }

}
