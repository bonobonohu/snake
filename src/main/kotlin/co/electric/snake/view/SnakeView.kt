package co.electric.snake.view

import co.electric.snake.model.Snake
import java.awt.Color
import java.awt.Graphics

class SnakeView(val snake: Snake, private val color: Color) {

    fun draw(graphics: Graphics) {
        graphics.color = color
        var first = true
        snake.getBodyItemsInNewList().stream().forEach { bodyItem ->
            val x = ArenaView.PADDING + 1 + ArenaView.POINT_SIZE * bodyItem.x
            val y = ArenaView.PADDING + 1 + ArenaView.POINT_SIZE * bodyItem.y
            if (first) {
                graphics.color = Color.BLACK
            } else {
                graphics.color = color
            }
            graphics.fillRect(x, y, ArenaView.POINT_SIZE, ArenaView.POINT_SIZE)
            first = false
        }
    }

}
