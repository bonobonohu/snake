package model.strategy.bono.newdirectionprocessors;

import model.Direction;
import model.strategy.bono.directionhandlers.DirectionContainer;
import model.strategy.bono.directionhandlers.DirectionData;

public abstract class NewDirectionProcessor
{
    protected DirectionData blockingDirectionsData;
    protected DirectionContainer<Direction> allValidDirections;
    protected DirectionContainer<Direction> equivalentBestDirections;

    public abstract Direction getNewDirection();

    public NewDirectionProcessor()
    {
    }

    public void setBlockingDirectionsData(DirectionData blockingDirectionsData)
    {
        this.blockingDirectionsData = blockingDirectionsData;
    }

    public void setAllValidDirections(
            DirectionContainer<Direction> allValidDirections)
    {
        this.allValidDirections = allValidDirections;
    }

    public void setEquivalentBestDirections(
            DirectionContainer<Direction> equivalentBestDirections)
    {
        this.equivalentBestDirections = equivalentBestDirections;
    }
}
