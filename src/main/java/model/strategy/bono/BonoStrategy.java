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
import model.strategy.bono.closeddirectionsprocessingstrategies.AllButMaximumsStrategy;
import model.strategy.bono.closeddirectionsprocessingstrategies.ClosedDirectionsProcessingStrategy;
import model.strategy.bono.closeddirectionsprocessingstrategies.ZerosStrategy;
import model.strategy.bono.directionhandlers.BlockingDirectionContainer;
import model.strategy.bono.directionhandlers.SimpleDirectionContainer;
import model.strategy.bono.newdirectionprocessors.DependencyProvider;
import model.strategy.bono.newdirectionprocessors.NewDirectionProcessor;

public class BonoStrategy implements SnakeStrategy
{
    private Arena arena;
    private Snake snake;

    private Coordinate actualHeadCoordinate;
    private Coordinate foodCoordinate;
    private Coordinate maxCoordinate;

    Set<Coordinate> freeCoordinatesTemp = new HashSet<>();

    @Override
    public Direction nextMove(Snake snakeArgument, Arena arenaArgument)
    {
        Direction newDirection = null;

        arena = arenaArgument;
        snake = snakeArgument;

        System.out.println("--- BEGIN " + snake.getName() + "---");

        System.out.println("Length: " + snake.length());

        actualHeadCoordinate = snake.getHeadCoordinate();
        foodCoordinate = arena.getFood().get(0).getCoordinate();
        maxCoordinate = arena.getMaxCoordinate();

        System.out.println("Food: " + arena.getFood().get(0).getCoordinate());
        System.out.println("Head: " + actualHeadCoordinate);

        newDirection = process();

        System.out.println("--- END " + snake.getName() + "---");

        return newDirection;
    }

    private Direction process()
    {
        Direction newDirection = null;

        SimpleDirectionContainer<Direction> freeDirections = getFreeDirections();
        SimpleDirectionContainer<Direction> closedDirections = getClosedDirections(
                freeDirections, new AllButMaximumsStrategy());
        SimpleDirectionContainer<Direction> filteredDirections = getFilteredDirections(
                freeDirections, closedDirections);

        BlockingDirectionProcessor blockingDirectionProcessor = new BlockingDirectionProcessor(
                snake, arena);
        BlockingDirectionContainer blockingDirections = blockingDirectionProcessor
                .process(actualHeadCoordinate, filteredDirections);

        Map<Integer, SimpleDirectionContainer<Direction>> distancesToFood = getDistancesToFood(
                filteredDirections);
        SimpleDirectionContainer<Direction> equivalentBestDirections = getEquivalentBestDirections(
                distancesToFood);

        DependencyProvider dependencyProvider = new DependencyProvider(arena,
                snake, blockingDirections, filteredDirections,
                equivalentBestDirections);

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

        System.out.println("Filtered Directions: " + filteredDirections);

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

        System.out.println("Free Directions: " + freeDirections);

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
            closedDirections.addAll((new ZerosStrategy())
                    .process(freeCoordinatesCountByDirection));

            closedDirections
                    .addAll(strategy.process(freeCoordinatesCountByDirection));
        }

        System.out.println("Closed Directions: " + closedDirections);

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

        System.out.println("Free Coordinates Count By Direction "
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

        System.out.println("Distances To Food: " + distancesToFood);

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

        System.out.println(
                "Equivalent Best Directions: " + equivalentBestDirections);

        return equivalentBestDirections;
    }

    private Direction getNewDirection(DependencyProvider dependencyProvider)
    {
        Direction newDirection = NewDirectionProcessor
                .processNewDirection(dependencyProvider);

        System.out.println("The Processed Direction: " + newDirection);

        return newDirection;
    }
}
