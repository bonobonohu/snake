package model.strategy.bono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import model.Arena;
import model.Coordinate;
import model.Direction;
import model.Snake;
import model.strategy.SnakeStrategy;
import model.strategy.bono.closeddirectionsprocessingstrategies.ClosedDirectionsProcessingStrategy;
import model.strategy.bono.closeddirectionsprocessingstrategies.MinimumsExceptZerosAndZerosStrategy;
import model.strategy.bono.directionhandlers.BlockingDirectionContainer;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;
import model.strategy.bono.newdirectionprocessors.DependencyProvider;
import model.strategy.bono.newdirectionprocessors.NewDirectionProcessor;

public class BonoStrategy implements SnakeStrategy
{
    private static final boolean PRINT_LOGS = true;
    private static final ClosedDirectionsProcessingStrategy CLOSED_DIRECTIONS_PROCESSING_STRATEGY = new MinimumsExceptZerosAndZerosStrategy();

    private Arena arena;
    private Snake snake;

    private Coordinate actualHeadCoordinate;
    private Coordinate foodCoordinate;
    private Coordinate maxCoordinate;

    Printer printer = new Printer(PRINT_LOGS);

    Set<Coordinate> freeCoordinatesTemp = new HashSet<>();

    @Override
    public Direction nextMove(Snake snakeArgument, Arena arenaArgument)
    {
        Direction newDirection = null;

        arena = arenaArgument;
        snake = snakeArgument;

        printer.print("--- BEGIN " + snake.getName() + "---");

        printer.print("Length: " + snake.length());

        actualHeadCoordinate = snake.getHeadCoordinate();
        foodCoordinate = arena.getFood().get(0).getCoordinate();
        maxCoordinate = arena.getMaxCoordinate();

        printer.print("Food: " + arena.getFood().get(0).getCoordinate());
        printer.print("Head: " + actualHeadCoordinate);

        newDirection = process();

        printer.print("--- END " + snake.getName() + "---");

        return newDirection;
    }

    private Direction process()
    {
        Direction newDirection = null;

        SimpleDirectionContainer<Direction> freeDirections = getFreeDirections();
        SimpleDirectionContainer<Direction> closedDirections = getClosedDirections(
                freeDirections, CLOSED_DIRECTIONS_PROCESSING_STRATEGY);
        SimpleDirectionContainer<Direction> filteredDirections = getFilteredDirections(
                freeDirections, closedDirections);

        BlockingDirectionProcessor blockingDirectionProcessor = new BlockingDirectionProcessor(
                snake, arena, printer);
        BlockingDirectionContainer blockingDirections = blockingDirectionProcessor
                .process(actualHeadCoordinate, filteredDirections);

        Map<Integer, SimpleDirectionContainer<Direction>> distancesToFood = getDistancesToFood(
                filteredDirections);
        SimpleDirectionContainer<Direction> equivalentBestDirections = getEquivalentBestDirections(
                distancesToFood);

        DependencyProvider dependencyProvider = new DependencyProvider(arena,
                snake, blockingDirections, filteredDirections,
                equivalentBestDirections, printer);

        newDirection = getNewDirection(dependencyProvider);

        return newDirection;
    }

    private SimpleDirectionContainer<Direction> getFilteredDirections(
            SimpleDirectionContainer<Direction> freeDirections,
            SimpleDirectionContainer<Direction> closedDirections)
    {
        SimpleDirectionContainer<Direction> filteredDirections = new SimpleDirectionContainer<>();

        filteredDirections = freeDirections.getAsNewObject();
        filteredDirections.removeAll(closedDirections);

        printer.print("Filtered Directions: " + filteredDirections);

        return filteredDirections;
    }

    private SimpleDirectionContainer<Direction> getFreeDirections()
    {
        SimpleDirectionContainer<Direction> freeDirections = new SimpleDirectionContainer<>();

        for (Direction actualDirection : Direction.values()) {
            Coordinate nextCoordinate = arena
                    .nextCoordinate(actualHeadCoordinate, actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                freeDirections.add(actualDirection);
            }
        }

        printer.print("Free Directions: " + freeDirections);

        return freeDirections;
    }

    private SimpleDirectionContainer<Direction> getClosedDirections(
            SimpleDirectionContainer<Direction> freeDirections,
            ClosedDirectionsProcessingStrategy strategy)
    {
        SimpleDirectionContainer<Direction> closedDirections = new SimpleDirectionContainer<>();

        Map<Direction, Integer> freeCoordinatesCountByDirection = getFreeCoordinatesCountByDirection();

        if (allAreTheSame(freeCoordinatesCountByDirection)) {
            freeCoordinatesCountByDirection.clear();
        }

        if (freeCoordinatesCountByDirection.size() > 1) {
            closedDirections
                    .addAll(strategy.process(freeCoordinatesCountByDirection));
        }

        printer.print("Closed Directions: " + closedDirections);

        return closedDirections;
    }

