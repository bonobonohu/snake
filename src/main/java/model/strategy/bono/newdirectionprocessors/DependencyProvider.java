package model.strategy.bono.newdirectionprocessors;

import model.Direction;
import model.strategy.bono.directionhandlers.BlockingDirectionContainer;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public class DependencyProvider
{
    private BlockingDirectionContainer blockingDirectionsDataHandler;
    private SimpleDirectionContainer<Direction> equivalentBestDirections;
    private SimpleDirectionContainer<Direction> allValidDirections;

    public DependencyProvider(
            BlockingDirectionContainer blockingDirectionsDataHandler,
            SimpleDirectionContainer<Direction> equivalentBestDirections,
            SimpleDirectionContainer<Direction> allValidDirections)
    {
        this.blockingDirectionsDataHandler = blockingDirectionsDataHandler;
        this.equivalentBestDirections = equivalentBestDirections;
        this.allValidDirections = allValidDirections;
    }

    public BlockingDirectionContainer getBlockingDirectionsDataHandler()
    {
        return blockingDirectionsDataHandler;
    }

    public SimpleDirectionContainer<Direction> getEquivalentBestDirections()
    {
        return equivalentBestDirections;
    }

    public SimpleDirectionContainer<Direction> getAllValidDirections()
    {
        return allValidDirections;
    }
}
