package co.electric.snake.strategy.bonostrategy.directionprocessor.distanceprocessor

import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

internal class EastLookingDistanceProcessorTest {

    companion object {
        private val MAX_COORDINATE = Coordinate(50, 50)
    }

    private val underTest = EastLookingDistanceProcessor()

    @Test
    fun getDistanceShouldReturnEmptyOptionalWhenDirectionIsNotEast() {
        // GIVEN
        val expected = Optional.empty<Int>()
        // WHEN
        val actual = underTest.getDistance(Direction.WEST, Coordinate(1, 1), Coordinate(11, 1), MAX_COORDINATE)
        // THEN
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun getDistanceShouldReturnCorrectResultWhenDistanceProcessedDirectly() {
        // GIVEN
        val expected = Optional.of(10)
        // WHEN
        val actual = underTest.getDistance(Direction.EAST, Coordinate(1, 1), Coordinate(11, 1), MAX_COORDINATE)
        // THEN
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun getDistanceShouldReturnCorrectResultWhenDistanceProcessedClipped() {
        // GIVEN
        val expected = Optional.of(10)
        // WHEN
        val actual = underTest.getDistance(Direction.EAST, Coordinate(41, 1), Coordinate(1, 1), MAX_COORDINATE)
        // THEN
        assertThat(actual).isEqualTo(expected)
    }

}
