package model.strategy.bono.newdirectionprocessors;

import model.Direction;
import model.strategy.bono.directionhandlers.DirectionContainer;
import model.strategy.bono.directionhandlers.DirectionDataHandler;

public class DependencyProvider
{
    private DirectionDataHandler blockingDirectionsDataHandler;
    private DirectionContainer<Direction> allValidDirections;
    private DirectionContainer<Direction> equivalentBestDirections;

    public DependencyProvider(DirectionDataHandler blockingDirectionsDataHandler,
            DirectionContainer<Direction> allValidDirections,
            DirectionContainer<Direction> equivalentBestDirections)
    {
        this.blockingDirectionsDataHandler = blockingDirectionsDataHandler;
        this.allValidDirections = allValidDirections;
        this.equivalentBestDirections = equivalentBestDirections;
    }

    public DirectionDataHandler getBlockingDirectionsDataHandler()
    {
        return blockingDirectionsDataHandler;
    }

    public DirectionContainer<Direction> getAllValidDirections()
    {
        return allValidDirections;
    }

    public DirectionContainer<Direction> getEquivalentBestDirections()
    {
        return equivalentBestDirections;
    }
}
