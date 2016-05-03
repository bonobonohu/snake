package model.strategy.bono;

import java.util.ArrayList;
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

    SimpleDirectionContainer<Direction> freeDirections = new SimpleDirectionContainer<Direction>();
    SimpleDirectionContainer<Direction> closedDirections = new SimpleDirectionContainer<Direction>();

    Set<Coordinate> alreadyCheckedCoordinatesTemp = new HashSet<Coordinate>();
    Set<Coordinate> freeCoordinatesTemp = new HashSet<Coordinate>();

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

        Map<Integer, SimpleDirectionContainer<Direction>> distancesToFood = new TreeMap<>();

        BlockingDirectionProcessor blockingDirectionProcessor = new BlockingDirectionProcessor(
                snake, arena);
        BlockingDirectionContainer blockingDirections = new BlockingDirectionContainer();

        SimpleDirectionContainer<Direction> equivalentBestDirections = new SimpleDirectionContainer<>();
        SimpleDirectionContainer<Direction> allValidDirections = new SimpleDirectionContainer<>();

        blockingDirections = blockingDirectionProcessor
                .process(actualHeadCoordinate);

        distancesToFood = processDistancesToFood();

        equivalentBestDirections = processEquivalentBestDirections(
                distancesToFood);
        allValidDirections = processAllValidDirections(distancesToFood);

        newDirection = processNewDirection(blockingDirections,
                equivalentBestDirections, allValidDirections);

        return newDirection;
    }

    private SimpleDirectionContainer<Direction> preFilterDirections()
    {
        processFreeDirections();

        processClosedDirections();

        return null;
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
        SimpleDirectionContainer<Direction> closedDirections = new SimpleDirectionContainer<Direction>();
        Set<Coordinate> freeCoordinates = new HashSet<Coordinate>();

        for (Direction actualDirection : freeDirections.getAllAsList()) {
            alreadyCheckedCoordinatesTemp.clear();

            Coordinate nextCoordinate = arena
                    .nextCoordinate(actualHeadCoordinate, actualDirection);

            if (isALoop(nextCoordinate, null)) {
                freeCoordinates = getFreeCoordinates(nextCoordinate);
            }
        }
    }

    private boolean isALoop(Coordinate headCoordinate, Coordinate nextStep)
    {
        if (alreadyCheckedCoordinatesTemp.size() != 0
                && alreadyCheckedCoordinatesTemp.contains(nextStep)) {
            return false;
        }

        if (nextStep != null) {
            alreadyCheckedCoordinatesTemp.add(nextStep);
        }

        if (nextStep.equals(headCoordinate)) {
            return true;
        }

        Coordinate coordinateToInvestigate = nextStep != null ? nextStep
                : headCoordinate;

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

    private Set<Coordinate> getFreeCoordinates(Coordinate headCoordinate)
    {
        Set<Coordinate> freeCoordinates = new HashSet<Coordinate>();

        for (Direction actualDirection : Direction.values()) {
            freeCoordinatesTemp.clear();

            Coordinate nextCoordinate = arena.nextCoordinate(headCoordinate,
                    actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                getFreeCoordinatesSetToDirection(nextCoordinate);
            }

            // itt beletenni valami directionnel ellátott konténerbe, aztán
            // lehet nézni, hogy melyik directionnek van e metszete melyikkel.
            //
            // ha több különböző van, akkor adjunk vissza üreset, meg akkor is,
            // ha mind üres. bár akkor ha mind üres, akkor azok végülis
            // megegyeznek. ha mindegyik megegyezik, akkor meg visszaadjuk azt.
            //
            // ha csinálunk négy égtáj szerint külön-külön konténert, akkor már
            // menet közben lehet nézni, hogy van e közös eleme a többivel, és
            // amint lesz közös elem, abba lehet fejezni az adott irány
            // vizsgálatát, mert már nem lehet diszjunkt.
            //
            // csak arra figyelj, nehogy belezavarodj, hogy melyik irány
            // vizsgálattal végeztél meg melyikkel nem. szóval, az éppen
            // vizsgáltat ilyenkor ürítsd ki, aztán a végén menj végig az összes
            // égtájon, és ami nem üres, az lesz, amiben megvan az összes
            // koordináta. vagy valami ilyesmi.
        }

        return null;
    }

    private void getFreeCoordinatesSetToDirection(Coordinate freeCoordinate)
    {
        if (freeCoordinatesTemp.size() != 0
                && freeCoordinatesTemp.contains(freeCoordinate)) {
            return;
        } else {
            freeCoordinatesTemp.add(freeCoordinate);
        }

        for (Direction actualDirection : Direction.values()) {
            Coordinate nextCoordinate = arena.nextCoordinate(freeCoordinate,
                    actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                getFreeCoordinatesSetToDirection(nextCoordinate);
            }
        }
    }

    private Map<Integer, SimpleDirectionContainer<Direction>> processDistancesToFood()
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

    private SimpleDirectionContainer<Direction> processEquivalentBestDirections(
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

    private SimpleDirectionContainer<Direction> processAllValidDirections(
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

    private Direction processNewDirection(
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
