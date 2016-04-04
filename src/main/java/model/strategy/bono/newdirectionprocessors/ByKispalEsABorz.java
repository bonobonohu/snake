package model.strategy.bono.newdirectionprocessors;

import model.Direction;

public class ByKispalEsABorz extends NewDirectionProcessor
{
    @Override
    public Direction getNewDirection()
    {
        Direction newDirection = null;

        newDirection = Direction.SOUTH;

        return newDirection;
    }

}
