package co.electric.snake.strategy.bonostrategy.directionprocessor.closeddirections

import co.electric.snake.framework.model.Arena
import co.electric.snake.framework.model.Direction
import co.electric.snake.framework.model.Snake
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

internal class ClosedDirectionsProcessorTest {

    private val freeCoordinateCountsProcessor = mock(FreeCoordinateCountsProcessor::class.java)
    private val closedDirectionsCollector = mock(ClosedDirectionsCollector::class.java)
    private val underTest = ClosedDirectionsProcessor(freeCoordinateCountsProcessor, closedDirectionsCollector)
    private val snake = mock(Snake::class.java)
    private val arena = mock(Arena::class.java)

    @BeforeEach
    fun beforeEach() {
        reset(freeCoordinateCountsProcessor, closedDirectionsCollector)
    }

    @Test
    fun getDirectionsShouldReturnEmptyContainerWhenAllReturnedCoordinateCountsAreTheSame() {
        // GIVEN
        val expected = SimpleDirectionContainer()
        val freeDirections = SimpleDirectionContainer(setOf(Direction.NORTH, Direction.WEST))
        val freeCoordinateCountsByDirections = HashMap<Direction, Int>()
        freeCoordinateCountsByDirections[Direction.NORTH] = 100
        freeCoordinateCountsByDirections[Direction.WEST] = 100
        `when`(freeCoordinateCountsProcessor.getFreeCoordinateCountsByDirections(snake, arena, freeDirections))
                .thenReturn(freeCoordinateCountsByDirections)
        `when`(closedDirectionsCollector.getDirections(freeCoordinateCountsByDirections))
                .thenReturn(SimpleDirectionContainer())
        // WHEN
        val actual = underTest.getDirections(snake, arena, freeDirections)
        // THEN
        assertThat(actual).isEqualTo(expected)
        assertThat(freeCoordinateCountsByDirections).isEmpty()
    }

    @Test
    fun getDirectionsShouldReturnContainerWithSmallestCoordinateCountsWhenReturnedCoordinateCountsAreNotTheSame() {
        // GIVEN
        val expected = SimpleDirectionContainer(setOf(Direction.NORTH))
        val freeDirections = SimpleDirectionContainer(setOf(Direction.NORTH, Direction.WEST))
        val freeCoordinateCountsByDirections = HashMap<Direction, Int>()
        freeCoordinateCountsByDirections[Direction.NORTH] = 10
        freeCoordinateCountsByDirections[Direction.WEST] = 100
        `when`(freeCoordinateCountsProcessor.getFreeCoordinateCountsByDirections(snake, arena, freeDirections))
                .thenReturn(freeCoordinateCountsByDirections)
        `when`(closedDirectionsCollector.getDirections(freeCoordinateCountsByDirections))
                .thenReturn(SimpleDirectionContainer(setOf(Direction.NORTH)))
        // WHEN
        val actual = underTest.getDirections(snake, arena, freeDirections)
        // THEN
        assertThat(actual).isEqualTo(expected)
        assertThat(freeCoordinateCountsByDirections).containsOnlyKeys(Direction.NORTH, Direction.WEST)
    }

}
