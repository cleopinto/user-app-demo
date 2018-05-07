package pinto.cleo.userdemo.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Created by cleo on 5/6/18.
 */
public class AlternatingIterator<E> implements Iterator<E> {

    private final Iterator<E> [] iterators;
    private final int noOfIterators;
    private int currentIndex = 0;

    public AlternatingIterator(Iterator<E>... iterators){
        if (iterators == null || iterators.length == 0){
            throw new IllegalArgumentException("At least one iterator is required");
        }
        this.iterators = iterators;
        this.noOfIterators = iterators.length;
    }

    @Override
    public boolean hasNext() {
        Optional<Iterator<E>> iterator = Arrays.stream(iterators)
                .filter(Iterator::hasNext)
                .findFirst();
        return iterator.isPresent();
    }

    @Override
    public E next() {
        E element = null;
        int noOfAttempts = 1;
        while(!iterators[currentIndex].hasNext() && noOfAttempts <= noOfIterators){
            noOfAttempts++;
            currentIndex = ((currentIndex + 1) % noOfIterators);
        }
        if (noOfAttempts > noOfIterators){
            throw new NoSuchElementException();
        }
        if(iterators[currentIndex].hasNext()){
            element = iterators[currentIndex].next();
            currentIndex = ((currentIndex + 1) % noOfIterators);
        }
        return element;
    }
}
