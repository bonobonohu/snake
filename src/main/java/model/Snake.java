package model;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import model.strategy.SnakeStrategy;

public class Snake implements Member {
    private Deque<Coordinate> bodyItems = new LinkedList<>();
    private SnakeStrategy strategy;
    private ModifiableArena arena;
    private String name;

    public Snake(ModifiableArena arena, SnakeStrategy strategy, String name) {
        this.arena = arena;
        this.strategy = strategy;
        this.name = name;
        this.bodyItems.add(arena.generateRandomFreeCoordinate());
    }

    public List<Coordinate> getBodyItems() {
        return new ArrayList<>(bodyItems);
    }

    public Coordinate getHeadCoordinate() {
        return bodyItems.getFirst();
    }

    protected Coordinate decideNextCoordinate() {
        Direction direction = strategy.nextMove(this, arena);
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

    public Coordinate getTailCoordinate() {
        return bodyItems.getLast();
    }

    @Override
    public boolean occupies(Coordinate nextCoordinate) {
        return bodyItems.contains(nextCoordinate);
    }

    public int length() {
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
