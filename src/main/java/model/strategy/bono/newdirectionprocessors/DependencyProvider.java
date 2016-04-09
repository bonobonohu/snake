package model.strategy.bono.newdirectionprocessors;

import model.Arena;
import model.Direction;
import model.Snake;
import model.strategy.bono.directionhandlers.BlockingDirectionContainer;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public class DependencyProvider
{
    private Arena arena;
    private Snake snake;

    private BlockingDirectionContainer blockingDirectionsDataHandler;
    private SimpleDirectionContainer<Direction> equivalentBestDirections;
    private SimpleDirectionContainer<Direction> allValidDirections;

    public DependencyProvider(Arena arena, Snake snake,
            BlockingDirectionContainer blockingDirectionsDataHandler,
            SimpleDirectionContainer<Direction> equivalentBestDirections,
            SimpleDirectionContainer<Direction> allValidDirections)
    {
        this.arena = arena;
        this.snake = snake;

        this.blockingDirectionsDataHandler = blockingDirectionsDataHandler;
        this.equivalentBestDirections = equivalentBestDirections;
        this.allValidDirections = allValidDirections;
    }

    public Arena getArena()
    {
        return arena;
    }

    public Snake getSnake()
    {
        return snake;
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
