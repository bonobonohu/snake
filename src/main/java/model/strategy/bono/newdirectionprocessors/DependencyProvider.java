package model.strategy.bono.newdirectionprocessors;

import model.Arena;
import model.Snake;
import model.strategy.bono.Printer;
import model.strategy.bono.directioncontainers.BlockingDirectionContainer;
import model.strategy.bono.directioncontainers.SimpleDirectionContainer;

public class DependencyProvider {
    private Arena arena;
    private Snake snake;

    private BlockingDirectionContainer blockingDirectionsDataHandler;
    private SimpleDirectionContainer equivalentBestDirections;
    private SimpleDirectionContainer filteredDirections;

    private Printer printer;

    public DependencyProvider(Arena arena, Snake snake, BlockingDirectionContainer blockingDirectionsDataHandler,
            SimpleDirectionContainer filteredDirections, SimpleDirectionContainer equivalentBestDirections,
            Printer printer) {
        this.arena = arena;
        this.snake = snake;

        this.blockingDirectionsDataHandler = blockingDirectionsDataHandler;
        this.equivalentBestDirections = equivalentBestDirections;
        this.filteredDirections = filteredDirections;

        this.printer = printer;
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

    public Printer getPrinter() {
        return printer;
    }
}
