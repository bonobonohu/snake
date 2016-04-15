package model.strategy.bono.newdirectionprocessors;

import java.util.ArrayList;
import java.util.List;

import model.Arena;
import model.Coordinate;
import model.Direction;
import model.Snake;
import model.strategy.bono.directionhandlers.BlockingDirectionContainer;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public abstract class NewDirectionProcessor
{
    private Arena arena;
    private Snake snake;

    private Coordinate actualHeadCoordinate;

    private boolean testDirectBlocks;

    protected BlockingDirectionContainer blockingDirections;
    protected SimpleDirectionContainer<Direction> equivalentBestDirections;
    protected SimpleDirectionContainer<Direction> allValidDirections;

    public static Direction processNewDirection(
            DependencyProvider dependencyProvider, boolean testDirectBlocks)
    {
        Direction newDirection = null;

        List<NewDirectionProcessor> newDirectionProcessors = new ArrayList<>();
        newDirectionProcessors.add(new ByFreeEquivalentBestDirections(
                dependencyProvider, testDirectBlocks));
        newDirectionProcessors.add(new ByFreeValidDirections(dependencyProvider,
                testDirectBlocks));
        newDirectionProcessors.add(
                new ByBlockingDistances(dependencyProvider, testDirectBlocks));

        for (NewDirectionProcessor newDirectionProcessor : newDirectionProcessors) {
            if (newDirection == null) {
                newDirection = newDirectionProcessor.getNewDirection();
            }
        }

        return newDirection;
    }

    public static Direction processLastChanceNewDirection(
            DependencyProvider dependencyProvider, boolean testDirectBlocks)
    {
        Direction newDirection = null;

        List<NewDirectionProcessor> newDirectionProcessors = new ArrayList<>();
        newDirectionProcessors
                .add(new ByRandom(dependencyProvider, testDirectBlocks));
        newDirectionProcessors
                .add(new ByKispalEsABorz(dependencyProvider, testDirectBlocks));

        for (NewDirectionProcessor newDirectionProcessor : newDirectionProcessors) {
            if (newDirection == null) {
                newDirection = newDirectionProcessor.getNewDirection();
            }
        }

        return newDirection;
    }

    public NewDirectionProcessor(DependencyProvider dependencyProvider,
            boolean testDirectBlocks)
    {
        this.arena = dependencyProvider.getArena();
        this.snake = dependencyProvider.getSnake();

        actualHeadCoordinate = snake.getHeadCoordinate();

        this.testDirectBlocks = testDirectBlocks;

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

        if (testDirectBlocks) {
            newDirection = testDirectBlocks(directionContainer);
        } else {
            newDirection = directionContainer.getRandomElement();
        }

        return newDirection;
    }

    private Direction testDirectBlocks(
            SimpleDirectionContainer<Direction> directionContainer)
    {
        Direction newDirection = null;

        SimpleDirectionContainer<Direction> testedDirections = new SimpleDirectionContainer<Direction>();

        List<Direction> directions = directionContainer.getAllAsList();

        for (Direction actualDirection : directions) {
            int directlyBlockedDirections = 0;

            Coordinate nextCoordinate = arena
                    .nextCoordinate(actualHeadCoordinate, actualDirection);

            for (Direction actualTestingDirection : Direction.values()) {
                Coordinate nextTestingCoordinate = arena
                        .nextCoordinate(nextCoordinate, actualTestingDirection);

                if (arena.isOccupied(nextTestingCoordinate)) {
                    directlyBlockedDirections++;
                }
            }

            if (directlyBlockedDirections < 3) {
                testedDirections.add(actualDirection);
            } else {
                System.out.println("Direction blocked from at least 3 ways: "
                        + actualDirection);
            }
        }

        newDirection = testedDirections.getRandomElement();

        return newDirection;
    }
}
