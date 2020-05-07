package co.electric.snake.strategy.bonostrategy.directionprocessor;

import co.electric.snake.framework.model.Arena;
import co.electric.snake.framework.model.Coordinate;
import co.electric.snake.framework.model.Direction;
import co.electric.snake.framework.model.Snake;
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class EquivalentBestDirectionsProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(EquivalentBestDirectionsProcessor.class);

    public SimpleDirectionContainer getDirections(Snake snake, Arena arena, SimpleDirectionContainer filteredDirections) {
        SimpleDirectionContainer equivalentBestDirections = new SimpleDirectionContainer();

        Coordinate actualHeadCoordinate = snake.getHeadCoordinate();
        Map<Integer, SimpleDirectionContainer> distancesToFood = getDistancesToFood(arena, actualHeadCoordinate, filteredDirections);

        if (!distancesToFood.isEmpty()) {
            List<SimpleDirectionContainer> directionContainers = new ArrayList<>(distancesToFood.values());
            equivalentBestDirections = directionContainers.get(0);
        }


        LOG.info("Equivalent Best Directions: " + equivalentBestDirections);

        return equivalentBestDirections;
    }

    private Map<Integer, SimpleDirectionContainer> getDistancesToFood(Arena arena, Coordinate actualHeadCoordinate, SimpleDirectionContainer filteredDirections) {
        Coordinate foodCoordinate = arena.getFoodInNewList().get(0).getCoordinate();
        Coordinate maxCoordinate = arena.getMaxCoordinate();

        Map<Integer, SimpleDirectionContainer> distancesToFood = new TreeMap<>();

        for (Direction actualDirection : filteredDirections) {
            Coordinate nextCoordinate = arena.nextCoordinate(actualHeadCoordinate, actualDirection);

            if (!arena.isOccupied(nextCoordinate)) {
                int actualDistanceToFood = nextCoordinate.minDistance(foodCoordinate, maxCoordinate);

                SimpleDirectionContainer directions;
                if (!distancesToFood.containsKey(actualDistanceToFood)) {
                    directions = new SimpleDirectionContainer();
                } else {
                    directions = distancesToFood.get(actualDistanceToFood);
                }
                directions.add(actualDirection);
                distancesToFood.put(actualDistanceToFood, directions);
            }
        }

        LOG.info("Distances To Food: " + distancesToFood);

        return distancesToFood;
    }

}
