package co.electric.snake.model.strategy.bono.newdirectionprocessors;

import co.electric.snake.model.Direction;
import co.electric.snake.model.strategy.bono.Printer;
import co.electric.snake.model.strategy.bono.directioncontainers.BlockingDirectionContainer;
import co.electric.snake.model.strategy.bono.directioncontainers.SimpleDirectionContainer;

import java.util.ArrayList;
import java.util.List;

public abstract class NewDirectionProcessor {

    protected BlockingDirectionContainer blockingDirections;
    protected SimpleDirectionContainer equivalentBestDirections;
    protected SimpleDirectionContainer filteredDirections;

    protected Printer printer;

    public NewDirectionProcessor(DependencyProvider dependencyProvider) {
        this.blockingDirections = dependencyProvider.getBlockingDirectionsDataHandler();
        this.equivalentBestDirections = dependencyProvider.getEquivalentBestDirections();
        this.filteredDirections = dependencyProvider.getFilteredDirections();

        this.printer = dependencyProvider.getPrinter();
    }

    public static Direction processNewDirection(DependencyProvider dependencyProvider) {
        Direction newDirection = null;

        /**
         * @todo implement real Chain of Responsibility pattern, in which every link says which one is the next following it!
         */
        List<NewDirectionProcessor> newDirectionProcessors = new ArrayList<>();
        // newDirectionProcessors
        // .add(new ByEquivalentBestDirections(dependencyProvider));
        newDirectionProcessors.add(new ByFreeEquivalentBestDirections(dependencyProvider));
        newDirectionProcessors.add(new ByFreeFilteredDirections(dependencyProvider));
        newDirectionProcessors.add(new ByBlockingDistances(dependencyProvider));
        newDirectionProcessors.add(new ByFilteredDirections(dependencyProvider));
        newDirectionProcessors.add(new ByKispalEsABorz(dependencyProvider));

        for (NewDirectionProcessor newDirectionProcessor : newDirectionProcessors) {
            if (newDirection == null) {
                newDirection = newDirectionProcessor.getNewDirection();
            }
        }

        return newDirection;
    }

    public abstract Direction getNewDirection();

    protected Direction processFinalDirection(SimpleDirectionContainer directionContainer) {
        return directionContainer.getRandomElement();
    }

}