package model.strategy.bono;

import java.util.ArrayList;
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
                freeDirections);
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
            SimpleDirectionContainer<Direction> freeDirections)
    {
        SimpleDirectionContainer<Direction> closedDirections = new SimpleDirectionContainer<>();

        Map<Direction, Integer> freeCoordinatesCountByDirection = new HashMap<>();

        SimpleDirectionContainer<Direction> randomizableDirections = new SimpleDirectionContainer<>();
        for (Direction actualDirection : Direction.values()) {
            randomizableDirections.add(actualDirection);
        }

        for (Direction actualDirection : randomizableDirections
                .getRandomizedElementsAsList()) {
            Coordinate nextCoordinate = arena
                    .nextCoordinate(actualHeadCoordinate, actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                Integer freeCoordinatesCount = getFreeCoordinatesCount(
                        nextCoordinate);

                System.out.println("Free Coordinates Count For "
                        + actualDirection + ": " + freeCoordinatesCount);

                if (freeCoordinatesCount == 0) {
                    closedDirections.add(actualDirection);
                } else {
                    freeCoordinatesCountByDirection.put(actualDirection,
                            freeCoordinatesCount);
                }
            }
        }

        System.out.println("Free Coordinates Count By Direction Before: "
                + freeCoordinatesCountByDirection);

        if (freeCoordinatesCountByDirection.size() > 1) {
            /* Free Coordinates Count, Count in the Map */
            Map<Integer, Integer> multipleCounts = new HashMap<>();

            for (Direction key : freeCoordinatesCountByDirection.keySet()) {
                Integer actualFreeCountForDirection = freeCoordinatesCountByDirection
                        .get(key);

                if (!multipleCounts.containsKey(actualFreeCountForDirection)) {
                    multipleCounts.put(actualFreeCountForDirection, 1);
                } else {
                    Integer oldCount = multipleCounts
                            .get(actualFreeCountForDirection);

                    multipleCounts.put(actualFreeCountForDirection, ++oldCount);
                }
            }

            System.out.println("Multiple Counts: " + multipleCounts);

            SimpleDirectionContainer<Direction> multipleDirectionsToRemove = new SimpleDirectionContainer<>();
            for (Integer actualFreeCountForDirection : multipleCounts
                    .keySet()) {
                Integer actualMultipleCount = multipleCounts
                        .get(actualFreeCountForDirection);

                if (actualMultipleCount > 1) {
                    for (Direction key : freeCoordinatesCountByDirection
                            .keySet()) {
                        if (actualMultipleCount > 1
                                && actualFreeCountForDirection
                                        .equals(freeCoordinatesCountByDirection
                                                .get(key))) {
                            multipleDirectionsToRemove.add(key);

                            if (multipleCounts.size() > 1) {
                                actualMultipleCount--;
                            }
                        }
                    }
                }
            }

            System.out.println("Multiple Directions To Remove: "
                    + multipleDirectionsToRemove);

            for (Direction actualDirection : multipleDirectionsToRemove) {
                freeCoordinatesCountByDirection.remove(actualDirection);
            }

            System.out.println("Free Coordinates Count By Direction After: "
                    + freeCoordinatesCountByDirection);

            if (freeCoordinatesCountByDirection.size() > 1) {
                /* csak a mineket tiltjuk ki. */
                {
                    Integer minCount = Integer.MAX_VALUE;
                    SimpleDirectionContainer<Direction> minDirections = new SimpleDirectionContainer<>();

                    for (Direction key : freeCoordinatesCountByDirection
                            .keySet()) {
                        if (freeCoordinatesCountByDirection
                                .get(key) < minCount) {
                            minCount = freeCoordinatesCountByDirection.get(key);
                        }
                    }

                    for (Direction key : freeCoordinatesCountByDirection
                            .keySet()) {
                        if (freeCoordinatesCountByDirection
                                .get(key) == minCount) {
                            minDirections.add(key);
                        }
                    }

                    closedDirections.addAll(minDirections);
                }

                /* maxon kívül az összest kitiltjuk. */
                // {
                // Integer maxCount = Integer.MIN_VALUE;
                // Direction maxDirection = null;
                //
                // for (Direction key :
                // freeCoordinatesCountByDirection.keySet()) {
                // if (freeCoordinatesCountByDirection.get(key) > maxCount) {
                // maxCount = freeCoordinatesCountByDirection.get(key);
                // maxDirection = key;
                // }
                // }
                //
                // if (maxDirection != null) {
                // for (Direction key : freeCoordinatesCountByDirection
                // .keySet()) {
                // if (key != maxDirection) {
                // closedDirections.add(key);
                // }
                // }
                // }
                // }
            }
        }

        System.out.println("Closed Directions: " + closedDirections);

        return closedDirections;
    }

    private Integer getFreeCoordinatesCount(Coordinate headCoordinate)
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
