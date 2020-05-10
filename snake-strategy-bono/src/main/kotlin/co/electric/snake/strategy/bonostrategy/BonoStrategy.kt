package co.electric.snake.strategy.bonostrategy

import co.electric.snake.framework.model.Arena
import co.electric.snake.framework.model.Direction
import co.electric.snake.framework.model.Snake
import co.electric.snake.framework.strategy.SnakeStrategy
import co.electric.snake.strategy.bonostrategy.directionprocessor.BlockingDirectionsProcessor
import co.electric.snake.strategy.bonostrategy.directionprocessor.EquivalentBestDirectionsProcessor
import co.electric.snake.strategy.bonostrategy.directionprocessor.FilteredDirectionsProcessor
import co.electric.snake.strategy.bonostrategy.directionprocessor.FreeDirectionsProcessor
import co.electric.snake.strategy.bonostrategy.directionprocessor.closeddirections.ClosedDirectionsProcessor
import co.electric.snake.strategy.bonostrategy.newdirectionprocessor.NewDirectionProcessorChain
import org.slf4j.LoggerFactory

class BonoStrategy(private val freeDirectionsProcessor: FreeDirectionsProcessor, private val closedDirectionsProcessor: ClosedDirectionsProcessor, private val filteredDirectionsProcessor: FilteredDirectionsProcessor, private val equivalentBestDirectionsProcessor: EquivalentBestDirectionsProcessor, private val blockingDirectionsProcessor: BlockingDirectionsProcessor, private val newDirectionProcessorChain: NewDirectionProcessorChain) : SnakeStrategy {

    companion object {
        private val LOG = LoggerFactory.getLogger(BonoStrategy::class.java)
    }

    override fun nextMove(snake: Snake, arena: Arena): Direction {
        LOG.info("--- BEGIN ${snake.name} ---")
        LOG.info("Head: ${snake.getHeadCoordinate()}")
        LOG.info("Length: ${snake.getLength()}")
        LOG.info("Food: ${arena.getFoodInNewList()[0].coordinate}")
        val newDirection = process(snake, arena)
        LOG.info("--- END ${snake.name} ---")
        return newDirection
    }

    private fun process(snake: Snake, arena: Arena): Direction {
        val freeDirections = freeDirectionsProcessor.getDirections(snake, arena)
        val closedDirections = closedDirectionsProcessor.getDirections(snake, arena, freeDirections)
        val filteredDirections = filteredDirectionsProcessor.getDirections(freeDirections, closedDirections)
        val equivalentBestDirections = equivalentBestDirectionsProcessor.getDirections(snake, arena, filteredDirections)
        val blockingDirections = blockingDirectionsProcessor.getDirections(snake, arena, filteredDirections)
        return newDirectionProcessorChain.process(filteredDirections, equivalentBestDirections, blockingDirections)
    }

}
