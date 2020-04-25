package view;

import model.Arena;
import model.Coordinate;
import model.Food;
import model.Snake;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ArenaView extends JPanel {

    public static final int PADDING = 10;
    public static final int POINT_SIZE = 10;
    private static final long serialVersionUID = 1L;
    private static final Color[] AVAILABLE_COLORS = {Color.BLUE, Color.MAGENTA, Color.YELLOW};

    private final Arena arena;
    private final FoodDrawer foodDrawer = new FoodDrawer();
    private List<SnakeView> snakeViews = new ArrayList<>();

    public ArenaView(Arena arena) {
        this.arena = arena;
        generateSnakeViews();
    }

    private void generateSnakeViews() {
        int colorIndex = 0;
        for (Snake snake : arena.getSnakes()) {
            snakeViews.add(new SnakeView(snake, AVAILABLE_COLORS[colorIndex]));
            colorIndex = (colorIndex + 1) % AVAILABLE_COLORS.length;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        Coordinate maxCoordinate = arena.getMaxCoordinate();
        g.setColor(Color.BLACK);
        g.drawRect(PADDING, PADDING, POINT_SIZE * (maxCoordinate.getX() + 1), POINT_SIZE * (maxCoordinate.getY() + 1));
        int i = 0;
        for (SnakeView snake : snakeViews) {
            snake.draw(g);
            g.drawString(snake.getSnake().getName() + ": " + snake.getSnake().getBodyItems().size(), 30, 30 + 20 * i++);
        }
        for (Food food : arena.getFood()) {
            foodDrawer.draw(food, g);
        }
        repaint();
    }

}
