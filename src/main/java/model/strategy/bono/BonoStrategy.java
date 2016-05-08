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

    SimpleDirectionContainer<Direction> freeDirections = new SimpleDirectionContainer<>();
    SimpleDirectionContainer<Direction> closedDirections = new SimpleDirectionContainer<>();
    SimpleDirectionContainer<Direction> filteredDirections = new SimpleDirectionContainer<>();

    Set<Coordinate> alreadyCheckedCoordinatesTemp = new HashSet<>();
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
        System.out.println("Head: " + snake.getHeadCoordinate());

        newDirection = process();

        System.out.println("--- END " + snake.getName() + "---");

        return newDirection;
    }

    private Direction process()
    {
        Direction newDirection = null;

        BlockingDirectionProcessor blockingDirectionProcessor = new BlockingDirectionProcessor(
                snake, arena);

        BlockingDirectionContainer blockingDirections = new BlockingDirectionContainer();

        Map<Integer, SimpleDirectionContainer<Direction>> distancesToFood = new TreeMap<>();

        SimpleDirectionContainer<Direction> equivalentBestDirections = new SimpleDirectionContainer<>();
        SimpleDirectionContainer<Direction> allValidDirections = new SimpleDirectionContainer<>();

        blockingDirections = blockingDirectionProcessor
                .process(actualHeadCoordinate);

        distancesToFood = getDistancesToFood();

        equivalentBestDirections = getEquivalentBestDirections(distancesToFood);
        allValidDirections = getAllValidDirections(distancesToFood);

        newDirection = getNewDirection(blockingDirections,
                equivalentBestDirections, allValidDirections);

        return newDirection;
    }

    private void processFilteredDirections()
    {
        processFreeDirections();

        processClosedDirections();

        filteredDirections = freeDirections.getAsNewObject();
        filteredDirections.removeAll(closedDirections);
    }

    private void processFreeDirections()
    {
        for (Direction actualDirection : Direction.values()) {
            Coordinate nextCoordinate = arena
                    .nextCoordinate(actualHeadCoordinate, actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                freeDirections.add(actualDirection);
            }
        }
    }

    private void processClosedDirections()
    {
        Map<Direction, Integer> freeCoordinatesCountByDirection = new HashMap<>();

        for (Direction actualDirection : freeDirections.getAllAsList()) {
            Coordinate nextCoordinate = arena
                    .nextCoordinate(actualHeadCoordinate, actualDirection);

            if (isALoop(nextCoordinate, null)) {
                Integer freeCoordinatesCount = getFreeCoordinatesCount(
                        nextCoordinate);
                if (freeCoordinatesCount == 0) {
                    closedDirections.add(actualDirection);
                } else {
                    freeCoordinatesCountByDirection.put(actualDirection,
                            freeCoordinatesCount);
                }
            }
        }

        if (freeCoordinatesCountByDirection.size() > 1) {
            /* alternate: a legnyagyobbon kívül mindet visszaadni... */
            Integer minCount = Integer.MAX_VALUE;
            Direction minDirection = null;

            for (Direction key : freeCoordinatesCountByDirection.keySet()) {
                if (freeCoordinatesCountByDirection.get(key) < minCount) {
                    minCount = freeCoordinatesCountByDirection.get(key);
                    minDirection = key;
                }
            }

            if (minDirection != null) {
                closedDirections.add(minDirection);
            }
        }
    }

    private boolean isALoop(Coordinate headCoordinate, Coordinate nextStep)
    {
        Coordinate coordinateToInvestigate;

        if (nextStep == null) {
            alreadyCheckedCoordinatesTemp.clear();

            coordinateToInvestigate = headCoordinate;
        } else {
            if (alreadyCheckedCoordinatesTemp.contains(nextStep)) {
                return false;
            }

            if (nextStep.equals(headCoordinate)) {
                return true;
            }

            alreadyCheckedCoordinatesTemp.add(nextStep);

            coordinateToInvestigate = nextStep;
        }

        for (Direction actualDirection : Direction.values()) {
            Coordinate nextCoordinate = arena
                    .nextCoordinate(coordinateToInvestigate, actualDirection);

            if (arena.isOccupied(nextCoordinate)
                    && isALoop(headCoordinate, nextCoordinate)) {
                return true;
            }
        }

        return false;
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

    private Map<Integer, SimpleDirectionContainer<Direction>> getDistancesToFood()
    {
        Map<Integer, SimpleDirectionContainer<Direction>> distancesToFood = new TreeMap<>();

        for (Direction actualDirection : Direction.values()) {
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

    private SimpleDirectionContainer<Direction> getAllValidDirections(
            Map<Integer, SimpleDirectionContainer<Direction>> distancesToFood)
    {
        SimpleDirectionContainer<Direction> allValidDirections = new SimpleDirectionContainer<>();

        List<SimpleDirectionContainer<Direction>> directionContainers = new ArrayList<>();

        if (distancesToFood.size() > 0) {
            directionContainers.addAll(distancesToFood.values());

            for (SimpleDirectionContainer<Direction> directionContainer : directionContainers) {
                allValidDirections.addAll(directionContainer);
            }
        }

        System.out.println("All Valid Directions: " + allValidDirections);

        return allValidDirections;
    }

    private Direction getNewDirection(
            BlockingDirectionContainer blockingDirections,
            SimpleDirectionContainer<Direction> equivalentBestDirections,
            SimpleDirectionContainer<Direction> allValidDirections)
    {
        Direction newDirection = null;

        DependencyProvider dependencyProvider = new DependencyProvider(arena,
                snake, blockingDirections, equivalentBestDirections,
                allValidDirections);

        boolean testDirectBlocks = true;

        do {
            newDirection = NewDirectionProcessor
                    .processNewDirection(dependencyProvider, testDirectBlocks);

            if (testDirectBlocks == false && newDirection == null) {
                newDirection = NewDirectionProcessor
                        .processLastChanceNewDirection(dependencyProvider,
                                testDirectBlocks);
            }

            testDirectBlocks = false;
        } while (newDirection == null);

        System.out.println("The processed Direction: " + newDirection);

        return newDirection;
    }
}
