package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.slf4j.LoggerFactory

class ByBlockingDistances : NewDirectionProcessor {

    companion object {
        private val LOG = LoggerFactory.getLogger(ByBlockingDistances::class.java)
    }

    override val order = 3

    override fun process(filteredDirections: SimpleDirectionContainer, equivalentBestDirections: SimpleDirectionContainer, blockingDirections: BlockingDirectionContainer): Direction? {
        var newDirection: Direction? = null
        val foundNewDirections = SimpleDirectionContainer()
        val orderedBlockings = blockingDirections.getBlockingsOrdered()
        var foundNewDirection = false
        val orderedBlockingsEntrySetIterator: Iterator<Map.Entry<Int, SimpleDirectionContainer>> = orderedBlockings.entries.iterator()
        while (!foundNewDirection && orderedBlockingsEntrySetIterator.hasNext()) {
            val blockingsTemp = orderedBlockingsEntrySetIterator.next()
            val blockingDirectionsTemp = blockingsTemp.value
            var numOfTries = 0
            var finalDirection: Direction?
            do {
                finalDirection = blockingDirectionsTemp.getRandomElement()
                if (finalDirection != null) {
                    foundNewDirections.add(finalDirection)
                    foundNewDirection = true
                }
                numOfTries++
            } while (
                    !foundNewDirection
                    && filteredDirections.contains(finalDirection)
                    && numOfTries < filteredDirections.size
            )
        }
        if (!foundNewDirections.isEmpty()) {
            newDirection = processFinalDirection(foundNewDirections, LOG)
        }
        return newDirection
    }

}
