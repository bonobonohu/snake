package co.electric.snake.model;

import co.electric.snake.model.strategy.SnakeStrategy;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Snake implements Member {

    private final Deque<Coordinate> bodyItems = new LinkedList<>();

    private final ModifiableArena arena;
    private final SnakeStrategy strategy;
    private final String name;

    public Snake(ModifiableArena arena, SnakeStrategy strategy, String name) {
        this.arena = arena;
        this.strategy = strategy;
        this.name = name;
        this.bodyItems.add(arena.generateRandomFreeCoordinate());
    }

    @Override
    public boolean occupies(Coordinate nextCoordinate) {
        return bodyItems.contains(nextCoordinate);
    }

    protected Coordinate decideNextCoordinate() {
        final Direction direction = strategy.nextMove(this, arena);
        Coordinate nextCoordinate = arena.nextCoordinate(bodyItems.getFirst(), direction);
        return nextCoordinate;
    }

    protected void moveTo(Coordinate nextCoordinate) {
        if (!arena.isFood(nextCoordinate)) {
            bodyItems.removeLast();
        } else {
            arena.removeFood(nextCoordinate);
        }
        if (arena.isOccupied(nextCoordinate)) {
            System.out.println("Snake died: " + name);
            throw new SnakeDeadException();
        }
        bodyItems.addFirst(nextCoordinate);
    }

    public List<Coordinate> getBodyItems() {
        return new ArrayList<>(bodyItems);
    }

    public Coordinate getHeadCoordinate() {
        return bodyItems.getFirst();
    }

    public Coordinate getTailCoordinate() {
        return bodyItems.getLast();
    }

    public int getLength() {
        return bodyItems.size();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Snake [bodyItems=" + bodyItems + ", strategy=" + strategy + "]";
    }

}
