package model.strategy;

import model.Arena;
import model.Direction;
import model.Snake;

public interface SnakeStrategy {

    Direction nextMove(Snake snake, Arena arena);

}
