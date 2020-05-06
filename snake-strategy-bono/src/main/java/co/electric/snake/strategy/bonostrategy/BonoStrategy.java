package co.electric.snake.strategy.bonostrategy;

import co.electric.snake.framework.model.Arena;
import co.electric.snake.framework.model.Coordinate;
import co.electric.snake.framework.model.Direction;
import co.electric.snake.framework.model.Snake;
import co.electric.snake.framework.strategy.SnakeStrategy;
import co.electric.snake.strategy.bonostrategy.closeddirectionsprocessors.ClosedDirectionsProcessor;
import co.electric.snake.strategy.bonostrategy.directioncontainers.BlockingDirectionContainer;
import co.electric.snake.strategy.bonostrategy.directioncontainers.SimpleDirectionContainer;
import co.electric.snake.strategy.bonostrategy.newdirectionprocessors.DependencyProvider;
import co.electric.snake.strategy.bonostrategy.newdirectionprocessors.NewDirectionProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BonoStrategy implements SnakeStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(BonoStrategy.class);

    private Arena arena;
    private Snake snake;

    private Coordinate actualHeadCoordinate;
    private Coordinate foodCoordinate;
    private Coordinate maxCoordinate;

    private ClosedDirectionsProcessor closedDirectionsProcessor;

    public BonoStrategy(ClosedDirectionsProcessor closedDirectionsProcessor) {
        this.closedDirectionsProcessor = closedDirectionsProcessor;
    }

    @Override
    public Direction nextMove(Snake snakeArgument, Arena arenaArgument) {
        arena = arenaArgument;
        snake = snakeArgument;

        LOG.info("--- BEGIN " + snake.getName() + "---");

        LOG.info("Length: " + snake.getLength());

        actualHeadCoordinate = snake.getHeadCoordinate();
        foodCoordinate = arena.getFoodInNewList().get(0).getCoordinate();
        maxCoordinate = arena.getMaxCoordinate();

        LOG.info("Food: " + arena.getFoodInNewList().get(0).getCoordinate());
        LOG.info("Head: " + actualHeadCoordinate);

        Direction newDirection = process();

        LOG.info("--- END " + snake.getName() + "---");

        return newDirection;
    }

    private Direction process() {
        SimpleDirectionContainer freeDirections = getFreeDirections();
        SimpleDirectionContainer closedDirections = closedDirectionsProcessor.getClosedDirections(arena, actualHeadCoordinate);
        SimpleDirectionContainer filteredDirections = getFilteredDirections(freeDirections, closedDirections);

        BlockingDirectionProcessor blockingDirectionProcessor = new BlockingDirectionProcessor(snake, arena);
        BlockingDirectionContainer blockingDirections = blockingDirectionProcessor.process(actualHeadCoordinate,
                filteredDirections);

        Map<Integer, SimpleDirectionContainer> distancesToFood = getDistancesToFood(filteredDirections);
        SimpleDirectionContainer equivalentBestDirections = getEquivalentBestDirections(distancesToFood);

        DependencyProvider dependencyProvider = new DependencyProvider(arena, snake, blockingDirections,
                filteredDirections, equivalentBestDirections);

        return getNewDirection(dependencyProvider);
    }

    private SimpleDirectionContainer getFilteredDirections(SimpleDirectionContainer freeDirections,
                                                           SimpleDirectionContainer closedDirections) {
        SimpleDirectionContainer filteredDirections = freeDirections.getElementsInANewInstance();
        filteredDirections.removeAll(closedDirections);

        LOG.info("Filtered Directions: " + filteredDirections);

        return filteredDirections;
    }

    private SimpleDirectionContainer getFreeDirections() {
        SimpleDirectionContainer freeDirections = new SimpleDirectionContainer();

        for (Direction actualDirection : Direction.values()) {
            Coordinate nextCoordinate = arena.nextCoordinate(actualHeadCoordinate, actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                freeDirections.add(actualDirection);
            }
        }

        LOG.info("Free Directions: " + freeDirections);

        return freeDirections;
    }

    private Map<Integer, SimpleDirectionContainer> getDistancesToFood(SimpleDirectionContainer filteredDirections) {
        Map<Integer, SimpleDirectionContainer> distancesToFood = new TreeMap<>();

        for (Direction actualDirection : filteredDirections) {
            Coordinate nextCoordinate = arena.nextCoordinate(actualHeadCoordinate, actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                int actualDistanceToFood = nextCoordinate.minDistance(foodCoordinate, maxCoordinate);

                SimpleDirectionContainer directions;
                if (!distancesToFood.containsKey(actualDistanceToFood)) {
                    directions = new SimpleDirectionContainer();
                } else {
                    directions = distancesToFood.get(actualDistanceToFood);
                }
                directions.add(actualDirection);
                distancesToFood.put(actualDistanceToFood, directions);
            }
        }

        LOG.info("Distances To Food: " + distancesToFood);

        return distancesToFood;
    }

    private SimpleDirectionContainer getEquivalentBestDirections(Map<Integer, SimpleDirectionContainer> distancesToFood) {
        List<SimpleDirectionContainer> directionContainers = new ArrayList<>();

        if (!distancesToFood.isEmpty()) {
            directionContainers.addAll(distancesToFood.values());
        } else {
            directionContainers.add(null);
        }

        SimpleDirectionContainer equivalentBestDirections = directionContainers.get(0);

        LOG.info("Equivalent Best Directions: " + equivalentBestDirections);

        return equivalentBestDirections;
    }

    private Direction getNewDirection(DependencyProvider dependencyProvider) {
        Direction newDirection = NewDirectionProcessor.processNewDirection(dependencyProvider);

        LOG.info("The Processed Direction: " + newDirection);

        return newDirection;
    }

}
