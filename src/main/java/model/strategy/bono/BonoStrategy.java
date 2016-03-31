package model.strategy.bono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.Arena;
import model.Coordinate;
import model.Direction;
import model.Snake;
import model.strategy.SnakeStrategy;

public class BonoStrategy implements SnakeStrategy
{
    private Snake snake;
    private Arena arena;

    @Override
    public Direction nextMove(Snake snakeArgument, Arena arenaArgument)
    {
        // dummy commit for Jenkins testing. sorry.
        System.out.println("JENKINS HAPPENS.");
        snake = snakeArgument;
        arena = arenaArgument;

        System.out.println("--- BEGIN " + snake.getName() + "---");

        Direction newDirection = Direction.SOUTH;

        Coordinate actualHeadCoordinate = snake.getHeadCoordinate();
        Coordinate foodCoordinate = arena.getFood().get(0).getCoordinate();
        Coordinate maxCoordinate = arena.getMaxCoordinate();

        DirectionData blockingDirectionsData = new DirectionData();

        Map<Integer, DirectionContainer<Direction>> distancesToFood = new TreeMap<>();
        DirectionContainer<Direction> allValidDirections = new DirectionContainer<>();
        DirectionContainer<Direction> equivalentBestDirections = new DirectionContainer<>();

        System.out.println("Head: " + snake.getHeadCoordinate());
        System.out.println("Food: " + arena.getFood().get(0).getCoordinate());

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

        if (distancesToFood.size() > 0) {
            List<DirectionContainer<Direction>> directionContainers = new ArrayList<>();
            directionContainers.addAll(distancesToFood.values());

            System.out.println("Directions: " + directionContainers);

            for (DirectionContainer<Direction> directionContainer : directionContainers) {
                allValidDirections.addAll(directionContainer);
            }

            System.out.println("All Valid Directions: " + allValidDirections);

            equivalentBestDirections = directionContainers.get(0);

            System.out.println(
                    "Equivalent Best Directions: " + equivalentBestDirections);

            if (blockingDirectionsData.size() > 0) {
                System.out.println(
                        "Blocking Directions: " + blockingDirectionsData);

                DirectionContainer<Direction> freeEquivalentBestDirections = equivalentBestDirections
                        .getAsNewObject();
                freeEquivalentBestDirections
                        .removeAll(blockingDirectionsData.getDirections());

                if (freeEquivalentBestDirections.size() > 0) {
                    newDirection = freeEquivalentBestDirections
                            .getRandomElement();

                    System.out.println(
                            "Random element from the free equivalent best directions: "
                                    + newDirection);
                } else {
                    DirectionContainer<Direction> freeValidDirections = allValidDirections
                            .getAsNewObject();
                    freeValidDirections
                            .removeAll(blockingDirectionsData.getDirections());

                    if (freeValidDirections.size() > 0) {
                        newDirection = freeValidDirections.getRandomElement();

                        System.out.println(
                                "Random element from the free valid directions: "
                                        + newDirection);
                    } else {
                        Map<Integer, DirectionContainer<Direction>> orderedBlockings = new TreeMap<>(
                                Collections.reverseOrder());

                        for (Map.Entry<Direction, Integer> entry : blockingDirectionsData
                                .getDistanceToDirectionsEntrySet()) {
                            if (orderedBlockings
                                    .containsKey(entry.getValue())) {
                                DirectionContainer<Direction> directionsTemp = orderedBlockings
                                        .get(entry.getValue());
                                directionsTemp.add(entry.getKey());

                                orderedBlockings.put(entry.getValue(),
                                        directionsTemp);
                            } else {
                                DirectionContainer<Direction> directionsTemp = new DirectionContainer<>();
                                directionsTemp.add(entry.getKey());

                                orderedBlockings.put(entry.getValue(),
                                        directionsTemp);
                            }
                        }

                        boolean foundNewDirection = false;
                        Iterator<Map.Entry<Integer, DirectionContainer<Direction>>> orderedBlockingsEntrySetIterator = orderedBlockings
                                .entrySet().iterator();
                        while (!foundNewDirection
                                && orderedBlockingsEntrySetIterator.hasNext()) {
                            Map.Entry<Integer, DirectionContainer<Direction>> blockingsTemp = orderedBlockingsEntrySetIterator
                                    .next();

                            DirectionContainer<Direction> blockingDirectionsTemp = blockingsTemp
                                    .getValue();

                            int numOfTries = 0;
                            Direction randomDirection;
                            do {
                                randomDirection = blockingDirectionsTemp
                                        .getRandomElement();

                                if (randomDirection != null) {
                                    newDirection = randomDirection;
                                    foundNewDirection = true;
                                }

                                numOfTries++;
                            } while (!foundNewDirection || (allValidDirections
                                    .contains(randomDirection)
                                    && numOfTries < allValidDirections.size()));
                        }

                        System.out.println(
                                "Weighted element by blocking distances: "
                                        + newDirection);
                    }
                }
            } else {
                newDirection = equivalentBestDirections.getRandomElement();

                System.out.println(
                        "Random element from the equivalent best directions: "
                                + newDirection);
            }
        } else {
            System.out.println(
                    "All directions are blocked directly. We are in deep shit.");
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
