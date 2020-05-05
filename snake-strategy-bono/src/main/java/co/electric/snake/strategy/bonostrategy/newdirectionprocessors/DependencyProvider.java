package co.electric.snake.strategy.bonostrategy.newdirectionprocessors;

import co.electric.snake.framework.model.Arena;
import co.electric.snake.framework.model.Snake;
import co.electric.snake.strategy.bonostrategy.directioncontainers.BlockingDirectionContainer;
import co.electric.snake.strategy.bonostrategy.directioncontainers.SimpleDirectionContainer;

public class DependencyProvider {

    private Arena arena;
    private Snake snake;

    private BlockingDirectionContainer blockingDirectionsDataHandler;
    private SimpleDirectionContainer equivalentBestDirections;
    private SimpleDirectionContainer filteredDirections;

    public DependencyProvider(Arena arena, Snake snake, BlockingDirectionContainer blockingDirectionsDataHandler,
                              SimpleDirectionContainer filteredDirections, SimpleDirectionContainer equivalentBestDirections) {
        this.arena = arena;
        this.snake = snake;

        this.blockingDirectionsDataHandler = blockingDirectionsDataHandler;
        this.equivalentBestDirections = equivalentBestDirections;
        this.filteredDirections = filteredDirections;
    }

    public Arena getArena() {
        return arena;
    }

    public Snake getSnake() {
        return snake;
    }

    public BlockingDirectionContainer getBlockingDirectionsDataHandler() {
        return blockingDirectionsDataHandler;
    }

    public SimpleDirectionContainer getEquivalentBestDirections() {
        return equivalentBestDirections;
    }

    public SimpleDirectionContainer getFilteredDirections() {
        return filteredDirections;
    }

}
