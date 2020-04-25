package co.electric.snake

import co.electric.snake.controller.SnakeApplication
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder


@SpringBootApplication
class KotlinApplication : CommandLineRunner {

    override fun run(vararg args: String?) {
        SnakeApplication.start()
    }

}

fun main(args: Array<String>) {
    SpringApplicationBuilder(KotlinApplication::class.java)
        .headless(false)
        .run(*args)
}
