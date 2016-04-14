package model.strategy.bono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.Arena;
import model.Coordinate;
import model.Direction;
import model.Snake;
import model.strategy.SnakeStrategy;
import model.strategy.bono.directionhandlers.BlockingDirectionContainer;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;
import model.strategy.bono.newdirectionprocessors.DependencyProvider;
import model.strategy.bono.newdirectionprocessors.NewDirectionProcessor;

public class BonoStrategy implements SnakeStrategy
{
    private Arena arena;
    private Snake snake;

    private Coordinate actualHeadCoordinate;
    private Coordinate foodCoordinate;
    private Coordinate maxCoordinate;

    @Override
    public Direction nextMove(Snake snakeArgument, Arena arenaArgument)
    {
        Direction newDirection = null;

        arena = arenaArgument;
        snake = snakeArgument;

        System.out.println("--- BEGIN " + snake.getName() + "---");

        actualHeadCoordinate = snake.getHeadCoordinate();
        foodCoordinate = arena.getFood().get(0).getCoordinate();
        maxCoordinate = arena.getMaxCoordinate();

        System.out.println("Food: " + arena.getFood().get(0).getCoordinate());
        System.out.println("Head: " + snake.getHeadCoordinate());

        newDirection = process();

        System.out.println("The processed Direction: " + newDirection);

        System.out.println("--- END " + snake.getName() + "---");

        return newDirection;
    }

    private Direction process()
    {
        Direction newDirection = null;

        Map<Integer, SimpleDirectionContainer<Direction>> distancesToFood = new TreeMap<>();

        BlockingDirectionProcessor blockingDirectionProcessor = new BlockingDirectionProcessor(
                snake, arena);
        BlockingDirectionContainer blockingDirections = new BlockingDirectionContainer();

        SimpleDirectionContainer<Direction> equivalentBestDirections = new SimpleDirectionContainer<>();
        SimpleDirectionContainer<Direction> allValidDirections = new SimpleDirectionContainer<>();

        blockingDirections = blockingDirectionProcessor
                .process(actualHeadCoordinate);

        distancesToFood = processDistancesToFood();

        equivalentBestDirections = processEquivalentBestDirections(
                distancesToFood);
        allValidDirections = processAllValidDirections(distancesToFood);

        newDirection = processNewDirection(blockingDirections,
                equivalentBestDirections, allValidDirections);

        return newDirection;
    }

    private Map<Integer, SimpleDirectionContainer<Direction>> processDistancesToFood()
    {
        Map<Integer, SimpleDirectionContainer<Direction>> distancesToFood = new TreeMap<>();

        for (Direction actualDirection : Direction.values()) {
            Coordinate nextCoordinate = arena
                    .nextCoordinate(actualHeadCoordinate, actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                int actualDistanceToFood = nextCoordinate
                        .minDistance(foodCoordinate, maxCoordinate);

                if (distancesToFood.containsKey(actualDistanceToFood)) {
                    SimpleDirectionContainer<Direction> directions = distancesToFood
                            .get(actualDistanceToFood);
                    directions.add(actualDirection);

                    distancesToFood.put(actualDistanceToFood, directions);
                } else {
                    SimpleDirectionContainer<Direction> directions = new SimpleDirectionContainer<>();
                    directions.add(actualDirection);

                    distancesToFood.put(actualDistanceToFood, directions);
                }
            }
        }

        return distancesToFood;
    }

    private SimpleDirectionContainer<Direction> processEquivalentBestDirections(
            Map<Integer, SimpleDirectionContainer<Direction>> distancesToFood)
    {
        SimpleDirectionContainer<Direction> equivalentBestDirections = new SimpleDirectionContainer<>();

        List<SimpleDirectionContainer<Direction>> directionContainers = new ArrayList<>();

        if (distancesToFood.size() > 0) {
            directionContainers.addAll(distancesToFood.values());
        } else {
            directionContainers.add(null);
        }

        equivalentBestDirections = directionContainers.get(0);

        System.out.println(
                "Equivalent Best Directions: " + equivalentBestDirections);

        return equivalentBestDirections;
    }

    private SimpleDirectionContainer<Direction> processAllValidDirections(
            Map<Integer, SimpleDirectionContainer<Direction>> distancesToFood)
    {
        SimpleDirectionContainer<Direction> allValidDirections = new SimpleDirectionContainer<>();

        List<SimpleDirectionContainer<Direction>> directionContainers = new ArrayList<>();

        if (distancesToFood.size() > 0) {
            directionContainers.addAll(distancesToFood.values());

            for (SimpleDirectionContainer<Direction> directionContainer : directionContainers) {
                allValidDirections.addAll(directionContainer);
            }
        }

        System.out.println("All Valid Directions: " + allValidDirections);

        return allValidDirections;
    }

    private Direction processNewDirection(
            BlockingDirectionContainer blockingDirections,
            SimpleDirectionContainer<Direction> equivalentBestDirections,
            SimpleDirectionContainer<Direction> allValidDirections)
    {
        Direction newDirection = null;

        boolean testDirectBlocks = true;

        do {
            DependencyProvider dependencyProvider = new DependencyProvider(
                    arena, snake, blockingDirections, equivalentBestDirections,
                    allValidDirections);

            newDirection = NewDirectionProcessor
                    .processNewDirection(dependencyProvider, testDirectBlocks);

            testDirectBlocks = false;
        } while (newDirection == null);

        return newDirection;
    }
}
