package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class ByEquivalentBestDirectionsTest {

    private val underTest = ByEquivalentBestDirections()

    @Test
    fun testProcessShouldReturnRandomElementFromEquivalentBestDirectionsWhenItContainsMultipleElements() {
        // GIVEN
        val expecteds = setOf(Direction.SOUTH, Direction.WEST)
        val filteredDirections = SimpleDirectionContainer()
        val equivalentBestDirections = SimpleDirectionContainer(expecteds)
        val blockingDirections = BlockingDirectionContainer()
        // WHEN
        val actual = underTest.process(filteredDirections, equivalentBestDirections, blockingDirections)
        // THEN
        Assertions.assertThat(actual.get()).isIn(expecteds)
    }

    @Test
    fun testProcessShouldReturnTheContainedElementFromEquivalentBestDirectionsWhenItContainsExactlyOneSingleElement() {
        // GIVEN
        val expected = Direction.WEST
        val filteredDirections = SimpleDirectionContainer()
        val equivalentBestDirections = SimpleDirectionContainer(setOf(Direction.WEST))
        val blockingDirections = BlockingDirectionContainer()
        // WHEN
        val actual = underTest.process(filteredDirections, equivalentBestDirections, blockingDirections)
        // THEN
        Assertions.assertThat(actual.get()).isEqualTo(expected)
    }

}
