package telran.list.model;

import telran.list.intefaces.IList;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class MyLinkedList<E> implements IList<E> {
    private Node<E> first;
    private Node<E> last;
    private int size;


    //O(1)
    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    //O(1)
    @Override
    public int size() {
        return size;
    }

    //O(n)//O(1) for last first
    @Override
    public boolean add(int index, E element) {
        if (index == size) {
            Node<E> newNode = new Node<>(last, element, null);
            if (last != null) {
                last.next = newNode;
            } else {
                first = newNode;
            }
            last = newNode;
        } else {
            Node<E> node = getNodeByIndex(index);
            Node<E> newNode = new Node<>(node.prev, element, node);
            node.prev = newNode;
            if (index != 0) {
                newNode.prev.next = newNode;
            } else {
                first = newNode;
            }
        }
        size++;
        return true;
    }

    //O(n)// checkIndex O(1)
    private Node<E> getNodeByIndex(int index) {
        checkIndex(index);
        Node<E> node = first;
        if (index < size / 2) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    //O(1)
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index);
        }
    }

    //O(n)
    @Override
    public E get(int index) {
        return getNodeByIndex(index).payload;
    }

    //O(n)
    @Override
    public int indexOf(Object o) {
        int index = 0;
        if (o != null) {
            for (Node<E> node = first; node != null; node = node.next, index++) {
                if (o.equals(node.payload)) {
                    return index;
                }
            }
        } else {
            for (Node<E> node = first; node != null; node = node.next, index++) {
                if (null == node.payload) {
                    return index;
                }
            }
        }
        return -1;
    }

    //O(n)
    @Override
    public int lastIndexOf(Object o) {
        int index = size - 1;
        if (o != null) {
            for (Node<E> node = last; node != null; node = node.prev, index--) {
                if (o.equals(node.payload)) {
                    return index;
                }
            }
        } else {
            for (Node<E> node = last; node != null; node = node.prev, index--) {
                if (null == node.payload) {
                    return index;
                }
            }
        }
        return -1;
    }

    //O(1) checkIndex //getNodeByIndex O(n)// prev.next next.prev first last O(1)
    @Override
    public E remove(int index) {
        checkIndex(index);
        Node<E> node = getNodeByIndex(index);
        E removed = node.payload;
        Node<E> prevNode = node.prev;
        Node<E> nextNode = node.next;

        if (first == last) {
            first = null;
            last = null;
        } else if (node == first) {
            first = node.next;
            first.prev = null;
        } else if (node == last) {
            last = node.prev;
            last.next = null;
        } else {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        size--;
        return removed;
    }

    //O(n)
    @Override
    public E set(int index, E element) {
        checkIndex(index);
        Node<E> node = getNodeByIndex(index);
        E oldElement = node.payload;
        node.payload = element;
        return oldElement;
    }


    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private Node<E> current = first;
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < size;
            }
            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E data = current.payload;
                current = current.next;
                i++;
                return data;
            }
        };
    }


    private static class Node<E> {
        E payload;
        Node<E> next;
        Node<E> prev;

        public Node(Node<E> prev, E payload, Node<E> next) {
            this.payload = payload;
            this.next = next;
            this.prev = prev;
        }
    }
}
