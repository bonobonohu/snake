package co.electric.snake.model.strategy.bono;

import co.electric.snake.model.Arena;
import co.electric.snake.model.Coordinate;
import co.electric.snake.model.Direction;
import co.electric.snake.model.Snake;
import co.electric.snake.model.strategy.SnakeStrategy;
import co.electric.snake.model.strategy.bono.closeddirectionsprocessors.ClosedDirectionsProcessor;
import co.electric.snake.model.strategy.bono.directioncontainers.BlockingDirectionContainer;
import co.electric.snake.model.strategy.bono.directioncontainers.SimpleDirectionContainer;
import co.electric.snake.model.strategy.bono.newdirectionprocessors.DependencyProvider;
import co.electric.snake.model.strategy.bono.newdirectionprocessors.NewDirectionProcessor;

import java.util.*;

public class BonoStrategy implements SnakeStrategy {

    private static final boolean PRINT_LOGS = true;

    private Arena arena;
    private Snake snake;

    private Coordinate actualHeadCoordinate;
    private Coordinate foodCoordinate;
    private Coordinate maxCoordinate;

    private Printer printer = new Printer(PRINT_LOGS);

    private Set<Coordinate> freeCoordinatesTemp = new HashSet<>();

    @Override
    public Direction nextMove(Snake snakeArgument, Arena arenaArgument) {
        arena = arenaArgument;
        snake = snakeArgument;

        printer.print("--- BEGIN " + snake.getName() + "---");

        printer.print("Length: " + snake.getLength());

        actualHeadCoordinate = snake.getHeadCoordinate();
        foodCoordinate = arena.getFood().get(0).getCoordinate();
        maxCoordinate = arena.getMaxCoordinate();

        printer.print("Food: " + arena.getFood().get(0).getCoordinate());
        printer.print("Head: " + actualHeadCoordinate);

        Direction newDirection = process();

        printer.print("--- END " + snake.getName() + "---");

        return newDirection;
    }

    private Direction process() {
        SimpleDirectionContainer freeDirections = getFreeDirections();
        SimpleDirectionContainer closedDirections = getClosedDirections();
        SimpleDirectionContainer filteredDirections = getFilteredDirections(freeDirections, closedDirections);

        BlockingDirectionProcessor blockingDirectionProcessor = new BlockingDirectionProcessor(snake, arena, printer);
        BlockingDirectionContainer blockingDirections = blockingDirectionProcessor.process(actualHeadCoordinate,
                filteredDirections);

        Map<Integer, SimpleDirectionContainer> distancesToFood = getDistancesToFood(filteredDirections);
        SimpleDirectionContainer equivalentBestDirections = getEquivalentBestDirections(distancesToFood);

        /**
         * @todo implement Builder pattern.
         */
        DependencyProvider dependencyProvider = new DependencyProvider(arena, snake, blockingDirections,
                filteredDirections, equivalentBestDirections, printer);

        return getNewDirection(dependencyProvider);
    }

    private SimpleDirectionContainer getFilteredDirections(SimpleDirectionContainer freeDirections,
                                                           SimpleDirectionContainer closedDirections) {
        SimpleDirectionContainer filteredDirections = freeDirections.getAsNewObject();
        filteredDirections.removeAll(closedDirections);

        printer.print("Filtered Directions: " + filteredDirections);

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

        printer.print("Free Directions: " + freeDirections);

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

        printer.print("Closed Directions: " + closedDirections);

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

        printer.print("Free Coordinates Count By Direction " + freeCoordinatesCountByDirection);

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

        printer.print("Distances To Food: " + distancesToFood);

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

        printer.print("Equivalent Best Directions: " + equivalentBestDirections);

        return equivalentBestDirections;
    }

    private Direction getNewDirection(DependencyProvider dependencyProvider) {
        Direction newDirection = NewDirectionProcessor.processNewDirection(dependencyProvider);

        printer.print("The Processed Direction: " + newDirection);

        return newDirection;
    }

}