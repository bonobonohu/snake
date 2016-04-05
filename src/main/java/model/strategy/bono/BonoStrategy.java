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
import model.strategy.bono.directionhandlers.DirectionContainer;
import model.strategy.bono.directionhandlers.DirectionData;
import model.strategy.bono.distanceprocessors.DistanceProcessor;
import model.strategy.bono.newdirectionprocessors.ByBlockingDistances;
import model.strategy.bono.newdirectionprocessors.ByEquivalentBestDirections;
import model.strategy.bono.newdirectionprocessors.ByFreeEquivalentBestDirections;
import model.strategy.bono.newdirectionprocessors.ByFreeValidDirections;
import model.strategy.bono.newdirectionprocessors.ByKispalEsABorz;
import model.strategy.bono.newdirectionprocessors.NewDirectionProcessor;

public class BonoStrategy implements SnakeStrategy
{
    private Snake snake;
    private Arena arena;

    @Override
    public Direction nextMove(Snake snakeArgument, Arena arenaArgument)
    {
        snake = snakeArgument;
        arena = arenaArgument;

        System.out.println("--- BEGIN " + snake.getName() + "---");

        Direction newDirection = null;

        Coordinate actualHeadCoordinate = snake.getHeadCoordinate();
        Coordinate foodCoordinate = arena.getFood().get(0).getCoordinate();
        Coordinate maxCoordinate = arena.getMaxCoordinate();

        DirectionData blockingDirectionsData = new DirectionData();

        Map<Integer, DirectionContainer<Direction>> distancesToFood = new TreeMap<>();
        DirectionContainer<Direction> allValidDirections = new DirectionContainer<>();
        DirectionContainer<Direction> equivalentBestDirections = new DirectionContainer<>();

        System.out.println("Food: " + arena.getFood().get(0).getCoordinate());
        System.out.println("Head: " + snake.getHeadCoordinate());

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

                        if (isValidBlock(actualHeadCoordinate, blockingSnake)) {
                            int blockingTailLength = getBlockingTailLength(
                                    coordinateToInvestigate, blockingSnake);

                            DistanceProcessor distanceProcessor = DistanceProcessor
                                    .getDistanceProcessor(actualDirection);
                            int distance = distanceProcessor.getDistance(
                                    actualHeadCoordinate,
                                    coordinateToInvestigate, maxCoordinate);

                            if (isBlockingRisk(distance, blockingTailLength)) {
                                blockingDirectionsData.putData(actualDirection,
                                        distance, coordinateToInvestigate);
                            }
                        }
                    }
                }

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

        List<DirectionContainer<Direction>> directionContainers = new ArrayList<>();

        if (distancesToFood.size() > 0) {
            directionContainers.addAll(distancesToFood.values());

            for (DirectionContainer<Direction> directionContainer : directionContainers) {
                allValidDirections.addAll(directionContainer);
            }
        } else {
            directionContainers.add(null);

            System.out.println(
                    "All directions are blocked directly. We are in deep shit.");
        }

        System.out.println("All Valid Directions: " + allValidDirections);

        equivalentBestDirections = directionContainers.get(0);

        System.out.println(
                "Equivalent Best Directions: " + equivalentBestDirections);

        System.out.println("Blocking Directions: " + blockingDirectionsData);

        List<NewDirectionProcessor> newDirectionProcessors = new ArrayList<>();
        newDirectionProcessors.add(new ByEquivalentBestDirections());
        newDirectionProcessors.add(new ByFreeEquivalentBestDirections());
        newDirectionProcessors.add(new ByFreeValidDirections());
        newDirectionProcessors.add(new ByBlockingDistances());
        newDirectionProcessors.add(new ByKispalEsABorz());

        for (NewDirectionProcessor newDirectionProcessor : newDirectionProcessors) {
            if (newDirection == null) {
                newDirectionProcessor
                        .setBlockingDirectionsData(blockingDirectionsData);
                newDirectionProcessor.setAllValidDirections(allValidDirections);
                newDirectionProcessor
                        .setEquivalentBestDirections(equivalentBestDirections);

                newDirection = newDirectionProcessor.getNewDirection();
            }
        }

        System.out.println("--- END " + snake.getName() + "---");

        return newDirection;
    }

    private boolean isBlockingRisk(int distance, int blockingTailLength)
    {
        return blockingTailLength >= distance;
    }

    private int getBlockingTailLength(Coordinate nextCoordinateToInvestigate,
            Snake blockingSnake)
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

    private boolean isValidBlock(Coordinate actualHeadCoordinate,
            Snake blockingSnake)
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
}
