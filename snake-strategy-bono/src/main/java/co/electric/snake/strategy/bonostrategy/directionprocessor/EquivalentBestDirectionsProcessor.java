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

        Coordinate headCoordinate = snake.getHeadCoordinate();
        Map<Integer, SimpleDirectionContainer> distancesToFood = getDistancesToFood(arena, headCoordinate, filteredDirections);

        if (!distancesToFood.isEmpty()) {
            List<SimpleDirectionContainer> directionContainers = new ArrayList<>(distancesToFood.values());
            equivalentBestDirections = directionContainers.get(0);
        }


        LOG.info("Equivalent Best Directions: " + equivalentBestDirections);

        return equivalentBestDirections;
    }

    private Map<Integer, SimpleDirectionContainer> getDistancesToFood(Arena arena, Coordinate headCoordinate, SimpleDirectionContainer filteredDirections) {
        Coordinate foodCoordinate = arena.getFoodInNewList().get(0).getCoordinate();
        Coordinate maxCoordinate = arena.getMaxCoordinate();

        Map<Integer, SimpleDirectionContainer> distancesToFood = new TreeMap<>();

        for (Direction direction : filteredDirections) {
            Coordinate nextCoordinate = arena.nextCoordinate(headCoordinate, direction);

            if (!arena.isOccupied(nextCoordinate)) {
                int distanceToFood = nextCoordinate.minDistance(foodCoordinate, maxCoordinate);

                SimpleDirectionContainer directions;
                if (!distancesToFood.containsKey(distanceToFood)) {
                    directions = new SimpleDirectionContainer();
                } else {
                    directions = distancesToFood.get(distanceToFood);
                }
                directions.add(direction);
                distancesToFood.put(distanceToFood, directions);
            }
        }

        LOG.info("Distances To Food: " + distancesToFood);

        return distancesToFood;
    }

}
