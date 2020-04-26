package co.electric.snake.model

import co.electric.snake.model.strategy.SnakeStrategy
import java.util.*

open class Snake(private val arena: ModifiableArena, private val strategy: SnakeStrategy, val name: String) : Member {

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

    protected fun moveTo(nextCoordinate: Coordinate) {
        if (!arena.isFood(nextCoordinate)) {
            bodyItems.removeLast()
        } else {
            arena.removeFood(nextCoordinate)
        }
        if (arena.isOccupied(nextCoordinate)) {
            println("Snake died: $name")
            throw SnakeDeadException()
        }
        bodyItems.addFirst(nextCoordinate)
    }

    fun getBodyItems(): List<Coordinate> {
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
