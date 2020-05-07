package co.electric.snake.strategy.bonostrategy.directionprocessor;

import co.electric.snake.framework.model.Arena;
import co.electric.snake.framework.model.Coordinate;
import co.electric.snake.framework.model.Direction;
import co.electric.snake.framework.model.Snake;
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer;
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer;
import co.electric.snake.strategy.bonostrategy.distanceprocessor.DistanceProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BlockingDirectionsProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(BlockingDirectionsProcessor.class);

    /**
     * @todo avoid magic constant, make two fors (what if not 50x50 but 25x32 arena?), split into smaller methods!
     */
    public BlockingDirectionContainer getDirections(Snake snake, Arena arena, SimpleDirectionContainer filteredDirections) {
        BlockingDirectionContainer blockingDirections = new BlockingDirectionContainer();

        Coordinate actualHeadCoordinate = snake.getHeadCoordinate();
        Coordinate maxCoordinate = arena.getMaxCoordinate();

        for (Direction actualDirection : filteredDirections) {
            Coordinate nextCoordinate = arena.nextCoordinate(actualHeadCoordinate, actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                Coordinate coordinateToInvestigate = actualHeadCoordinate;

                for (int i = 0; i < 49; i++) {
                    coordinateToInvestigate = arena.nextCoordinate(coordinateToInvestigate, actualDirection);

                    if (arena.isOccupied(coordinateToInvestigate)) {
                        Snake blockingSnake = getBlockingSnake(arena, coordinateToInvestigate);

                        if (isValidBlock(snake, blockingSnake, maxCoordinate)) {
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

        LOG.info("Blocking Directions: " + blockingDirections);

        return blockingDirections;
    }

    private Snake getBlockingSnake(Arena arena, Coordinate blockingCoordinate) {
        Snake blockingSnake = null;

        List<Snake> snakes = arena.getSnakesInNewList();
        for (Snake actualSnake : snakes) {
            List<Coordinate> bodyItems = actualSnake.getBodyItemsInNewList();

            for (Coordinate bodyItem : bodyItems) {
                if (bodyItem.equals(blockingCoordinate)) {
                    blockingSnake = actualSnake;
                }
            }
        }

        return blockingSnake;
    }

    private boolean isValidBlock(Snake snake, Snake blockingSnake, Coordinate maxCoordinate) {
        boolean validBlock = false;

        Coordinate actualHeadCoordinate = snake.getHeadCoordinate();

        if (blockingSnake.equals(snake)) {
            boolean allXsAreTheSame = true;
            boolean allYsAreTheSame = true;

            for (Coordinate actualBodyItem : snake.getBodyItemsInNewList()) {
                if (actualBodyItem.getX() != actualHeadCoordinate.getX()) {
                    allXsAreTheSame = false;
                }
                if (actualBodyItem.getY() != actualHeadCoordinate.getY()) {
                    allYsAreTheSame = false;
                }
            }

            if ((!allXsAreTheSame && !allYsAreTheSame) || (allXsAreTheSame && snake.getLength() == maxCoordinate.getY())
                    || (allYsAreTheSame && snake.getLength() == maxCoordinate.getX())) {
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

        for (Coordinate actualBodyItem : blockingSnake.getBodyItemsInNewList()) {
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
