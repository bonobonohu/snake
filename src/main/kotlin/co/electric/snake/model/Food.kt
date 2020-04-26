package co.electric.snake.model

class Food(val coordinate: Coordinate) : Member {

    override fun occupies(coordinate: Coordinate): Boolean {
        return this.coordinate == coordinate
    }

    override fun toString(): String {
        return "Food [coordinate=$coordinate]"
    }

}
