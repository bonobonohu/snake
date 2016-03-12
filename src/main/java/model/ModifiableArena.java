package model;

public class ModifiableArena extends Arena {

	public void addSnake(ModifiableSnake snake) {
		this.snakes.add(snake);
	}

	public void move() {
		printResultsIfNeeded();
		for (ModifiableSnake snake : snakes) {
			snake.move();
		}
		round++;
	}

	public void removeFood(Coordinate nextCoordinate) {
		removeFoodFromCollection(nextCoordinate);
		generateNewFood();
	}

}
