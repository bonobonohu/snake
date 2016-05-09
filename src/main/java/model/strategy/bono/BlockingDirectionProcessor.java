package model.strategy.bono;

import java.util.List;

import model.Arena;
import model.Coordinate;
import model.Direction;
import model.Snake;
import model.strategy.bono.directionhandlers.BlockingDirectionContainer;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;
import model.strategy.bono.distanceprocessors.DistanceProcessor;

public class BlockingDirectionProcessor
{
    private Arena arena;
    private Snake snake;

    private Coordinate maxCoordinate;

    public BlockingDirectionProcessor(Snake snakeParam, Arena arenaParam)
    {
        arena = arenaParam;
        snake = snakeParam;

        maxCoordinate = arena.getMaxCoordinate();
    }

    public BlockingDirectionContainer process(Coordinate actualHeadCoordinate,
            SimpleDirectionContainer<Direction> filteredDirections)
    {
        BlockingDirectionContainer blockingDirections = new BlockingDirectionContainer();

        for (Direction actualDirection : filteredDirections) {
            Coordinate nextCoordinate = arena
                    .nextCoordinate(actualHeadCoordinate, actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                Coordinate coordinateToInvestigate = actualHeadCoordinate;

                for (int i = 0; i < 49; i++) {
                    coordinateToInvestigate = arena.nextCoordinate(
                            coordinateToInvestigate, actualDirection);

                    if (arena.isOccupied(coordinateToInvestigate)) {
                        Snake blockingSnake = getBlockingSnake(
                                coordinateToInvestigate);

                        if (isValidBlock(actualHeadCoordinate, blockingSnake)) {
                            int blockingTailLength = getBlockingTailLength(
                                    blockingSnake, coordinateToInvestigate);

                            DistanceProcessor distanceProcessor = DistanceProcessor
                                    .getDistanceProcessor(actualDirection);
                            int distanceToBlock = distanceProcessor.getDistance(
                                    actualHeadCoordinate,
                                    coordinateToInvestigate, maxCoordinate);

                            if (isBlockingRisk(blockingTailLength,
                                    distanceToBlock)) {
                                blockingDirections.putData(actualDirection,
                                        coordinateToInvestigate,
                                        distanceToBlock);
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Blocking Directions: " + blockingDirections);

        return blockingDirections;
    }

    private Snake getBlockingSnake(Coordinate blockingCoordinate)
    {
        Snake blockingSnake = null;

        List<Snake> snakes = arena.getSnakes();
        for (Snake actualSnake : snakes) {
            List<Coordinate> bodyItems = actualSnake.getBodyItems();

            for (Coordinate bodyItem : bodyItems) {
                if (bodyItem.equals(blockingCoordinate)) {
                    blockingSnake = actualSnake;
                }
            }
        }

        return blockingSnake;
    }

    private boolean isValidBlock(Coordinate actualHeadCoordinate,
            Snake blockingSnake)
    {
        boolean validBlock = false;

        if (blockingSnake.equals(snake)) {
            boolean allXsAreTheSame = true;
            boolean allYsAreTheSame = true;

            for (Coordinate actualBodyItem : snake.getBodyItems()) {
                if (actualBodyItem.getX() != actualHeadCoordinate.getX()) {
                    allXsAreTheSame = false;
                }
                if (actualBodyItem.getY() != actualHeadCoordinate.getY()) {
                    allYsAreTheSame = false;
                }
            }

            if ((!allXsAreTheSame && !allYsAreTheSame)
                    || (allXsAreTheSame
                            && snake.length() == maxCoordinate.getY())
                    || (allYsAreTheSame
                            && snake.length() == maxCoordinate.getX())) {
                validBlock = true;
            }
        } else {
            validBlock = true;
        }

        return validBlock;
    }

    private int getBlockingTailLength(Snake blockingSnake,
            Coordinate nextCoordinateToInvestigate)
    {
        boolean reachedTheBlockingPart = false;
        int blockingTailLength = 0;

        for (Coordinate actualBodyItem : blockingSnake.getBodyItems()) {
            if (actualBodyItem.equals(nextCoordinateToInvestigate)) {
                reachedTheBlockingPart = true;
            }

            if (reachedTheBlockingPart) {
                blockingTailLength++;
            }
        }

        return blockingTailLength;
    }

    private boolean isBlockingRisk(int blockingTailLength, int distanceToBlock)
    {
        return blockingTailLength >= distanceToBlock;
    }
}
