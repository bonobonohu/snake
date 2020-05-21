package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class ByFreeFilteredDirectionsTest {

    private val underTest = ByFreeFilteredDirections()

    @Test
    fun testProcessShouldReturnARandomFreeFilteredDirectionWhenThereAreMultipleFreeFilteredElements() {
        // GIVEN
        val expecteds = setOf(Direction.NORTH, Direction.SOUTH)
        val filteredDirections = SimpleDirectionContainer(setOf(Direction.NORTH, Direction.SOUTH, Direction.WEST))
        val equivalentBestDirections = SimpleDirectionContainer(expecteds)
        val blockingDirections = BlockingDirectionContainer()
        blockingDirections.putData(Direction.WEST, Coordinate(1, 1), 11)
        // WHEN
        val actual = underTest.process(filteredDirections, equivalentBestDirections, blockingDirections)
        // THEN
        Assertions.assertThat(actual.get()).isIn(expecteds)
    }

    @Test
    fun testProcessShouldReturnTheOnlyFreeFilteredDirectionWhenThereIsOnlyOneFreeFilteredElement() {
        // GIVEN
        val expected = Direction.NORTH
        val filteredDirections = SimpleDirectionContainer(setOf(Direction.NORTH, Direction.SOUTH, Direction.WEST))
        val equivalentBestDirections = SimpleDirectionContainer()
        val blockingDirections = BlockingDirectionContainer()
        blockingDirections.putData(Direction.SOUTH, Coordinate(12, 49), 2)
        blockingDirections.putData(Direction.WEST, Coordinate(1, 1), 11)
        // WHEN
        val actual = underTest.process(filteredDirections, equivalentBestDirections, blockingDirections)
        // THEN
        Assertions.assertThat(actual.get()).isEqualTo(expected)
    }

}
