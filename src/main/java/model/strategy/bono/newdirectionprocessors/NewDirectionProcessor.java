package model.strategy.bono.newdirectionprocessors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Arena;
import model.Coordinate;
import model.Direction;
import model.Snake;
import model.strategy.bono.BlockingDirectionProcessor;
import model.strategy.bono.directionhandlers.BlockingDirectionContainer;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public abstract class NewDirectionProcessor
{
    private Arena arena;
    private Snake snake;

    private Coordinate actualHeadCoordinate;

    protected BlockingDirectionContainer blockingDirections;
    protected SimpleDirectionContainer<Direction> equivalentBestDirections;
    protected SimpleDirectionContainer<Direction> allValidDirections;

    public NewDirectionProcessor(DependencyProvider dependencyProvider)
    {
        this.arena = dependencyProvider.getArena();
        this.snake = dependencyProvider.getSnake();

        actualHeadCoordinate = snake.getHeadCoordinate();

        this.blockingDirections = dependencyProvider
                .getBlockingDirectionsDataHandler();
        this.equivalentBestDirections = dependencyProvider
                .getEquivalentBestDirections();
        this.allValidDirections = dependencyProvider.getAllValidDirections();
    }

    public abstract Direction getNewDirection();

    protected Direction processFinalDirection(
            SimpleDirectionContainer<Direction> directionContainer)
    {
        Direction newDirection;

        List<Direction> directions = directionContainer.getAllAsList();
        Map<Direction, Integer> freeRadicalsToDirection = new HashMap<>();

        for (Direction actualDirection : directions) {
            BlockingDirectionProcessor blockingDirectionProcessor = new BlockingDirectionProcessor(
                    snake, arena);
            BlockingDirectionContainer blockingDirections = new BlockingDirectionContainer();

            Coordinate nextCoordinate = arena
                    .nextCoordinate(actualHeadCoordinate, actualDirection);

            blockingDirections = blockingDirectionProcessor
                    .process(nextCoordinate);

            int freeRadicals = blockingDirections.processFreeRadicals();
            freeRadicalsToDirection.put(actualDirection, freeRadicals);

            System.out.println("Free radicals to " + actualDirection + ": "
                    + freeRadicals);
        }

        newDirection = directionContainer.getRandomElement();

        return newDirection;
    }
}
