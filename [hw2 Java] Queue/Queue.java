package queue;

// Model: a[-inf]..a[front]..a[rare]..a[+inf]
// Let: n = rare - front + 1
// Let: immutable(front, rare, step): forall i=front..rare: a'[i] = a[i + step]
// Inv: n >= 0 && forall i=front..rare: a[i] != null
public interface Queue extends Copiable {
    // Pred: element != null
    // Post: n' = n + 1 &&
    //       rare' = rare + 1 &&
    //       front' = front &&
    //       a'[rare'] = element &&
    //       immutable(front, rare, 0)
    void enqueue(Object element);

    // Pred: n > 0
    // Post: R = a[front] &&
    //       immutable(front, rare, 0)
    Object element();

    // Pred: n > 0
    // Post: R = a[front] &&
    //       rare' = rare &&
    //       front' = front + 1 &&
    //       immutable(front', rare', 0)
    Object dequeue();

    // Pred: true
    // Post: R = n &&
    //       n' = n &&
    //       immutable(front, rare, 0)
    int size();

    // Pred: true
    // Post: R = (n == 0) &&
    //       n' = n &&
    //       immutable(front, rare, 0)
    boolean isEmpty();

    // Pred: a != null
    // Post: n' = 0 &&
    //       rare' = -1 &&
    //       front' = 0 &&
    //       immutable(front', rare', 0)
    void clear();

    // Pred: element != null
    // Post: n' = n + 1 &&
    //       rare' = rare &&
    //       front' = front - 1 &&
    //       a'[front'] = element &&
    //       immutable(front, rare, 0)
    void push(Object element);

    // Pred: n > 0
    // Post: R = a[rare] &&
    //       immutable(front, rare, 0)
    Object peek();

    // Pred: n > 0
    // Post: R = a[rare] &&
    //       rare' = rare - 1 &&
    //       front' = front &&
    //       immutable(front', rare', 0)
    Object remove();

    // Pred: element != null
    // Post: R = true - will find such i: a[i] == element; false - else &&
    //       immutable(front, rare, 0)
    boolean contains(Object element);

    // Pred: element != null
    // Let: i - min index in [front, rare]: a[i] == element, else null
    // Post: R = true - i != null; false - else &&
    //       rare' = rare - 1 &&
    //       front' = front == i ? front + 1 : front &&
    //       a' = a[-inf..i-1] + a[i+1..+inf] where '+' is concatenation &&
    //       immutable(front', i - 1) && immutable(i, rare', 1)
    boolean removeFirstOccurrence(Object element);

}
