package co.electric.snake.strategy.bonostrategy.directionprocessor

import co.electric.snake.framework.model.Arena
import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import co.electric.snake.framework.model.Snake
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

internal class FreeDirectionsProcessorTest {

    companion object {
        private val HEAD_COORDINATE = Coordinate(1, 1)
        private val NEXT_COORDINATE_AT_NORTH = Coordinate(1, 2)
        private val NEXT_COORDINATE_AT_SOUTH = Coordinate(1, 50)
        private val NEXT_COORDINATE_AT_EAST = Coordinate(2, 1)
        private val NEXT_COORDINATE_AT_WEST = Coordinate(50, 1)
    }

    private val underTest = FreeDirectionsProcessor()
    private val snake = mock(Snake::class.java)
    private val arena = mock(Arena::class.java)

    @BeforeEach
    fun beforeEach() {
        reset(snake, arena)
    }

    @Test
    fun testGetDirectionsShouldReturnOnlyFreeDirections() {
        // GIVEN
        val expected = SimpleDirectionContainer(setOf(Direction.NORTH, Direction.WEST))
        `when`(snake.getHeadCoordinate())
                .thenReturn(HEAD_COORDINATE)
        `when`(arena.nextCoordinate(HEAD_COORDINATE, Direction.NORTH))
                .thenReturn(NEXT_COORDINATE_AT_NORTH)
        `when`(arena.nextCoordinate(HEAD_COORDINATE, Direction.SOUTH))
                .thenReturn(NEXT_COORDINATE_AT_SOUTH)
        `when`(arena.nextCoordinate(HEAD_COORDINATE, Direction.EAST))
                .thenReturn(NEXT_COORDINATE_AT_EAST)
        `when`(arena.nextCoordinate(HEAD_COORDINATE, Direction.WEST))
                .thenReturn(NEXT_COORDINATE_AT_WEST)
        `when`(arena.isOccupied(NEXT_COORDINATE_AT_NORTH))
                .thenReturn(false)
        `when`(arena.isOccupied(NEXT_COORDINATE_AT_SOUTH))
                .thenReturn(true)
        `when`(arena.isOccupied(NEXT_COORDINATE_AT_EAST))
                .thenReturn(true)
        `when`(arena.isOccupied(NEXT_COORDINATE_AT_WEST))
                .thenReturn(false)
        // WHEN
        val actual = underTest.getDirections(snake, arena)
        // THEN
        Assertions.assertThat(actual).containsOnlyElementsOf(expected)
    }

}
