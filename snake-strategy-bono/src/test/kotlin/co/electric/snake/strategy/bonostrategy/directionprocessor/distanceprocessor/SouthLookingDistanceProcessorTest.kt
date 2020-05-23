package co.electric.snake.strategy.bonostrategy.directionprocessor.distanceprocessor

import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

internal class SouthLookingDistanceProcessorTest {

    companion object {
        private val MAX_COORDINATE = Coordinate(50, 50)
    }

    private val underTest = SouthLookingDistanceProcessor()

    @Test
    fun getDistanceShouldReturnEmptyOptionalWhenDirectionIsNotSouth() {
        // GIVEN
        val expected = Optional.empty<Int>()
        // WHEN
        val actual = underTest.getDistance(Direction.NORTH, Coordinate(1, 11), Coordinate(1, 1), MAX_COORDINATE)
        // THEN
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun getDistanceShouldReturnCorrectResultWhenDistanceProcessedDirectly() {
        // GIVEN
        val expected = Optional.of(10)
        // WHEN
        val actual = underTest.getDistance(Direction.SOUTH, Coordinate(1, 11), Coordinate(1, 1), MAX_COORDINATE)
        // THEN
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun getDistanceShouldReturnCorrectResultWhenDistanceProcessedClipped() {
        // GIVEN
        val expected = Optional.of(10)
        // WHEN
        val actual = underTest.getDistance(Direction.SOUTH, Coordinate(1, 1), Coordinate(1, 41), MAX_COORDINATE)
        // THEN
        assertThat(actual).isEqualTo(expected)
    }

}
