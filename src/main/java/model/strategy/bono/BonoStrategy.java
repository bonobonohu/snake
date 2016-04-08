package model.strategy.bono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.Arena;
import model.Coordinate;
import model.Direction;
import model.Snake;
import model.strategy.SnakeStrategy;
import model.strategy.bono.directionhandlers.BlockingDirectionContainer;
import model.strategy.bono.directionhandlers.DirectionContainer;
import model.strategy.bono.distanceprocessors.DistanceProcessor;
import model.strategy.bono.newdirectionprocessors.ByBlockingDistances;
import model.strategy.bono.newdirectionprocessors.ByEquivalentBestDirections;
import model.strategy.bono.newdirectionprocessors.ByFreeEquivalentBestDirections;
import model.strategy.bono.newdirectionprocessors.ByFreeValidDirections;
import model.strategy.bono.newdirectionprocessors.ByKispalEsABorz;
import model.strategy.bono.newdirectionprocessors.DependencyProvider;
import model.strategy.bono.newdirectionprocessors.NewDirectionProcessor;

public class BonoStrategy implements SnakeStrategy
{
    private Arena arena;
    private Snake snake;

    private Coordinate actualHeadCoordinate;
    private Coordinate foodCoordinate;
    private Coordinate maxCoordinate;

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

        Map<Integer, DirectionContainer<Direction>> distancesToFood = new TreeMap<>();

        BlockingDirectionContainer blockingDirections = new BlockingDirectionContainer();

        DirectionContainer<Direction> equivalentBestDirections = new DirectionContainer<>();
        DirectionContainer<Direction> allValidDirections = new DirectionContainer<>();

        blockingDirections = processBlockingDirections();

        distancesToFood = processDistancesToFood();

        equivalentBestDirections = processEquivalentBestDirections(
                distancesToFood);
        allValidDirections = processAllValidDirections(distancesToFood);

        newDirection = processNewDirection(blockingDirections,
                equivalentBestDirections, allValidDirections);

