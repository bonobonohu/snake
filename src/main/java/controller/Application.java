package controller;

import model.ModifiableArena;
import model.ModifiableSnake;
import model.strategy.DefaultStrategy;
import model.strategy.bono.UltimateBonoStrategy;

public class Application
{

    public static void main(String[] args)
    {
        System.setSecurityManager(new SecurityManager());

        ModifiableArena arena = new ModifiableArena();

        ModifiableSnake snake2 = new ModifiableSnake(arena,
                new UltimateBonoStrategy(), "Ultimate Bono One");
        ModifiableSnake snake3 = new ModifiableSnake(arena,
                new DefaultStrategy(), "Default One");
        ModifiableSnake snake4 = new ModifiableSnake(arena,
                new UltimateBonoStrategy(), "Ultimate Bono Two");

        // arena.addSnake(snake1);
        arena.addSnake(snake2);
        arena.addSnake(snake4);

        new SnakeController(arena).start();
    }

}
