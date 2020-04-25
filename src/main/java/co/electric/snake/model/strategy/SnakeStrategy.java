package co.electric.snake.model.strategy;

import co.electric.snake.model.Arena;
import co.electric.snake.model.Direction;
import co.electric.snake.model.Snake;

public interface SnakeStrategy {

    Direction nextMove(Snake snake, Arena arena);

}
