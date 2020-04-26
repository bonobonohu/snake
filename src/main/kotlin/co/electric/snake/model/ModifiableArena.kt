package co.electric.snake.model

class ModifiableArena : Arena() {

    fun addSnake(snake: ModifiableSnake) {
        snakes.add(snake)
    }

    fun move() {
        printResultsIfNeeded()
        snakes.stream().forEach(ModifiableSnake::move)
        round++
    }

    fun removeFood(nextCoordinate: Coordinate) {
        removeFoodFromCollection(nextCoordinate)
        generateNewFood()
    }

}
