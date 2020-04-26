package co.electric.snake.controller

import co.electric.snake.model.SnakeDeadException
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class TimerAction(private val snakeController: SnakeController) : ActionListener {

    private var stop = false

    override fun actionPerformed(event: ActionEvent) {
        try {
            if (!stop) {
                snakeController.step()
            }
        } catch (exception: SnakeDeadException) {
            stop = true
            snakeController.stop()
        }
    }

}
