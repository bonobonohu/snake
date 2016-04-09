package model.strategy.bono.newdirectionprocessors;

import model.Direction;
import model.strategy.bono.directionhandlers.BlockingDirectionContainer;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public abstract class NewDirectionProcessor
{
    protected BlockingDirectionContainer blockingDirections;
    protected SimpleDirectionContainer<Direction> equivalentBestDirections;
    protected SimpleDirectionContainer<Direction> allValidDirections;

    public NewDirectionProcessor(DependencyProvider dependencyProvider)
    {
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

        // List<Direction> directions = directionContainer.getAllAsList();

        newDirection = directionContainer.getRandomElement();

        return newDirection;
    }
}
