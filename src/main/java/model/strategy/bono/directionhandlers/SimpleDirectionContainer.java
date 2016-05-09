package model.strategy.bono.directionhandlers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class SimpleDirectionContainer<E> extends HashSet<E>
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

    public List<E> getRandomizedElementsAsList()
    {
        List<E> elements = new ArrayList<>();

        SimpleDirectionContainer<E> allElements = this.getAsNewObject();

        while (allElements.size() > 0) {
            E randomElement = allElements.getRandomElement();
            allElements.remove(randomElement);

            elements.add(randomElement);
        }

        return elements;
    }

    public List<E> getAllAsList()
    {
        List<E> elements = new ArrayList<>();

        elements.addAll(this);

        return elements;
    }

    public SimpleDirectionContainer<E> getAsNewObject()
    {
        SimpleDirectionContainer<E> newDirectionsContainer = new SimpleDirectionContainer<>();

        newDirectionsContainer.addAll(this);

        return newDirectionsContainer;
    }
}
