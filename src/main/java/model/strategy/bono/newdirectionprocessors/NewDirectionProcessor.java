package model.strategy.bono.newdirectionprocessors;

import java.util.ArrayList;
import java.util.List;

import model.Direction;
import model.strategy.bono.Printer;
import model.strategy.bono.directionhandlers.BlockingDirectionContainer;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public abstract class NewDirectionProcessor
{
    protected BlockingDirectionContainer blockingDirections;
    protected SimpleDirectionContainer equivalentBestDirections;
    protected SimpleDirectionContainer filteredDirections;

    protected Printer printer;

    public static Direction processNewDirection(DependencyProvider dependencyProvider)
    {
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

    public NewDirectionProcessor(DependencyProvider dependencyProvider)
    {
        this.blockingDirections = dependencyProvider.getBlockingDirectionsDataHandler();
        this.equivalentBestDirections = dependencyProvider.getEquivalentBestDirections();
        this.filteredDirections = dependencyProvider.getFilteredDirections();

        this.printer = dependencyProvider.getPrinter();
    }

    public abstract Direction getNewDirection();

    protected Direction processFinalDirection(SimpleDirectionContainer directionContainer)
    {
        return directionContainer.getRandomElement();
    }
}
