package co.electric.snake.strategy.bonostrategy.directionprocessor.distanceprocessor

import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.*

internal class DistanceProcessorChainTest {

    companion object {

        private val DIRECTION = Direction.NORTH
        private val HEAD_COORDINATE = Coordinate(1, 1)
        private val BLOCKING_COORDINATE = Coordinate(1, 3)
        private val MAX_COORDINATE = Coordinate(50, 50)

    }

    private val chainItem = Mockito.mock(MockableChainItem::class.java)
    private val underTest = DistanceProcessorChain(setOf(chainItem))

    @Test
    fun testProcessShouldReturnDistanceFromChainWhenThereIsCorrespondingChainItem() {
        // GIVEN
        val expected = 10
        `when`(chainItem.getDistance(DIRECTION, HEAD_COORDINATE, BLOCKING_COORDINATE, MAX_COORDINATE))
                .thenReturn(Optional.of(10))
        // WHEN
        val actual = underTest.getDistance(DIRECTION, HEAD_COORDINATE, BLOCKING_COORDINATE, MAX_COORDINATE)
        // THEN
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun testProcessShouldReturnFallbackDistanceWhenChainGivenNoResults() {
        // GIVEN
        val expected = 0
        `when`(chainItem.getDistance(DIRECTION, HEAD_COORDINATE, BLOCKING_COORDINATE, MAX_COORDINATE))
                .thenReturn(Optional.empty())
        // WHEN
        val actual = underTest.getDistance(DIRECTION, HEAD_COORDINATE, BLOCKING_COORDINATE, MAX_COORDINATE)
        // THEN
        assertThat(actual).isEqualTo(expected)
    }

    open class MockableChainItem : DistanceProcessor {

        override fun getDistance(direction: Direction, headCoordinate: Coordinate, blockingCoordinate: Coordinate, maxCoordinate: Coordinate): Optional<Int> {
            return Optional.of(1)
        }

    }

}
