package model.strategy.bono.newdirectionprocessors;

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
            Coordinate nextCoordinate = arena
                    .nextCoordinate(actualHeadCoordinate, actualDirection);
            int directlyBlockedDirections = 1;
            // 1, because we are coming from somewhere, stupid!

            for (Direction actualTestingDirection : Direction.values()) {
                Coordinate nextTestingCoordinate = arena
                        .nextCoordinate(nextCoordinate, actualTestingDirection);

                if (arena.isOccupied(nextTestingCoordinate)) {
                    directlyBlockedDirections++;
                }
            }

            if (directlyBlockedDirections < 3) {
                testedDirections.add(actualDirection);
            }
        }

        newDirection = testedDirections.getRandomElement();

        return newDirection;
    }
}
