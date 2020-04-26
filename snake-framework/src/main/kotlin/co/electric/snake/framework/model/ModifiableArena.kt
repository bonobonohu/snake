package co.electric.snake.framework.model

class ModifiableArena : Arena() {

    fun addSnake(snake: ModifiableSnake) {
        snakes.add(snake)
    }

    fun move() {
        logResultsIfNeeded()
        snakes.stream().forEach(ModifiableSnake::move)
        round++
    }

    fun removeFood(nextCoordinate: Coordinate) {
        removeFoodFromCollection(nextCoordinate)
        generateNewFood()
    }

}
