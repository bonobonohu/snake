package co.electric.snake.strategy.bonostrategy.directionprocessor;

import co.electric.snake.framework.model.Arena;
import co.electric.snake.framework.model.Coordinate;
import co.electric.snake.framework.model.Direction;
import co.electric.snake.framework.model.Snake;
import co.electric.snake.strategy.bonostrategy.BlockingDirectionContainer;
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer;
import co.electric.snake.strategy.bonostrategy.distanceprocessor.DistanceProcessorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockingDirectionsProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(BlockingDirectionsProcessor.class);

    private final DistanceProcessorChain distanceProcessorChain;

    public BlockingDirectionsProcessor(DistanceProcessorChain distanceProcessorChain) {
        this.distanceProcessorChain = distanceProcessorChain;
    }

    public BlockingDirectionContainer getDirections(Snake snake, Arena arena, SimpleDirectionContainer filteredDirections) {
        final BlockingDirectionContainer blockingDirections = new BlockingDirectionContainer();
        final Coordinate headCoordinate = snake.getHeadCoordinate();
        final Coordinate maxCoordinate = arena.getMaxCoordinate();
        filteredDirections.forEach(
                direction -> {
                    Coordinate coordinateToInvestigate = headCoordinate;
                    final int maxCoordinateForDirection = getMaxCoordinateForDirection(maxCoordinate, direction);
                    for (int i = 0; i < maxCoordinateForDirection; i++) {
                        coordinateToInvestigate = arena.nextCoordinate(coordinateToInvestigate, direction);
                        if (arena.isOccupied(coordinateToInvestigate)) {
                            final Snake blockingSnake = getBlockingSnake(arena, coordinateToInvestigate);
                            final int blockingTailLength = getBlockingTailLength(blockingSnake, coordinateToInvestigate);
                            final int distanceToBlock = getDistanceToBlock(direction, headCoordinate, coordinateToInvestigate, maxCoordinate);
                            if (isBlockingRisk(blockingTailLength, distanceToBlock)) {
                                blockingDirections.putData(direction, coordinateToInvestigate, distanceToBlock);
                            }
                        }
                    }
                }
        );
        LOG.info("Blocking Directions: " + blockingDirections);
        return blockingDirections;
    }

    private int getDistanceToBlock(Direction direction, Coordinate headCoordinate, Coordinate coordinateToInvestigate, Coordinate maxCoordinate) {
        return distanceProcessorChain.getDistance(direction, headCoordinate, coordinateToInvestigate, maxCoordinate);
    }

    private int getMaxCoordinateForDirection(Coordinate maxCoordinate, Direction direction) {
        final int maxCoordinateForDirection;
        if (Arrays.asList(Direction.NORTH, Direction.SOUTH).contains(direction)) {
            maxCoordinateForDirection = maxCoordinate.getX() - 1;
        } else {
            maxCoordinateForDirection = maxCoordinate.getY() - 1;
        }
        return maxCoordinateForDirection;
    }

    private Snake getBlockingSnake(Arena arena, Coordinate blockingCoordinate) {
        return arena.getSnakesInNewList().stream()
                .filter(snake -> snake.getBodyItemsInNewList().stream().anyMatch(bodyItem -> bodyItem.equals(blockingCoordinate)))
                .findAny()
                .orElse(null);
    }

    private int getBlockingTailLength(Snake blockingSnake, Coordinate blockingCoordinate) {
        final AtomicInteger blockingTailLength = new AtomicInteger();
        final AtomicBoolean reachedTheBlockingPart = new AtomicBoolean(false);
        blockingSnake.getBodyItemsInNewList().forEach(
                bodyItem -> {
                    if (bodyItem.equals(blockingCoordinate)) {
                        reachedTheBlockingPart.set(true);
                    }
                    if (reachedTheBlockingPart.get()) {
                        blockingTailLength.getAndIncrement();
                    }
                }
        );
        return blockingTailLength.get();
    }

    private boolean isBlockingRisk(int blockingTailLength, int distanceToBlock) {
        return blockingTailLength >= distanceToBlock;
    }

}
