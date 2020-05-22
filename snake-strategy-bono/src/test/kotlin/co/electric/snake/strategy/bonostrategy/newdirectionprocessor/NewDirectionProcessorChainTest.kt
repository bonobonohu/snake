package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import co.electric.snake.framework.model.Direction
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.*

internal class NewDirectionProcessorChainTest {

    companion object {

        private val FILTERED_DIRECTIONS = SimpleDirectionContainer()
        private val EQUIVALENT_BEST_DIRECTIONS = SimpleDirectionContainer()
        private val BLOCKING_DIRECTIONS = BlockingDirectionContainer()

    }

    private val firstChainItem = Mockito.mock(ChainItem::class.java)
    private val secondChainItem = Mockito.mock(ChainItem::class.java)
    private val thirdChainItem = Mockito.mock(ChainItem::class.java)
    private val underTest = NewDirectionProcessorChain(setOf(firstChainItem, secondChainItem, thirdChainItem))

    @Test
    fun testProcessShouldReturnFirstInOrderResultsFromChain() {
        // GIVEN
        val expected = Direction.NORTH
        `when`(firstChainItem.process(FILTERED_DIRECTIONS, EQUIVALENT_BEST_DIRECTIONS, BLOCKING_DIRECTIONS))
                .thenReturn(Optional.empty())
        `when`(firstChainItem.order).thenReturn(1)
        `when`(secondChainItem.process(FILTERED_DIRECTIONS, EQUIVALENT_BEST_DIRECTIONS, BLOCKING_DIRECTIONS))
                .thenReturn(Optional.of(Direction.NORTH))
        `when`(secondChainItem.order).thenReturn(2)
        `when`(thirdChainItem.process(FILTERED_DIRECTIONS, EQUIVALENT_BEST_DIRECTIONS, BLOCKING_DIRECTIONS))
                .thenReturn(Optional.of(Direction.WEST))
        `when`(thirdChainItem.order).thenReturn(3)
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
        `when`(firstChainItem.order).thenReturn(1)
        `when`(secondChainItem.process(FILTERED_DIRECTIONS, EQUIVALENT_BEST_DIRECTIONS, BLOCKING_DIRECTIONS))
                .thenReturn(Optional.empty())
        `when`(secondChainItem.order).thenReturn(2)
        `when`(thirdChainItem.process(FILTERED_DIRECTIONS, EQUIVALENT_BEST_DIRECTIONS, BLOCKING_DIRECTIONS))
                .thenReturn(Optional.empty())
        `when`(thirdChainItem.order).thenReturn(3)
        // WHEN
        val actual = underTest.process(FILTERED_DIRECTIONS, EQUIVALENT_BEST_DIRECTIONS, BLOCKING_DIRECTIONS)
        // THEN
        assertThat(actual).isEqualTo(expected)
    }

    open class ChainItem : NewDirectionProcessor {

        override val order: Int
            get() = 0

        override fun process(filteredDirections: SimpleDirectionContainer, equivalentBestDirections: SimpleDirectionContainer, blockingDirections: BlockingDirectionContainer): Optional<Direction> {
            return Optional.of(Direction.EAST)
        }

    }

}
