package co.electric.snake.strategy.bonostrategy;

import co.electric.snake.framework.model.Direction;

import java.util.*;

public class SimpleDirectionContainer extends HashSet<Direction> {

    private static final Random RANDOM = new Random();

    public SimpleDirectionContainer() {
        super();
    }

    public SimpleDirectionContainer(Collection<? extends Direction> collection) {
        super(collection);
    }

    public SimpleDirectionContainer getElementsInANewInstance() {
        return new SimpleDirectionContainer(this);
    }

    public Direction getRandomElement() {
        final List<Direction> elements = new ArrayList<>(this);
        return !elements.isEmpty() ? elements.get(RANDOM.nextInt(elements.size())) : null;
    }

}
