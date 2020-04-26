package co.electric.snake.framework.model

import co.electric.snake.framework.strategy.SnakeStrategy
import org.slf4j.LoggerFactory
import java.util.*

open class Snake(private val arena: ModifiableArena, private val strategy: SnakeStrategy, val name: String) : Member {

    companion object {
        private val LOG = LoggerFactory.getLogger(Snake::class.java)

        private const val SNAKE_DIED_LOG_MESSAGE = "Snake died: {}"
    }

    private val bodyItems: Deque<Coordinate> = LinkedList()

    init {
        bodyItems.add(arena.generateRandomFreeCoordinate())
    }

    override fun occupies(coordinate: Coordinate): Boolean {
        return bodyItems.contains(coordinate)
    }

    protected fun decideNextCoordinate(): Coordinate {
        val direction = strategy.nextMove(this, arena)
        return arena.nextCoordinate(bodyItems.first, direction)
    }

    protected fun moveTo(coordinate: Coordinate) {
        if (!arena.isFood(coordinate)) {
            bodyItems.removeLast()
        } else {
            arena.removeFood(coordinate)
        }
        if (arena.isOccupied(coordinate)) {
            LOG.info(SNAKE_DIED_LOG_MESSAGE, name)
            throw SnakeDeadException()
        }
        bodyItems.addFirst(coordinate)
    }

    fun getBodyItemsInNewList(): List<Coordinate> {
        return ArrayList(bodyItems)
    }

    fun getHeadCoordinate(): Coordinate {
        return bodyItems.first
    }

    fun getTailCoordinate(): Coordinate {
        return bodyItems.last
    }

    fun getLength(): Int {
        return bodyItems.size
    }

    override fun toString(): String {
        return "Snake [bodyItems=$bodyItems, strategy=$strategy]"
    }

}
