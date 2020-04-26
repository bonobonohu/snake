package co.electric.snake.model.strategy.bono.directioncontainers;

import co.electric.snake.model.Direction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class SimpleDirectionContainer extends HashSet<Direction> {

    private static final long serialVersionUID = -7360462751577910244L;

    public Direction getRandomElement() {
        List<Direction> elements = new ArrayList<>();
        Random randomIndex = new Random();

        elements.addAll(this);

        Direction returnElement = !elements.isEmpty() ? elements.get(randomIndex.nextInt(elements.size())) : null;

        return returnElement;
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