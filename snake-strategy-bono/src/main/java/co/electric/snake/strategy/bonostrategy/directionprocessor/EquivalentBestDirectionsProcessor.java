package co.electric.snake.strategy.bonostrategy.directionprocessor;

import co.electric.snake.framework.model.Arena;
import co.electric.snake.framework.model.Coordinate;
import co.electric.snake.framework.model.Snake;
import co.electric.snake.strategy.bonostrategy.SimpleDirectionContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class EquivalentBestDirectionsProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(EquivalentBestDirectionsProcessor.class);

    public SimpleDirectionContainer getDirections(Snake snake, Arena arena, SimpleDirectionContainer filteredDirections) {
        final Coordinate headCoordinate = snake.getHeadCoordinate();
        final Map<Integer, SimpleDirectionContainer> distancesToFood = getDistancesToFood(arena, headCoordinate, filteredDirections);
        final SimpleDirectionContainer equivalentBestDirections = distancesToFood.values().stream()
                .findFirst()
                .orElse(new SimpleDirectionContainer());
        LOG.info("Equivalent Best Directions: " + equivalentBestDirections);
        return equivalentBestDirections;
    }

    private Map<Integer, SimpleDirectionContainer> getDistancesToFood(Arena arena, Coordinate headCoordinate, SimpleDirectionContainer filteredDirections) {
        final Map<Integer, SimpleDirectionContainer> distancesToFood = new TreeMap<>();
        final Coordinate foodCoordinate = arena.getFoodInNewList().get(0).getCoordinate();
        final Coordinate maxCoordinate = arena.getMaxCoordinate();
        filteredDirections.forEach(
                direction -> {
                    final Coordinate nextCoordinate = arena.nextCoordinate(headCoordinate, direction);
                    final int distanceToFood = nextCoordinate.minDistance(foodCoordinate, maxCoordinate);
                    final SimpleDirectionContainer directions = Optional.ofNullable(distancesToFood.get(distanceToFood)).orElse(new SimpleDirectionContainer());
                    directions.add(direction);
                    distancesToFood.put(distanceToFood, directions);
                }
        );
        LOG.info("Distances To Food: " + distancesToFood);
        return distancesToFood;
    }

}
