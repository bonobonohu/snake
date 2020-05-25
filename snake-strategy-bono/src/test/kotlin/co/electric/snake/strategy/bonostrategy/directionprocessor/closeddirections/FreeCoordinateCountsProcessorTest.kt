package co.electric.snake.strategy.bonostrategy.directionprocessor.closeddirections

import co.electric.snake.framework.model.Coordinate
import co.electric.snake.framework.model.Direction
import co.electric.snake.framework.model.ModifiableArena
import co.electric.snake.framework.model.TestSnake
import co.electric.snake.framework.strategy.SnakeStrategy
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

internal class FreeCoordinateCountsProcessorTest {

    companion object {
        private const val BLOCKER_SNAKE_NAME = "Blocker Snake"
        private const val SNAKE_UNDER_TEST_NAME = "Snake Under Test"
    }

    private val underTest = FreeCoordinateCountsProcessor()

    @Test
    fun testGetFreeCoordinateCountsByDirections() {
        // GIVEN
        val expected = HashMap<Direction, Int>()
        expected[Direction.SOUTH] = 5
        expected[Direction.WEST] = 2478
        expected[Direction.NORTH] = 2478
        val arena = ModifiableArena(true)
        val snakeStrategy = mock(SnakeStrategy::class.java)
        val blockerSnake = TestSnake(arena, snakeStrategy, BLOCKER_SNAKE_NAME)
                .addToBody(Coordinate(5, 10))
                .addToBody(Coordinate(6, 10))
                .addToBody(Coordinate(6, 9))
                .addToBody(Coordinate(6, 8))
                .addToBody(Coordinate(6, 7))
                .addToBody(Coordinate(6, 6))
                .addToBody(Coordinate(6, 5))
                .addToBody(Coordinate(7, 5))
                .addToBody(Coordinate(8, 5))
                .addToBody(Coordinate(8, 6))
                .addToBody(Coordinate(8, 7))
                .addToBody(Coordinate(8, 8))
                .addToBody(Coordinate(8, 9))
                .addToBody(Coordinate(8, 10))
                .addToBody(Coordinate(9, 10))
        arena.addSnake(blockerSnake)
        val snakeUnderTest = TestSnake(arena, snakeStrategy, SNAKE_UNDER_TEST_NAME)
                .addToBody(Coordinate(7, 11))
                .addToBody(Coordinate(8, 11))
        arena.addSnake(snakeUnderTest)
        val freeDirections = SimpleDirectionContainer(setOf(Direction.SOUTH, Direction.WEST, Direction.NORTH))
        // WHEN
        val actual = underTest.getFreeCoordinateCountsByDirections(snakeUnderTest, arena, freeDirections)
        // THEN
        assertThat(actual).containsExactlyInAnyOrderEntriesOf(expected)
    }

}
