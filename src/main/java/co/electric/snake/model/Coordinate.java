package co.electric.snake.model;

public final class Coordinate {

    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate nextCoordinate(Direction direction) {
        return new Coordinate(x + direction.getDiffX(), y + direction.getDiffY());
    }

    public Coordinate truncLimits(Coordinate maxCoordinates) {
        return new Coordinate((x + maxCoordinates.x) % maxCoordinates.x, (y + maxCoordinates.y) % maxCoordinates.y);
    }

    public int standardDistance(Coordinate otherCoordinate) {
        return Math.abs(x - otherCoordinate.x) + Math.abs(y - otherCoordinate.y);
    }

    public int minDistance(Coordinate otherCoordinate, Coordinate maxCoordinate) {
        int normalDistanceX = Math.abs(x - otherCoordinate.x);
        int normalDistanceY = Math.abs(y - otherCoordinate.y);
        return Math.min(normalDistanceX, maxCoordinate.x - normalDistanceX)
                + Math.min(normalDistanceY, maxCoordinate.y - normalDistanceY);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Coordinate other = (Coordinate) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Coordinate [x=" + x + ", y=" + y + "]";
    }

}
