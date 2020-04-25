package co.electric.snake.controller;

import co.electric.snake.model.ModifiableArena;
import co.electric.snake.model.ModifiableSnake;
import co.electric.snake.model.strategy.bono.BonoStrategy;

public class Application {

    public static void main(String[] args) {
        System.setSecurityManager(new SecurityManager());

        ModifiableArena arena = new ModifiableArena();

        ModifiableSnake snake1 = new ModifiableSnake(arena, new BonoStrategy(), "Jack");
        ModifiableSnake snake2 = new ModifiableSnake(arena, new BonoStrategy(), "Jill");

        arena.addSnake(snake1);
        arena.addSnake(snake2);

        new SnakeController(arena).start();
    }

}
