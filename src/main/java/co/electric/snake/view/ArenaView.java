package co.electric.snake.view;

import co.electric.snake.model.Arena;
import co.electric.snake.model.Coordinate;
import co.electric.snake.model.Food;
import co.electric.snake.model.Snake;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ArenaView extends JPanel {

    public static final int PADDING = 10;
    public static final int POINT_SIZE = 10;

    private static final long serialVersionUID = 1L;
    private static final Color[] AVAILABLE_COLORS = {Color.BLUE, Color.MAGENTA, Color.YELLOW};
    private static final String FONT = "TimesRoman";

    private final List<SnakeView> snakeViews = new ArrayList<>();

    private final Arena arena;

    public ArenaView(Arena arena) {
        this.arena = arena;
        generateSnakeViews();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setFont(new Font(FONT, Font.PLAIN, 20));
        graphics.setColor(Color.BLACK);
        final Coordinate maxCoordinate = arena.getMaxCoordinate();
        graphics.drawRect(PADDING, PADDING, POINT_SIZE * (maxCoordinate.getX() + 1), POINT_SIZE * (maxCoordinate.getY() + 1));
        int i = 0;
        for (SnakeView snake : snakeViews) {
            snake.draw(graphics);
            graphics.drawString(snake.getSnake().getName() + ": " + snake.getSnake().getBodyItemsInNewList().size(), 30, 30 + 20 * i++);
        }
        for (Food food : arena.getFoodInNewList()) {
            drawFood(food, graphics);
        }
        repaint();
    }

    private void generateSnakeViews() {
        int colorIndex = 0;
        for (Snake snake : arena.getSnakesInNewList()) {
            snakeViews.add(new SnakeView(snake, AVAILABLE_COLORS[colorIndex]));
            colorIndex = (colorIndex + 1) % AVAILABLE_COLORS.length;
        }
    }

    private void drawFood(Food food, Graphics graphics) {
        graphics.setColor(Color.RED);
        final Coordinate coordinate = food.getCoordinate();
        final int x = ArenaView.PADDING + 1 + ArenaView.POINT_SIZE * coordinate.getX();
        final int y = ArenaView.PADDING + 1 + ArenaView.POINT_SIZE * coordinate.getY();
        graphics.fillRect(x, y, ArenaView.POINT_SIZE, ArenaView.POINT_SIZE);
    }

}
