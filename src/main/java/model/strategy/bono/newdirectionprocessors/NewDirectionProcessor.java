package model.strategy.bono.newdirectionprocessors;

import model.Direction;
import model.strategy.bono.directionhandlers.DirectionContainer;
import model.strategy.bono.directionhandlers.DirectionDataHandler;

public abstract class NewDirectionProcessor
{
    protected DirectionDataHandler blockingDirectionsDataHandler;
    protected DirectionContainer<Direction> allValidDirections;
    protected DirectionContainer<Direction> equivalentBestDirections;

    public NewDirectionProcessor(DependencyProvider dependencyProvider)
    {
        this.blockingDirectionsDataHandler = dependencyProvider
                .getBlockingDirectionsDataHandler();
        this.allValidDirections = dependencyProvider.getAllValidDirections();
        this.equivalentBestDirections = dependencyProvider
                .getEquivalentBestDirections();
    }

    public abstract Direction getNewDirection();
}