        return newDirection;
    }

    private Map<Integer, DirectionContainer<Direction>> processDistancesToFood()
    {
        Map<Integer, DirectionContainer<Direction>> distancesToFood = new TreeMap<>();

        for (Direction actualDirection : Direction.values()) {
            Coordinate nextCoordinate = arena
                    .nextCoordinate(actualHeadCoordinate, actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                int actualDistanceToFood = nextCoordinate
                        .minDistance(foodCoordinate, maxCoordinate);

                if (distancesToFood.containsKey(actualDistanceToFood)) {
                    DirectionContainer<Direction> directions = distancesToFood
                            .get(actualDistanceToFood);
                    directions.add(actualDirection);

                    distancesToFood.put(actualDistanceToFood, directions);
                } else {
                    DirectionContainer<Direction> directions = new DirectionContainer<>();
                    directions.add(actualDirection);

                    distancesToFood.put(actualDistanceToFood, directions);
                }
            }
        }

        return distancesToFood;
    }

    private BlockingDirectionContainer processBlockingDirections()
    {
        BlockingDirectionContainer blockingDirections = new BlockingDirectionContainer();

        for (Direction actualDirection : Direction.values()) {
            Coordinate nextCoordinate = arena
                    .nextCoordinate(actualHeadCoordinate, actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                Coordinate coordinateToInvestigate = actualHeadCoordinate;

                for (int i = 0; i < 49; i++) {
                    coordinateToInvestigate = arena.nextCoordinate(
                            coordinateToInvestigate, actualDirection);

                    if (arena.isOccupied(coordinateToInvestigate)) {
                        Snake blockingSnake = getBlockingSnake(
                                coordinateToInvestigate);

                        if (isValidBlock(blockingSnake)) {
                            int blockingTailLength = getBlockingTailLength(
                                    blockingSnake, coordinateToInvestigate);

                            DistanceProcessor distanceProcessor = DistanceProcessor
                                    .getDistanceProcessor(actualDirection);
                            int distanceToBlock = distanceProcessor.getDistance(
                                    actualHeadCoordinate,
                                    coordinateToInvestigate, maxCoordinate);

                            if (isBlockingRisk(blockingTailLength, distanceToBlock)) {
                                blockingDirections.putData(distanceToBlock,
                                        actualDirection, coordinateToInvestigate);
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Blocking Directions: " + blockingDirections);

        return blockingDirections;
    }

    private Snake getBlockingSnake(Coordinate blockingCoordinate)
    {
        Snake blockingSnake = null;

        List<Snake> snakes = arena.getSnakes();
        for (Snake actualSnake : snakes) {
            List<Coordinate> bodyItems = actualSnake.getBodyItems();

            for (Coordinate bodyItem : bodyItems) {
                if (bodyItem.equals(blockingCoordinate)) {
                    blockingSnake = actualSnake;
                }
            }
        }

        return blockingSnake;
    }

    private boolean isValidBlock(Snake blockingSnake)
    {
        boolean validBlock = false;

        if (blockingSnake.equals(snake)) {
            boolean allXsAreTheSame = true;
            boolean allYsAreTheSame = true;

            for (Coordinate actualBodyItem : snake.getBodyItems()) {
                if (actualBodyItem.getX() != actualHeadCoordinate.getX()) {
                    allXsAreTheSame = false;
                }
                if (actualBodyItem.getY() != actualHeadCoordinate.getY()) {
                    allYsAreTheSame = false;
                }
            }

            if ((!allXsAreTheSame && !allYsAreTheSame)
                    || (allXsAreTheSame && snake.length() == arena
                            .getMaxCoordinate().getY())
                    || (allYsAreTheSame && snake.length() == arena
                            .getMaxCoordinate().getX())) {
                validBlock = true;
            }
        } else {
            validBlock = true;
        }

        return validBlock;
    }

    private int getBlockingTailLength(Snake blockingSnake,
            Coordinate nextCoordinateToInvestigate)
    {
        boolean reachedTheBlockingPart = false;
        int blockingTailLength = 0;

        for (Coordinate actualBodyItem : blockingSnake.getBodyItems()) {
            if (actualBodyItem.equals(nextCoordinateToInvestigate)) {
                reachedTheBlockingPart = true;
            }

            if (reachedTheBlockingPart) {
                blockingTailLength++;
            }
        }

        return blockingTailLength;
    }

    private boolean isBlockingRisk(int blockingTailLength, int distance)
    {
        return blockingTailLength >= distance;
    }

    private DirectionContainer<Direction> processEquivalentBestDirections(
            Map<Integer, DirectionContainer<Direction>> distancesToFood)
    {
        DirectionContainer<Direction> equivalentBestDirections = new DirectionContainer<>();

        List<DirectionContainer<Direction>> directionContainers = new ArrayList<>();

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

    private DirectionContainer<Direction> processAllValidDirections(
            Map<Integer, DirectionContainer<Direction>> distancesToFood)
    {
        DirectionContainer<Direction> allValidDirections = new DirectionContainer<>();

        List<DirectionContainer<Direction>> directionContainers = new ArrayList<>();

        if (distancesToFood.size() > 0) {
            directionContainers.addAll(distancesToFood.values());

            for (DirectionContainer<Direction> directionContainer : directionContainers) {
                allValidDirections.addAll(directionContainer);
            }
        }

        System.out.println("All Valid Directions: " + allValidDirections);

        return allValidDirections;
    }

    private Direction processNewDirection(
            BlockingDirectionContainer blockingDirections,
            DirectionContainer<Direction> equivalentBestDirections,
            DirectionContainer<Direction> allValidDirections)
    {
        Direction newDirection = null;

        DependencyProvider dependencyProvider = new DependencyProvider(
                blockingDirections, equivalentBestDirections,
                allValidDirections);
        List<NewDirectionProcessor> newDirectionProcessors = new ArrayList<>();
        newDirectionProcessors
                .add(new ByEquivalentBestDirections(dependencyProvider));
        newDirectionProcessors
                .add(new ByFreeEquivalentBestDirections(dependencyProvider));
        newDirectionProcessors
                .add(new ByFreeValidDirections(dependencyProvider));
        newDirectionProcessors.add(new ByBlockingDistances(dependencyProvider));
        newDirectionProcessors.add(new ByKispalEsABorz(dependencyProvider));

        for (NewDirectionProcessor newDirectionProcessor : newDirectionProcessors) {
            if (newDirection == null) {
                newDirection = newDirectionProcessor.getNewDirection();
            }
        }

        return newDirection;
    }
}
