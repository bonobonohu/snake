package controller;

import model.ModifiableArena;
import model.ModifiableSnake;
import model.strategy.BonoStrategy;
import model.strategy.DefaultStrategy;
import model.strategy.bono.UltimateKillerBonoStrategy;

public class Application
{

    public static void main(String[] args)
    {
        // List<Coordinate> coordinates = snake.getBodyItems();
        // Coordinate coordinate = snake.getHeadCoordinate();
        // String name = snake.getName();
        // Coordinate coordinate = snake.getTailCoordinate();
        // int length = snake.length();
        // boolean occupies = snake.occupies(nextCoordinate);

        // Coordinate coordinate = arena.generateRandomFreeCoordinate();
        // List<Food> foods = arena.getFood();
        // Coordinate coordinate = arena.getMaxCoordinate();
        // List<Snake> snakes = arena.getSnakes();
        // boolean isFood = arena.isFood(nextCoordinate);
        // boolean isOccupied = arena.isOccupied(nextCoordinate);
        // Coordinate coordinate = arena.nextCoordinate(coordinate, direction);

        // int x = coordinate.getX();
        // int y = coordinate.getY();
        // int distance = coordinate.minDistance(otherCoordinate,
        // maxCoordinate);
        // Coordinate coordinate = coordinate.nextCoordinate(direction);
        // int distance = coordinate.standardDistance(otherCoordinate);
        // Coordinate coordinate = coordinate.truncLimits(maxCoordinates);

        // Coordinate coordinate = food.getCoordinate();
        // boolean occupies = food.occupies(coordinate);

        System.setSecurityManager(new SecurityManager());

        ModifiableArena arena = new ModifiableArena();

        ModifiableSnake snake1 = new ModifiableSnake(arena, new BonoStrategy(),
                "Bono One");
        ModifiableSnake snake2 = new ModifiableSnake(arena,
                new UltimateKillerBonoStrategy(), "Ultimate Bono One");
        ModifiableSnake snake3 = new ModifiableSnake(arena,
                new DefaultStrategy(), "Default One");
        ModifiableSnake snake4 = new ModifiableSnake(arena,
                new UltimateKillerBonoStrategy(), "Ultimate Bono Two");

        // arena.addSnake(snake1);
        arena.addSnake(snake2);
        arena.addSnake(snake4);

        new SnakeController(arena).start();
    }

}
