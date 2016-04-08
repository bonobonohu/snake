package model.strategy.bono.newdirectionprocessors;

import model.Direction;
import model.strategy.bono.directionhandlers.DirectionContainer;
import model.strategy.bono.directionhandlers.BlockingDirectionContainer;

public abstract class NewDirectionProcessor
{
    protected BlockingDirectionContainer blockingDirections;
    protected DirectionContainer<Direction> allValidDirections;
    protected DirectionContainer<Direction> equivalentBestDirections;

    public NewDirectionProcessor(DependencyProvider dependencyProvider)
    {
        this.blockingDirections = dependencyProvider
                .getBlockingDirectionsDataHandler();
        this.allValidDirections = dependencyProvider.getAllValidDirections();
        this.equivalentBestDirections = dependencyProvider
                .getEquivalentBestDirections();
    }

    public abstract Direction getNewDirection();

    protected Direction processFinalDirection(
            DirectionContainer<Direction> directionContainer)
    {
        Direction newDirection;

        // List<Direction> directions = directionContainer.getAllAsList();

        newDirection = directionContainer.getRandomElement();

        return newDirection;
    }
}
