package model.strategy.bono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.Arena;
import model.Coordinate;
import model.Direction;
import model.Snake;
import model.strategy.SnakeStrategy;

public class UltimateKillerBonoStrategy implements SnakeStrategy
{
    private Snake snake;
    private Arena arena;

    @Override
    public Direction nextMove(Snake snakeArgument, Arena arenaArgument)
    {
        snake = snakeArgument;
        arena = arenaArgument;

        System.out.println("--- BEGIN " + snake.getName() + "---");

        Direction newDirection = Direction.SOUTH;

        Coordinate actualCoordinate = snake.getHeadCoordinate();
        Coordinate foodCoordinate = arena.getFood().get(0).getCoordinate();
        Coordinate maxCoordinate = arena.getMaxCoordinate();

        Map<Direction, Integer> blockingDirections = new HashMap<>();
        Map<Coordinate, Map<Direction, Integer>> blockingCoordinates = new HashMap<>();

        Map<Integer, DirectionsContainer<Direction>> distancesToFood = new TreeMap<>();
        DirectionsContainer<Direction> allValidDirections = new DirectionsContainer<>();
        DirectionsContainer<Direction> equivalentBestDirections = new DirectionsContainer<>();

        System.out.println("Head: " + snake.getHeadCoordinate());
        System.out.println("Food: " + arena.getFood().get(0).getCoordinate());

        for (Direction actualDirection : Direction.values()) {
            Coordinate nextCoordinate = arena.nextCoordinate(actualCoordinate,
                    actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                Coordinate coordinateToInvestigate = actualCoordinate;
                for (int i = 0; i < 49; i++) {
                    coordinateToInvestigate = arena.nextCoordinate(
                            coordinateToInvestigate, actualDirection);

                    if (arena.isOccupied(coordinateToInvestigate)) {
                        Snake blockingSnake = getBlockingSnake(
                                coordinateToInvestigate);

                        if (isValidBlock(actualCoordinate, blockingSnake)) {
                            int blockingTailLength = getBlockingTailLength(
                                    coordinateToInvestigate, blockingSnake);

                            DistanceProcessor distanceProcessor = DistanceProcessor
                                    .getDistanceProcessor(actualDirection);
                            int distance = distanceProcessor.getDistance(
                                    actualCoordinate, coordinateToInvestigate,
                                    maxCoordinate);

                            if (isBlockingRisk(distance, blockingTailLength)) {
                                if (blockingDirections
                                        .containsKey(actualDirection)) {
                                    int storedDistance = blockingDirections
                                            .get(actualDirection);
                                    if (distance < storedDistance) {
                                        blockingDirections.put(actualDirection,
                                                distance);

                                        // ha konkrétan az adott koordináta már
                                        // szerepel valahol, akkor megnézni,
                                        // hogy mekkora távval szerepel, és
                                        // mindig a legKÖZELEBBIT megtartani.
                                    }
                                } else {
                                    blockingDirections.put(actualDirection,
                                            distance);

                                    // ha konkrétan az adott koordináta már
                                    // szerepel valahol, akkor megnézni,
                                    // hogy mekkora távval szerepel, és
                                    // mindig a legKÖZELEBBIT megtartani.
                                }
                            }
                        }
                    }
                }

                int actualDistanceToFood = nextCoordinate
                        .minDistance(foodCoordinate, maxCoordinate);

                if (distancesToFood.containsKey(actualDistanceToFood)) {
                    DirectionsContainer<Direction> directions = distancesToFood
                            .get(actualDistanceToFood);
                    directions.add(actualDirection);

                    distancesToFood.put(actualDistanceToFood, directions);
                } else {
                    DirectionsContainer<Direction> directions = new DirectionsContainer<>();
                    directions.add(actualDirection);

                    distancesToFood.put(actualDistanceToFood, directions);
                }
            }
        }

        if (distancesToFood.size() > 0) {
            List<DirectionsContainer<Direction>> directionContainers = new ArrayList<>();
            directionContainers.addAll(distancesToFood.values());

            System.out.println("Directions: " + directionContainers);

            for (DirectionsContainer<Direction> directionContainer : directionContainers) {
                allValidDirections.addAll(directionContainer);
            }

            System.out.println("All Valid Directions: " + allValidDirections);

            equivalentBestDirections = directionContainers.get(0);

            System.out.println(
                    "Equivalent Best Directions: " + equivalentBestDirections);

            if (blockingDirections.size() > 0) {
                System.out
                        .println("Blocking Directions: " + blockingDirections);

                DirectionsContainer<Direction> freeEquivalentBestDirections = equivalentBestDirections
                        .getAsNewObject();
                freeEquivalentBestDirections
                        .removeAll(blockingDirections.keySet());

                if (freeEquivalentBestDirections.size() > 0) {
                    newDirection = freeEquivalentBestDirections
                            .getRandomElement();

                    System.out.println(
                            "Random element from the free equivalent best directions: "
                                    + newDirection);
                } else {
                    DirectionsContainer<Direction> freeValidDirections = allValidDirections
                            .getAsNewObject();
                    freeValidDirections.removeAll(blockingDirections.keySet());

                    if (freeValidDirections.size() > 0) {
                        newDirection = freeValidDirections.getRandomElement();

                        System.out.println(
                                "Random element from the free valid directions: "
                                        + newDirection);
                    } else {
                        Map<Integer, DirectionsContainer<Direction>> orderedBlockings = new TreeMap<>(
                                Collections.reverseOrder());

                        for (Map.Entry<Direction, Integer> entry : blockingDirections
                                .entrySet()) {
                            if (orderedBlockings
                                    .containsKey(entry.getValue())) {
                                DirectionsContainer<Direction> directionsTemp = orderedBlockings
                                        .get(entry.getValue());
                                directionsTemp.add(entry.getKey());

                                orderedBlockings.put(entry.getValue(),
                                        directionsTemp);
                            } else {
                                DirectionsContainer<Direction> directionsTemp = new DirectionsContainer<>();
                                directionsTemp.add(entry.getKey());

                                orderedBlockings.put(entry.getValue(),
                                        directionsTemp);
                            }
                        }

                        for (Map.Entry<Integer, DirectionsContainer<Direction>> blockingsTemp : orderedBlockings
                                .entrySet()) {
                            DirectionsContainer<Direction> blockingDirectionsTemp = blockingsTemp
                                    .getValue();

                            int numOfTries = 0;
                            Direction randomDirection;
                            do {
                                randomDirection = blockingDirectionsTemp
                                        .getRandomElement();

                                newDirection = randomDirection;

                                numOfTries++;
                            } while (allValidDirections
                                    .contains(randomDirection)
                                    && numOfTries < allValidDirections.size());
                            // @todo az utolsó random blocking elementtel száll
                            // ki.
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

    private boolean isValidBlock(Coordinate actualCoordinate,
            Snake blockingSnake)
    {
        boolean validBlock = false;

        if (blockingSnake.equals(snake)) {
            boolean allXsAreTheSame = true;
            boolean allYsAreTheSame = true;

            for (Coordinate actualBodyItem : snake.getBodyItems()) {
                if (actualBodyItem.getX() != actualCoordinate.getX()) {
                    allXsAreTheSame = false;
                }
                if (actualBodyItem.getY() != actualCoordinate.getY()) {
                    allYsAreTheSame = false;
                }
            }

            if (!allXsAreTheSame && !allYsAreTheSame) {
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
