package co.electric.snake.controller

import co.electric.snake.model.Arena
import co.electric.snake.model.ModifiableArena
import co.electric.snake.view.ArenaView
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.Timer

class SnakeController(private val arena: ModifiableArena) {

    companion object {
        private const val WIDTH = 550
        private const val HEIGHT = 570
        private const val TITLE = "Snake"
        private const val DELAY_BETWEEN_STEPS = 1

        private val FRAME_SIZE = Dimension(WIDTH, HEIGHT)
    }

    private val frame = JFrame(TITLE)
    private val timer = Timer(DELAY_BETWEEN_STEPS, TimerAction(this))

    private lateinit var arenaView: ArenaView

    init {
        initArenaView(arena)
        frame.isVisible = true
    }

    fun start() {
        timer.start()
    }

    fun stop() {
        timer.stop()
        arenaView.repaint()
    }

    fun step() {
        arena.move()
        arenaView.repaint()
    }

    private fun initArenaView(arena: Arena) {
        arenaView = ArenaView(arena)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.add(arenaView)
        frame.size = FRAME_SIZE
        frame.setLocationRelativeTo(null)
    }

}
