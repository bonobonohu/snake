package model.strategy.bono.newdirectionprocessors;

import model.Direction;
import model.strategy.bono.directionhandlers.BlockingDirectionContainer;
import model.strategy.bono.directionhandlers.DirectionContainer;

public abstract class NewDirectionProcessor
{
    protected BlockingDirectionContainer blockingDirections;
    protected DirectionContainer<Direction> equivalentBestDirections;
    protected DirectionContainer<Direction> allValidDirections;

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
            DirectionContainer<Direction> directionContainer)
    {
        Direction newDirection;

        // List<Direction> directions = directionContainer.getAllAsList();

        newDirection = directionContainer.getRandomElement();

        return newDirection;
    }
}
