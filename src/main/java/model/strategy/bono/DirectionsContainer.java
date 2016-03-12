package model.strategy.bono;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class DirectionsContainer<E> extends HashSet<E>
{

    /**
     * 
     */
    private static final long serialVersionUID = -7360462751577910244L;

    public E getRandomElement()
    {
        Iterator<E> iterator = iterator();
        List<E> elements = new ArrayList<>();
        Random randomIndex = new Random();

        while (iterator.hasNext()) {
            elements.add(iterator.next());
        }

        E returnElement = elements.get(randomIndex.nextInt(elements.size()));

        return returnElement;
    }

    public DirectionsContainer<E> getAsNewObject()
    {
        DirectionsContainer<E> newDirectionsContainer = new DirectionsContainer<>();

        newDirectionsContainer.addAll(this);

        return newDirectionsContainer;
    }
}
