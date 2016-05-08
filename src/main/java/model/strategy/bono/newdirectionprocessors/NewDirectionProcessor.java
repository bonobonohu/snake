package model.strategy.bono.newdirectionprocessors;

import java.util.ArrayList;
import java.util.List;

import model.Direction;
import model.strategy.bono.directionhandlers.BlockingDirectionContainer;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;

public abstract class NewDirectionProcessor
{
    protected BlockingDirectionContainer blockingDirections;
    protected SimpleDirectionContainer<Direction> equivalentBestDirections;
    protected SimpleDirectionContainer<Direction> allValidDirections;

    public static Direction processNewDirection(
            DependencyProvider dependencyProvider)
    {
        Direction newDirection = null;

        List<NewDirectionProcessor> newDirectionProcessors = new ArrayList<>();
        newDirectionProcessors
                .add(new ByFreeEquivalentBestDirections(dependencyProvider));
        newDirectionProcessors
                .add(new ByFreeValidDirections(dependencyProvider));
        newDirectionProcessors.add(new ByBlockingDistances(dependencyProvider));
        newDirectionProcessors.add(new ByRandom(dependencyProvider));
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
        this.blockingDirections = dependencyProvider
                .getBlockingDirectionsDataHandler();
        this.equivalentBestDirections = dependencyProvider
                .getEquivalentBestDirections();
        this.allValidDirections = dependencyProvider.getAllValidDirections();
    }

    public abstract Direction getNewDirection();

    protected Direction processFinalDirection(
            SimpleDirectionContainer<Direction> directionContainer)
    {
        return directionContainer.getRandomElement();
    }
}
