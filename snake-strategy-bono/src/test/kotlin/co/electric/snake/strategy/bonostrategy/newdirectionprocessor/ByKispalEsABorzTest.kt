package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

internal class ByKispalEsABorzTest {

    private val underTest = ByKispalEsABorz()

    @Test
    fun testProcessShouldReturnSouthDirectionOptionalWhenCalled() {
        // GIVEN
        val expected = Optional.of(Direction.SOUTH)
        val filteredDirections = SimpleDirectionContainer()
        val equivalentBestDirections = SimpleDirectionContainer()
        val blockingDirections = BlockingDirectionContainer()
        // WHEN
        val actual = underTest.process(filteredDirections, equivalentBestDirections, blockingDirections)
        // THEN
        assertThat(actual).isEqualTo(expected)
    }

}
