package queue;

import java.util.Objects;

public class LinkedQueue extends AbstractQueue {
    private Node front;
    private Node rare;

    protected void enqueueImpl(Object element) {
        if (size == 0) {
            rare = new Node(element);
            front = rare;
        } else {
            Node newRare = new Node(element, front, rare);
            rare.linkToFront = newRare;
            rare = newRare;
        }
    }

    protected Object elementImpl() {
        return front.value;
    }

    protected void pushImpl(Object element) {
        if (size == 0) {
            front = new Node(element);
            rare = front;
        } else {
            Node newFront = new Node(element, front, rare);
            front.linkToRare = newFront;
            front = newFront;
        }
    }

    protected Object peekImpl() {
        return rare.value;
    }

    protected void removeFront() {
        front = front.linkToFront;
    }

    protected void removeRare() {
        rare = rare.linkToRare;
    }

    public LinkedQueue makeCopy() {
        LinkedQueue copy = new LinkedQueue();
        copy.front = front;
        copy.rare = rare;
        copy.size = size;
        return copy;
    }

    protected boolean isFront(Object element) {
        return element == front;
    }

    protected boolean isRare(Object element) {
        return element == rare;
    }

    protected Object getContainsLink(Object element) {
        Node current = front;
        int cnt = 0;

        while (cnt != size) {
            if (element.equals(current.value)) {
                return current;
            }
            cnt++;
            current = current.linkToFront;
        }
        return null;
    }

    protected void deleteCentral(Object element) {
        Node temp = (Node) element;
        temp.linkToFront.linkToRare = temp.linkToRare;
        temp.linkToRare.linkToFront = temp.linkToFront;
        size--;
    }

    private static class Node {
        private final Object value;
        private Node linkToFront;
        private Node linkToRare;

        public Node(Object value, Node linkToFront, Node linkToRare) {
            Objects.requireNonNull(value);

            this.value = value;
            this.linkToFront = linkToFront;
            this.linkToRare = linkToRare;
        }

        public Node(Object value) {
            this.value = value;
            this.linkToFront = this;
            this.linkToRare = this;
        }
    }
}
