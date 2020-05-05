package co.electric.snake.strategy.bonostrategy.directioncontainers;

import co.electric.snake.framework.model.Direction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class SimpleDirectionContainer extends HashSet<Direction> {

    public Direction getRandomElement() {
        List<Direction> elements = new ArrayList<>(this);
        Random randomIndex = new Random();
        return !elements.isEmpty() ? elements.get(randomIndex.nextInt(elements.size())) : null;
    }

    public List<Direction> getRandomizedElementsAsList() {
        List<Direction> elements = new ArrayList<>();

        SimpleDirectionContainer allElements = this.getAsNewObject();

        while (!allElements.isEmpty()) {
            Direction randomElement = allElements.getRandomElement();
            allElements.remove(randomElement);

            elements.add(randomElement);
        }

        return elements;
    }

    public SimpleDirectionContainer getAsNewObject() {
        SimpleDirectionContainer newDirectionsContainer = new SimpleDirectionContainer();

        newDirectionsContainer.addAll(this);

        return newDirectionsContainer;
    }

}
