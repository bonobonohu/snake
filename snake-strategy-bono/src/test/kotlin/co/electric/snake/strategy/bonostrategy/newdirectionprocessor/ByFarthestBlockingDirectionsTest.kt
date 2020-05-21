package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class ByFarthestBlockingDirectionsTest {

    private val underTest = ByFarthestBlockingDirections()

    @Test
    fun testProcessShouldReturnARandomFarthestBlockingDirectionWhenThereAreMultipleFarthestElements() {
        // GIVEN
        val expecteds = setOf(Direction.SOUTH, Direction.WEST)
        val filteredDirections = SimpleDirectionContainer()
        val equivalentBestDirections = SimpleDirectionContainer()
        val blockingDirections = BlockingDirectionContainer()
        blockingDirections.putData(Direction.NORTH, Coordinate(12, 3), 2)
        blockingDirections.putData(Direction.SOUTH, Coordinate(12, 40), 11)
        blockingDirections.putData(Direction.WEST, Coordinate(1, 1), 11)
        blockingDirections.putData(Direction.EAST, Coordinate(14, 1), 2)
        // WHEN
        val actual = underTest.process(filteredDirections, equivalentBestDirections, blockingDirections)
        // THEN
        Assertions.assertThat(actual.get()).isIn(expecteds)
    }

    @Test
    fun testProcessShouldReturnTheOnlyFarthestBlockingDirectionWhenThereIsOnlyOneFarthestElement() {
        // GIVEN
        val expected = Direction.WEST
        val filteredDirections = SimpleDirectionContainer()
        val equivalentBestDirections = SimpleDirectionContainer()
        val blockingDirections = BlockingDirectionContainer()
        blockingDirections.putData(Direction.NORTH, Coordinate(12, 3), 2)
        blockingDirections.putData(Direction.SOUTH, Coordinate(12, 49), 2)
        blockingDirections.putData(Direction.WEST, Coordinate(1, 1), 11)
        blockingDirections.putData(Direction.EAST, Coordinate(14, 1), 2)
        // WHEN
        val actual = underTest.process(filteredDirections, equivalentBestDirections, blockingDirections)
        // THEN
        Assertions.assertThat(actual.get()).isEqualTo(expected)
    }

}
