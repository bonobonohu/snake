package model;

public class Food implements Member {

    private final Coordinate coordinate;

    public Food(final Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public boolean occupies(Coordinate nextCoordinate) {
        return coordinate.equals(nextCoordinate);
    }

    @Override
    public String toString() {
        return "Food [coordinate=" + coordinate + "]";
    }

}
