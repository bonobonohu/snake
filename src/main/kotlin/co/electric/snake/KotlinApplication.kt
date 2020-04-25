package co.electric.snake

import co.electric.snake.controller.SnakeController
import co.electric.snake.model.ModifiableArena
import co.electric.snake.model.ModifiableSnake
import co.electric.snake.model.strategy.bono.BonoStrategy
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
class KotlinApplication : CommandLineRunner {

    override fun run(vararg args: String?) {
        val arena = ModifiableArena()
        val jack = ModifiableSnake(arena, BonoStrategy(), "Jack")
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
