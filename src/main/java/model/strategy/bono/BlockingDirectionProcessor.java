package model.strategy.bono;

import model.Arena;
import model.Coordinate;
import model.Direction;
import model.Snake;
import model.strategy.bono.directioncontainers.BlockingDirectionContainer;
import model.strategy.bono.directioncontainers.SimpleDirectionContainer;
import model.strategy.bono.distanceprocessors.DistanceProcessor;

import java.util.List;

public class BlockingDirectionProcessor {

    private Arena arena;
    private Snake snake;

    private Printer printer;

    private Coordinate maxCoordinate;

    public BlockingDirectionProcessor(Snake snake, Arena arena, Printer printer) {
        this.arena = arena;
        this.snake = snake;

        this.printer = printer;

        maxCoordinate = arena.getMaxCoordinate();
    }

    public BlockingDirectionContainer process(Coordinate actualHeadCoordinate,
                                              SimpleDirectionContainer filteredDirections) {
        /**
         * @todo avoid magic constant, make two fors (what if not 50x50 but 25x32 arena?), split into smaller methods!
         */
        BlockingDirectionContainer blockingDirections = new BlockingDirectionContainer();

        for (Direction actualDirection : filteredDirections) {
            Coordinate nextCoordinate = arena.nextCoordinate(actualHeadCoordinate, actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                Coordinate coordinateToInvestigate = actualHeadCoordinate;

                for (int i = 0; i < 49; i++) {
                    coordinateToInvestigate = arena.nextCoordinate(coordinateToInvestigate, actualDirection);

                    if (arena.isOccupied(coordinateToInvestigate)) {
                        Snake blockingSnake = getBlockingSnake(coordinateToInvestigate);

                        if (isValidBlock(actualHeadCoordinate, blockingSnake)) {
                            int blockingTailLength = getBlockingTailLength(blockingSnake, coordinateToInvestigate);

                            int distanceToBlock = DistanceProcessor.getStrategy(actualDirection)
                                    .getDistance(actualHeadCoordinate, coordinateToInvestigate, maxCoordinate);

                            if (isBlockingRisk(blockingTailLength, distanceToBlock)) {
                                blockingDirections.putData(actualDirection, coordinateToInvestigate, distanceToBlock);
                            }
                        }
                    }
                }
            }
        }

        printer.print("Blocking Directions: " + blockingDirections);

        return blockingDirections;
    }

    private Snake getBlockingSnake(Coordinate blockingCoordinate) {
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

    private boolean isValidBlock(Coordinate actualHeadCoordinate, Snake blockingSnake) {
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

            if ((!allXsAreTheSame && !allYsAreTheSame) || (allXsAreTheSame && snake.length() == maxCoordinate.getY())
                    || (allYsAreTheSame && snake.length() == maxCoordinate.getX())) {
                validBlock = true;
            }
        } else {
            validBlock = true;
        }

        return validBlock;
    }

    private int getBlockingTailLength(Snake blockingSnake, Coordinate nextCoordinateToInvestigate) {
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

    private boolean isBlockingRisk(int blockingTailLength, int distanceToBlock) {
        return blockingTailLength >= distanceToBlock;
    }

}
