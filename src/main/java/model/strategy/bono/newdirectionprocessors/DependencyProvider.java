package model.strategy.bono.newdirectionprocessors;

import model.Direction;
import model.strategy.bono.directionhandlers.BlockingDirectionContainer;
import model.strategy.bono.directionhandlers.DirectionContainer;

public class DependencyProvider
{
    private BlockingDirectionContainer blockingDirectionsDataHandler;
    private DirectionContainer<Direction> equivalentBestDirections;
    private DirectionContainer<Direction> allValidDirections;

    public DependencyProvider(
            BlockingDirectionContainer blockingDirectionsDataHandler,
            DirectionContainer<Direction> equivalentBestDirections,
            DirectionContainer<Direction> allValidDirections)
    {
        this.blockingDirectionsDataHandler = blockingDirectionsDataHandler;
        this.equivalentBestDirections = equivalentBestDirections;
        this.allValidDirections = allValidDirections;
    }

    public BlockingDirectionContainer getBlockingDirectionsDataHandler()
    {
        return blockingDirectionsDataHandler;
    }

    public DirectionContainer<Direction> getEquivalentBestDirections()
    {
        return equivalentBestDirections;
    }

    public DirectionContainer<Direction> getAllValidDirections()
    {
        return allValidDirections;
    }
}
