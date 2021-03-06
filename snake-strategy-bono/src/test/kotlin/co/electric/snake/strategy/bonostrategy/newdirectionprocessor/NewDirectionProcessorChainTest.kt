package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

internal class NewDirectionProcessorChainTest {

    companion object {
        private val FILTERED_DIRECTIONS = SimpleDirectionContainer()
        private val EQUIVALENT_BEST_DIRECTIONS = SimpleDirectionContainer()
        private val BLOCKING_DIRECTIONS = BlockingDirectionContainer()
    }

    private val firstChainItem = mock(NewDirectionProcessor::class.java)
    private val secondChainItem = mock(NewDirectionProcessor::class.java)
    private val thirdChainItem = mock(NewDirectionProcessor::class.java)
    private val underTest = NewDirectionProcessorChain(setOf(firstChainItem, secondChainItem, thirdChainItem))

    @BeforeEach
    fun beforeEach() {
        reset(firstChainItem, secondChainItem, thirdChainItem)
    }

    @Test
    fun testProcessShouldReturnFirstInOrderResultsFromChainWhenThereAreCorrespondingChainItems() {
        // GIVEN
        val expected = Direction.NORTH
        `when`(firstChainItem.process(FILTERED_DIRECTIONS, EQUIVALENT_BEST_DIRECTIONS, BLOCKING_DIRECTIONS))
                .thenReturn(Optional.empty())
        `when`(firstChainItem.order)
                .thenReturn(1)
        `when`(secondChainItem.process(FILTERED_DIRECTIONS, EQUIVALENT_BEST_DIRECTIONS, BLOCKING_DIRECTIONS))
                .thenReturn(Optional.of(Direction.NORTH))
        `when`(secondChainItem.order)
                .thenReturn(2)
        `when`(thirdChainItem.process(FILTERED_DIRECTIONS, EQUIVALENT_BEST_DIRECTIONS, BLOCKING_DIRECTIONS))
                .thenReturn(Optional.of(Direction.WEST))
        `when`(thirdChainItem.order)
                .thenReturn(3)
        // WHEN
        val actual = underTest.process(FILTERED_DIRECTIONS, EQUIVALENT_BEST_DIRECTIONS, BLOCKING_DIRECTIONS)
        // THEN
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun testProcessShouldReturnFallbackDirectionWhenChainGivenNoResults() {
        // GIVEN
        val expected = Direction.SOUTH
        `when`(firstChainItem.process(FILTERED_DIRECTIONS, EQUIVALENT_BEST_DIRECTIONS, BLOCKING_DIRECTIONS))
                .thenReturn(Optional.empty())
        `when`(firstChainItem.order)
                .thenReturn(1)
        `when`(secondChainItem.process(FILTERED_DIRECTIONS, EQUIVALENT_BEST_DIRECTIONS, BLOCKING_DIRECTIONS))
                .thenReturn(Optional.empty())
        `when`(secondChainItem.order)
                .thenReturn(2)
        `when`(thirdChainItem.process(FILTERED_DIRECTIONS, EQUIVALENT_BEST_DIRECTIONS, BLOCKING_DIRECTIONS))
                .thenReturn(Optional.empty())
        `when`(thirdChainItem.order)
                .thenReturn(3)
        // WHEN
        val actual = underTest.process(FILTERED_DIRECTIONS, EQUIVALENT_BEST_DIRECTIONS, BLOCKING_DIRECTIONS)
        // THEN
        assertThat(actual).isEqualTo(expected)
    }

}
