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

    Set<Coordinate> alreadyCheckedCoordinatesTemp = new HashSet<>();
    Set<Coordinate> freeCoordinatesTemp = new HashSet<>();
    boolean thereIsALoopTemp;

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

        // SimpleDirectionContainer<Direction> freeDirections =
        // getFreeDirections();
        // SimpleDirectionContainer<Direction> closedDirections =
        // getClosedDirections(
        // freeDirections);
        // SimpleDirectionContainer<Direction> filteredDirections =
        // getFilteredDirections(
        // freeDirections, closedDirections);

        BlockingDirectionProcessor blockingDirectionProcessor = new BlockingDirectionProcessor(
                snake, arena);
        BlockingDirectionContainer blockingDirections = blockingDirectionProcessor
                .process(actualHeadCoordinate);

        Map<Integer, SimpleDirectionContainer<Direction>> distancesToFood = getDistancesToFood(); // filteredDirections
        SimpleDirectionContainer<Direction> equivalentBestDirections = getEquivalentBestDirections(
                distancesToFood);
        SimpleDirectionContainer<Direction> allValidDirections = getAllValidDirections(
                distancesToFood);

        DependencyProvider dependencyProvider = new DependencyProvider(arena,
                snake, blockingDirections, equivalentBestDirections,
                allValidDirections);

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

        System.out.println("Filtered directions: " + filteredDirections);

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

        System.out.println("Free directions: " + freeDirections);

        return freeDirections;
    }

    private SimpleDirectionContainer<Direction> getClosedDirections(
            SimpleDirectionContainer<Direction> freeDirections)
    {
        SimpleDirectionContainer<Direction> closedDirections = new SimpleDirectionContainer<>();

        Map<Direction, Integer> freeCoordinatesCountByDirection = new HashMap<>();

        for (Direction actualDirection : freeDirections.getAllAsList()) {
            Coordinate nextCoordinate = arena
                    .nextCoordinate(actualHeadCoordinate, actualDirection);

            checkThereIsALoop(nextCoordinate, null, 0);
            if (thereIsALoopTemp) {
                System.out.println("Loop Found: " + actualDirection);

                for (Direction actualDirectionToCheckFreeCoordinates : Direction
                        .values()) {
                    Coordinate nextCoordinateToCheckFreeCoordinates = arena
                            .nextCoordinate(actualHeadCoordinate,
                                    actualDirectionToCheckFreeCoordinates);

                    Integer freeCoordinatesCount = getFreeCoordinatesCount(
                            nextCoordinateToCheckFreeCoordinates);

                    System.out.println(
                            "Free Coordinates Count: " + freeCoordinatesCount);

                    if (freeCoordinatesCount == 0) {
                        closedDirections
                                .add(actualDirectionToCheckFreeCoordinates);
                    } else {
                        freeCoordinatesCountByDirection.put(
                                actualDirectionToCheckFreeCoordinates,
                                freeCoordinatesCount);
                    }
                }
            }
        }

        if (freeCoordinatesCountByDirection.size() > 1) {
            /* Free Coordinates Count, Count in the Map */
            Map<Integer, Integer> multipleCounts = new HashMap<>();

            for (Direction key : freeCoordinatesCountByDirection.keySet()) {
                Integer actualCount = freeCoordinatesCountByDirection.get(key);

                if (!multipleCounts.containsKey(actualCount)) {
                    multipleCounts.put(actualCount, 1);
                } else {
                    Integer oldCount = multipleCounts.get(actualCount);

                    multipleCounts.put(actualCount, ++oldCount);
                }
            }
            SimpleDirectionContainer<Direction> multipleDirections = new SimpleDirectionContainer<>();
            for (Integer actualMultipleCount : multipleCounts.keySet()) {
                if (actualMultipleCount > 1) {
                    for (Direction key : freeCoordinatesCountByDirection
                            .keySet()) {
                        if (freeCoordinatesCountByDirection
                                .get(key) == actualMultipleCount) {
                            multipleDirections.add(key);
                        }
                    }
                }
            }
            for (Direction actualDirection : multipleDirections) {
                freeCoordinatesCountByDirection.remove(actualDirection);
            }

            if (freeCoordinatesCountByDirection.size() > 1) {
                /* csak a min. */
                {
                    Integer minCount = Integer.MAX_VALUE;
                    Direction minDirection = null;

                    for (Direction key : freeCoordinatesCountByDirection
                            .keySet()) {
                        if (freeCoordinatesCountByDirection
                                .get(key) < minCount) {
                            minCount = freeCoordinatesCountByDirection.get(key);
                            minDirection = key;
                        }
                    }

                    if (minDirection != null) {
                        closedDirections.add(minDirection);
                    }
                }

                /* maxon kívül az összes. */
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

    private void checkThereIsALoop(Coordinate headCoordinate,
            Coordinate nextStep, int recursionLevel)
    {
        Coordinate coordinateToInvestigate;

        if (nextStep == null) {
            thereIsALoopTemp = false;

            alreadyCheckedCoordinatesTemp.clear();

            coordinateToInvestigate = headCoordinate;
        } else {
            if (thereIsALoopTemp
                    || alreadyCheckedCoordinatesTemp.contains(nextStep)) {
                return;
            }

            alreadyCheckedCoordinatesTemp.add(nextStep);

            coordinateToInvestigate = nextStep;
        }

        for (Direction actualDirection : Direction.values()) {
            Coordinate nextCoordinate = arena
                    .nextCoordinate(coordinateToInvestigate, actualDirection);

            // headcoordinate-et tilos bejegyezni, mint already checkedet.
            // ne a size-ot vizsgáld! elmászhat! a rekurziós szintet nézd!
            // rekurzió első szintjén nem számít a fejes!
            // első vagy nulladik? hogy nevezzük?

            if (nextCoordinate.equals(headCoordinate) && recursionLevel > 1) {
                thereIsALoopTemp = true;

                return;
            }
            if (arena.isOccupied(nextCoordinate)) {
                checkThereIsALoop(headCoordinate, nextCoordinate,
                        ++recursionLevel);
            }
        }
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

    // SimpleDirectionContainer<Direction> filteredDirections
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

    private Direction getNewDirection(DependencyProvider dependencyProvider)
    {
        Direction newDirection = NewDirectionProcessor
                .processNewDirection(dependencyProvider);

        System.out.println("The processed Direction: " + newDirection);

        return newDirection;
    }
}
