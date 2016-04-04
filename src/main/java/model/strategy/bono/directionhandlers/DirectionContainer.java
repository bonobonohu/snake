package model.strategy.bono.directionhandlers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class DirectionContainer<E> extends HashSet<E>
{

    private static final long serialVersionUID = -7360462751577910244L;

    public E getRandomElement()
    {
        List<E> elements = new ArrayList<>();
        Random randomIndex = new Random();

        elements.addAll(this);

        E returnElement = elements.size() > 0
                ? elements.get(randomIndex.nextInt(elements.size())) : null;

        return returnElement;
    }

    public DirectionContainer<E> getAsNewObject()
    {
        DirectionContainer<E> newDirectionsContainer = new DirectionContainer<>();

        newDirectionsContainer.addAll(this);

        return newDirectionsContainer;
    }
}
