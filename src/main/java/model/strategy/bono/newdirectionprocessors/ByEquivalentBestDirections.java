package model.strategy.bono.newdirectionprocessors;

import model.Direction;

public class ByEquivalentBestDirections extends NewDirectionProcessor
{
    @Override
    public Direction getNewDirection()
    {
        Direction newDirection = null;

        if (blockingDirectionsData.size() == 0) {
            newDirection = equivalentBestDirections.getRandomElement();

            System.out.println(
                    "Random element from the equivalent best directions: "
                            + newDirection);
        }

        return newDirection;
    }
}
