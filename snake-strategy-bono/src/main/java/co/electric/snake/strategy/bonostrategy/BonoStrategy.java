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

    private Set<Coordinate> freeCoordinatesTemp = new HashSet<>();

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
        SimpleDirectionContainer closedDirections = getClosedDirections();
        SimpleDirectionContainer filteredDirections = getFilteredDirections(freeDirections, closedDirections);

        BlockingDirectionProcessor blockingDirectionProcessor = new BlockingDirectionProcessor(snake, arena);
        BlockingDirectionContainer blockingDirections = blockingDirectionProcessor.process(actualHeadCoordinate,
                filteredDirections);

        Map<Integer, SimpleDirectionContainer> distancesToFood = getDistancesToFood(filteredDirections);
        SimpleDirectionContainer equivalentBestDirections = getEquivalentBestDirections(distancesToFood);

        /**
         * @todo implement Builder pattern.
         */
        DependencyProvider dependencyProvider = new DependencyProvider(arena, snake, blockingDirections,
                filteredDirections, equivalentBestDirections);

        return getNewDirection(dependencyProvider);
    }

    private SimpleDirectionContainer getFilteredDirections(SimpleDirectionContainer freeDirections,
                                                           SimpleDirectionContainer closedDirections) {
        SimpleDirectionContainer filteredDirections = freeDirections.getAsNewObject();
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

    private SimpleDirectionContainer getClosedDirections() {
        SimpleDirectionContainer closedDirections = new SimpleDirectionContainer();

        Map<Direction, Integer> freeCoordinatesCountByDirection = getFreeCoordinatesCountByDirection();

        if (allAreTheSame(freeCoordinatesCountByDirection)) {
            freeCoordinatesCountByDirection.clear();
        }

        closedDirections
                .addAll(ClosedDirectionsProcessor.getStrategy().getClosedDirections(freeCoordinatesCountByDirection));

        LOG.info("Closed Directions: " + closedDirections);

        return closedDirections;
    }

    private Map<Direction, Integer> getFreeCoordinatesCountByDirection() {
        Map<Direction, Integer> freeCoordinatesCountByDirection = new HashMap<>();

        SimpleDirectionContainer randomizableDirections = new SimpleDirectionContainer();
        randomizableDirections.addAll(Arrays.asList(Direction.values()));

        for (Direction actualDirection : randomizableDirections.getRandomizedElementsAsList()) {
            Coordinate nextCoordinate = arena.nextCoordinate(actualHeadCoordinate, actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                Integer freeCoordinatesCountForADirection = getFreeCoordinatesCountForADirection(nextCoordinate);

                freeCoordinatesCountByDirection.put(actualDirection, freeCoordinatesCountForADirection);
            }
        }

        LOG.info("Free Coordinates Count By Direction " + freeCoordinatesCountByDirection);

        return freeCoordinatesCountByDirection;
    }

    private Integer getFreeCoordinatesCountForADirection(Coordinate headCoordinate) {
        freeCoordinatesTemp.clear();

        for (Direction actualDirection : Direction.values()) {
            Coordinate nextCoordinate = arena.nextCoordinate(headCoordinate, actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                processFreeCoordinatesTemp(nextCoordinate);
            }
        }

        return freeCoordinatesTemp.size();
    }

    private void processFreeCoordinatesTemp(Coordinate freeCoordinate) {
        if (freeCoordinatesTemp.contains(freeCoordinate)) {
            return;
        } else {
            freeCoordinatesTemp.add(freeCoordinate);
        }

        for (Direction actualDirection : Direction.values()) {
            Coordinate nextCoordinate = arena.nextCoordinate(freeCoordinate, actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                processFreeCoordinatesTemp(nextCoordinate);
            }
        }
    }

    private boolean allAreTheSame(Map<Direction, Integer> freeCoordinatesCountByDirection) {
        boolean allTheSame = true;

        Integer theCount = Integer.MIN_VALUE;
        for (Direction direction : freeCoordinatesCountByDirection.keySet()) {
            if (theCount == Integer.MIN_VALUE) {
                theCount = freeCoordinatesCountByDirection.get(direction);
            } else if (!theCount.equals(freeCoordinatesCountByDirection.get(direction))) {
                allTheSame = false;
                break;
            }
        }

        return allTheSame;
    }

    private Map<Integer, SimpleDirectionContainer> getDistancesToFood(SimpleDirectionContainer filteredDirections) {
        Map<Integer, SimpleDirectionContainer> distancesToFood = new TreeMap<>();

        for (Direction actualDirection : filteredDirections) {
            Coordinate nextCoordinate = arena.nextCoordinate(actualHeadCoordinate, actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                int actualDistanceToFood = nextCoordinate.minDistance(foodCoordinate, maxCoordinate);

                if (distancesToFood.containsKey(actualDistanceToFood)) {
                    SimpleDirectionContainer directions = distancesToFood.get(actualDistanceToFood);
                    directions.add(actualDirection);

                    distancesToFood.put(actualDistanceToFood, directions);
                } else {
                    SimpleDirectionContainer directions = new SimpleDirectionContainer();
                    directions.add(actualDirection);

                    distancesToFood.put(actualDistanceToFood, directions);
                }
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
