package model;

public enum Direction {

    NORTH(0, 1), EAST(1, 0), SOUTH(0, -1), WEST(-1, 0);

    private int diffX;
    private int diffY;

    private Direction(int xDiff, int yDiff) {
        this.diffX = xDiff;
        this.diffY = yDiff;
    }

    public int getDiffX() {
        return diffX;
    }

    public int getDiffY() {
        return diffY;
    }

    public static Direction getDirection(Arena arena, Coordinate start, Coordinate end, Coordinate maxCoordinate) {
        Direction result = null;
        for (int i = 0; i < Direction.values().length && result == null; i++) {
            Direction direction = Direction.values()[i];
            if (checkX(maxCoordinate, end.getX() - start.getX(), direction)
                    && checkY(maxCoordinate, end.getY() - start.getY(), direction)) {
                result = direction;
            }
        }
        if (result == null) {
        	throw new IllegalArgumentException("Not a neighbouring cell.");
        }
        return result;
    }

    private static boolean checkY(Coordinate maxCoordinate, int yDiff, Direction direction) {
        return check(yDiff, direction.diffY, maxCoordinate.getY());
    }

    private static boolean checkX(Coordinate maxCoordinate, int xDiff, Direction direction) {
        return check(xDiff, direction.diffX, maxCoordinate.getX());
    }

    private static boolean check(int diff, int directionDiff, int maxCoordinate) {
        return (diff == directionDiff) || (diff - maxCoordinate == directionDiff)
                || (maxCoordinate + diff == directionDiff);
    }

}