    private Map<Direction, Integer> getFreeCoordinatesCountByDirection()
    {
        Map<Direction, Integer> freeCoordinatesCountByDirection = new HashMap<>();

        SimpleDirectionContainer<Direction> randomizableDirections = new SimpleDirectionContainer<>();
        randomizableDirections.addAll(Arrays.asList(Direction.values()));

        for (Direction actualDirection : randomizableDirections
                .getRandomizedElementsAsList()) {
            Coordinate nextCoordinate = arena
                    .nextCoordinate(actualHeadCoordinate, actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                Integer freeCoordinatesCountForADirection = getFreeCoordinatesCountForADirection(
                        nextCoordinate);

                freeCoordinatesCountByDirection.put(actualDirection,
                        freeCoordinatesCountForADirection);
            }
        }

        printer.print("Free Coordinates Count By Direction "
                + freeCoordinatesCountByDirection);

        return freeCoordinatesCountByDirection;
    }

    private Integer getFreeCoordinatesCountForADirection(
            Coordinate headCoordinate)
    {
        freeCoordinatesTemp.clear();

        for (Direction actualDirection : Direction.values()) {
            Coordinate nextCoordinate = arena.nextCoordinate(headCoordinate,
                    actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                processFreeCoordinatesTemp(nextCoordinate);
            }
        }

        return freeCoordinatesTemp.size();
    }

    private void processFreeCoordinatesTemp(Coordinate freeCoordinate)
    {
        if (freeCoordinatesTemp.contains(freeCoordinate)) {
            return;
        } else {
            freeCoordinatesTemp.add(freeCoordinate);
        }

        for (Direction actualDirection : Direction.values()) {
            Coordinate nextCoordinate = arena.nextCoordinate(freeCoordinate,
                    actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                processFreeCoordinatesTemp(nextCoordinate);
            }
        }
    }

    private boolean allAreTheSame(
            Map<Direction, Integer> freeCoordinatesCountByDirection)
    {
        boolean allTheSame = true;

        Integer theCount = Integer.MIN_VALUE;
        for (Direction direction : freeCoordinatesCountByDirection.keySet()) {
            if (theCount == Integer.MIN_VALUE) {
                theCount = freeCoordinatesCountByDirection.get(direction);
            } else if (!theCount
                    .equals(freeCoordinatesCountByDirection.get(direction))) {
                allTheSame = false;
                break;
            }
        }

        return allTheSame;
    }

    private Map<Integer, SimpleDirectionContainer<Direction>> getDistancesToFood(
            SimpleDirectionContainer<Direction> filteredDirections)
    {
        Map<Integer, SimpleDirectionContainer<Direction>> distancesToFood = new TreeMap<>();

        for (Direction actualDirection : filteredDirections) {
            Coordinate nextCoordinate = arena
                    .nextCoordinate(actualHeadCoordinate, actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                int actualDistanceToFood = nextCoordinate
                        .minDistance(foodCoordinate, maxCoordinate);

                if (distancesToFood.containsKey(actualDistanceToFood)) {
                    SimpleDirectionContainer<Direction> directions = distancesToFood
                            .get(actualDistanceToFood);
                    directions.add(actualDirection);

                    distancesToFood.put(actualDistanceToFood, directions);
                } else {
                    SimpleDirectionContainer<Direction> directions = new SimpleDirectionContainer<>();
                    directions.add(actualDirection);

                    distancesToFood.put(actualDistanceToFood, directions);
                }
            }
        }

        printer.print("Distances To Food: " + distancesToFood);

        return distancesToFood;
    }

    private SimpleDirectionContainer<Direction> getEquivalentBestDirections(
            Map<Integer, SimpleDirectionContainer<Direction>> distancesToFood)
    {
        SimpleDirectionContainer<Direction> equivalentBestDirections = new SimpleDirectionContainer<>();

        List<SimpleDirectionContainer<Direction>> directionContainers = new ArrayList<>();

        if (distancesToFood.size() > 0) {
            directionContainers.addAll(distancesToFood.values());
        } else {
            directionContainers.add(null);
        }

        equivalentBestDirections = directionContainers.get(0);

        printer.print(
                "Equivalent Best Directions: " + equivalentBestDirections);

        return equivalentBestDirections;
    }

    private Direction getNewDirection(DependencyProvider dependencyProvider)
    {
        Direction newDirection = NewDirectionProcessor
                .processNewDirection(dependencyProvider);

        printer.print("The Processed Direction: " + newDirection);

        return newDirection;
    }
}
