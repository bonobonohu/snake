package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ByFilteredDirectionsTest {

    private val underTest = ByFilteredDirections()

    @Test
    fun testProcessShouldReturnRandomElementFromFilteredDirectionsWhenItContainsMultipleElements() {
        // GIVEN
        val expecteds = setOf(Direction.SOUTH, Direction.WEST)
        val filteredDirections = SimpleDirectionContainer(expecteds)
        val equivalentBestDirections = SimpleDirectionContainer()
        val blockingDirections = BlockingDirectionContainer()
        // WHEN
        val actual = underTest.process(filteredDirections, equivalentBestDirections, blockingDirections)
        // THEN
        assertThat(actual.get()).isIn(expecteds)
    }

    @Test
    fun testProcessShouldReturnTheContainedElementFromFilteredDirectionsWhenItContainsExactlyOneSingleElement() {
        // GIVEN
        val expected = Direction.WEST
        val filteredDirections = SimpleDirectionContainer(setOf(Direction.WEST))
        val equivalentBestDirections = SimpleDirectionContainer()
        val blockingDirections = BlockingDirectionContainer()
        // WHEN
        val actual = underTest.process(filteredDirections, equivalentBestDirections, blockingDirections)
        // THEN
        assertThat(actual.get()).isEqualTo(expected)
    }

}
