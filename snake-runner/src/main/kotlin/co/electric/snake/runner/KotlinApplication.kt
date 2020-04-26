package co.electric.snake.runner

import co.electric.snake.framework.controller.SnakeController
import co.electric.snake.framework.model.ModifiableArena
import co.electric.snake.framework.model.ModifiableSnake
import co.electric.snake.strategy.bonostrategy.BonoStrategy
import co.electric.snake.strategy.defaultstrategy.DefaultStrategy
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
class KotlinApplication : CommandLineRunner {

    override fun run(vararg args: String?) {
        val arena = ModifiableArena()
        val jack = ModifiableSnake(arena, DefaultStrategy(), "Stone Jack")
        val jill = ModifiableSnake(arena, BonoStrategy(), "Jill")
        arena.addSnake(jack)
        arena.addSnake(jill)
        SnakeController(arena).start()
    }

}

fun main(args: Array<String>) {
    SpringApplicationBuilder(KotlinApplication::class.java)
            .headless(false)
            .run(*args)
}
