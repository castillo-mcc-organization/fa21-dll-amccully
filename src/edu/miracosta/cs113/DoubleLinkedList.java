package edu.miracosta.cs113;

import java.util.*;

public class DoubleLinkedList<E> implements List<E> {
    // data fields
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DoubleLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public String toString() {
        Node<E> nodeRef = head;
        String result = "[";
        while(nodeRef != null) {
            result += nodeRef.data;
            if(nodeRef.next != null) {
                result += ", ";
            }
            nodeRef = nodeRef.next;
        }
        return result + "]";
    }
    /*
        add docs
     */
    private static class Node<E> {
        private E data;
        private Node<E> next = null;
        private Node<E> prev = null;

        private Node(E dataItem) {
            data = dataItem;
            ///
        }
    }

    /*
        add docs
     */
    private class DoubleListIterator implements ListIterator<E> {
        private Node<E> nextItem;
        private Node<E> lastItemReturned;
        private int index;

        public DoubleListIterator() {
            lastItemReturned = null;
            nextItem = head;
            index = 0;
        }

        public DoubleListIterator(int i) {
            if (i < 0 || i > size) {
                throw new IndexOutOfBoundsException("Invalid index " + i);
            }
            lastItemReturned = null;
            if (i == size) {
                index = size;
                nextItem = null;
            } else {
                nextItem = head;
                for (index = 0; index < i; index++) {
                    nextItem = nextItem.next;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return (nextItem != null);
        }

        @Override
        public E next() {
            if(!hasNext()) {
                throw new NoSuchElementException("No next element");
            }
            lastItemReturned = nextItem;
            nextItem = nextItem.next;
            index++;
            return lastItemReturned.data;
        }

        @Override
        public boolean hasPrevious() {
            if(size == 0) {
                return false;
            }
            return ((nextItem == null && size != 0) || nextItem.prev != null);
        }

        @Override
        public E previous() {
            if(!hasPrevious()) {
                throw new NoSuchElementException("No previous element");
            }
            if(nextItem == null) {
                nextItem = tail;
            } else {
                nextItem = nextItem.prev;
            }
            lastItemReturned = nextItem;
            index--;
            return lastItemReturned.data;
        }

        @Override
        public int nextIndex() {
            /*
                if(hasNext()) {
                    return index;
                }
                return -1;
            */
            return index;
        }

        @Override
        public int previousIndex() {
            if(hasPrevious()) {
                if (nextItem == null) {
                    return (size - 1);
                } else {
                    return (index - 1);
                }
            }
            return -1;
        }

        @Override
        public void remove() {
            if(lastItemReturned == null) {
                throw new IllegalStateException("No last element returned");
            }
            if(tail == lastItemReturned) {
                tail.prev.next = null;
                tail = tail.prev;
            }
            else if(head == lastItemReturned) {
                if(head.next == null) {
                    head = null;
                    tail = null;
                }
                else {
                    head = head.next;
                    head.prev = null;
                }
            }
            else {
                lastItemReturned.prev.next = lastItemReturned.next;
                lastItemReturned.next.prev = lastItemReturned.prev;
            }
            size--;
            index--; //?
            lastItemReturned = null;
        }

        @Override
        public void set(E o) {
            if(lastItemReturned == null) {
                throw new IllegalStateException("No last element returned");
            }
            lastItemReturned.data = o;
            lastItemReturned = null;
        }

        @Override
        public void add(E o) {
            if(head == null) {  // adding to an empty list
                head = new Node<E>(o);
                tail = head;
            }
            else if(nextItem == head) {  // adding to the head of the list
                Node<E> nodeRef = new Node<E>(o);
                nodeRef.next = head;
                head.prev = nodeRef;
                head = nodeRef;
            }
            else if(nextItem == null) {  // since we already checked for list being empty, we only need to see if nextItem is null
                Node<E> nodeRef = new Node<E>(o);
                nodeRef.prev = tail;
                tail.next = nodeRef;
                tail = nodeRef;
            }
            else {
                Node<E> nodeRef = new Node<E>(o); //*
                nodeRef.next = nextItem; //*
                nodeRef.prev = nextItem.prev; //*
                nextItem.prev.next = nodeRef; //*
                nextItem.prev = nodeRef; //*
            }
            size++;
            index++;
            lastItemReturned = null;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (head == null && size == 0);
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        /*
            if(o == null || !(o instanceof LinkedList)) {
                return false;
            }
            LinkedList oListRef = (LinkedList)o;
            if(this.size() != oListRef.size()) {
                return false;
            }
            ListIterator oIterator = oListRef.listIterator();
            ListIterator thisIterator = this.listIterator();
            for(int i = 0; i < size; i++) {
                if(!(oIterator.next().toString().equals(thisIterator.next().toString()))) {
                    return false;
                }
            }
        */
        return true;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public boolean add(E o) {
        add(size, o);
        return true; // should always be true unless some unexpected error, which would stop program at the line above
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(Collection collection) {
        return false;
    }

    @Override
    public boolean addAll(int i, Collection collection) {
        return false;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public E get(int i) {
        /*
            try {
                return (E) listIterator(i).next();
            }
            catch (NoSuchElementException ne) {
                throw new IndexOutOfBoundsException();
            }
        */
        if(i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return (E)listIterator(i).next();
    }

    @Override
    public E set(int i, E o) {

        ListIterator iteratorRef = listIterator(i);
        if(!iteratorRef.hasNext()) {
            throw new IndexOutOfBoundsException();
        }
        E obj = (E)iteratorRef.next();
        iteratorRef.set(o);
        return obj;
        /*
            ListIterator iteratorRef = listIterator(i);
            try {
                E obj = (E)listRef.next();
                iteratorRef.set(o);
                return obj;
            }
            catch(NoSuchElementException ne) {
                throw new IndexOutOfBoundsException();
            }
            --------------------------------
            E obj = get(i);
            ListIterator iteratorRef = listIterator(i);
            iteratorRef.next();
            iteratorRef.set(o);
            return obj;
        */
    }

    @Override
    public void add(int i, E obj) {
        listIterator(i).add(obj);
    }

    @Override
    public E remove(int i) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        ListIterator iteratorRef = listIterator();
        while(iteratorRef.hasNext()) {
            int num = iteratorRef.nextIndex();
            if(o.equals(iteratorRef.next())) {
                return(num);
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator listIterator() {
        return new DoubleListIterator();
    }

    @Override
    public ListIterator listIterator(int i) {
        return new DoubleListIterator(i);
    }

    @Override
    public List subList(int i, int i1) {
        return null;
    }

    @Override
    public boolean retainAll(Collection collection) {
        return false;
    }

    @Override
    public boolean removeAll(Collection collection) {
        return false;
    }

    @Override
    public boolean containsAll(Collection collection) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] objects) {
        return new Object[0];
    }

}
