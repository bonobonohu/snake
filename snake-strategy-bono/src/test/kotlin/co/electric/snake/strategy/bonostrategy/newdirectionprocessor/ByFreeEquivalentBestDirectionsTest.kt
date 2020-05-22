package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ByFreeEquivalentBestDirectionsTest {

    private val underTest = ByFreeEquivalentBestDirections()

    @Test
    fun testProcessShouldReturnARandomFreeEquivalentBestDirectionWhenThereAreMultipleFreeEquivalentBestElements() {
        // GIVEN
        val expecteds = setOf(Direction.NORTH, Direction.SOUTH)
        val filteredDirections = SimpleDirectionContainer()
        val equivalentBestDirections = SimpleDirectionContainer(setOf(Direction.NORTH, Direction.SOUTH, Direction.WEST))
        val blockingDirections = BlockingDirectionContainer()
        blockingDirections.putData(Direction.WEST, Coordinate(1, 1), 11)
        // WHEN
        val actual = underTest.process(filteredDirections, equivalentBestDirections, blockingDirections)
        // THEN
        assertThat(actual.get()).isIn(expecteds)
    }

    @Test
    fun testProcessShouldReturnTheOnlyFreeEquivalentBestDirectionWhenThereIsOnlyOneFreeEquivalentBestElement() {
        // GIVEN
        val expected = Direction.NORTH
        val filteredDirections = SimpleDirectionContainer()
        val equivalentBestDirections = SimpleDirectionContainer(setOf(Direction.NORTH, Direction.SOUTH, Direction.WEST))
        val blockingDirections = BlockingDirectionContainer()
        blockingDirections.putData(Direction.SOUTH, Coordinate(12, 49), 2)
        blockingDirections.putData(Direction.WEST, Coordinate(1, 1), 11)
        // WHEN
        val actual = underTest.process(filteredDirections, equivalentBestDirections, blockingDirections)
        // THEN
        assertThat(actual.get()).isEqualTo(expected)
    }

}
