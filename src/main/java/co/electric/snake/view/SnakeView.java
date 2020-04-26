package co.electric.snake.view;

import co.electric.snake.model.Coordinate;
import co.electric.snake.model.Snake;

import java.awt.*;

public class SnakeView {

    private final Snake snake;
    private final Color color;

    public SnakeView(Snake snake, Color color) {
        this.snake = snake;
        this.color = color;
    }

    public void draw(Graphics graphics) {
        graphics.setColor(color);
        boolean first = true;
        for (Coordinate bodyItem : snake.getBodyItemsInNewList()) {
            int x = ArenaView.PADDING + 1 + ArenaView.POINT_SIZE * bodyItem.getX();
            int y = ArenaView.PADDING + 1 + ArenaView.POINT_SIZE * bodyItem.getY();
            if (first) {
                graphics.setColor(Color.BLACK);
            } else {
                graphics.setColor(color);
            }
            graphics.fillRect(x, y, ArenaView.POINT_SIZE, ArenaView.POINT_SIZE);
            first = false;
        }
    }

    public Snake getSnake() {
        return snake;
    }

}
