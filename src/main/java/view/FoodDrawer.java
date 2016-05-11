package view;

import java.awt.Color;
import java.awt.Graphics;

import model.Coordinate;
import model.Food;

public class FoodDrawer
{
    public void draw(Food food, Graphics g)
    {
        Coordinate coordinate = food.getCoordinate();
        g.setColor(Color.RED);
        int x = ArenaView.PADDING + 1 + ArenaView.POINT_SIZE * coordinate.getX();
        int y = ArenaView.PADDING + 1 + ArenaView.POINT_SIZE * coordinate.getY();
        g.fillRect(x, y, ArenaView.POINT_SIZE, ArenaView.POINT_SIZE);
    }
}
