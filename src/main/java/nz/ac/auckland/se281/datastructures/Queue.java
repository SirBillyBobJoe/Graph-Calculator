package nz.ac.auckland.se281.datastructures;

import java.util.NoSuchElementException;

/** A data structure thats a queue that follows the first in first out principle. */
public class Queue<T> {
  private int length = 0;
  private Node<T> front;
  private Node<T> rear;

  /** Constructor for Queue. */
  public Queue() {
    front = rear = null;
  }

  /**
   * Returns the number of elements in the queue.
   *
   * @return The number of elements in the queue.
   */
  public int size() {
    return length;
  }

  /**
   * Checks if the queue is empty.
   *
   * @return True if the queue is empty, false otherwise.
   */
  public boolean isEmpty() {
    return length == 0;
  }

  /**
   * Returns the element at the front of the queue without removing it.
   *
   * @return The element at the front of the queue.
   * @throws NoSuchElementException If the queue is empty.
   */
  public T front() throws NoSuchElementException {
    if (isEmpty()) {
      throw new NoSuchElementException("Queue is empty");
    }
    return front.getData();
  }

  /**
   * Adds the given element to the rear of the queue.
   *
   * @param element The element to add to the rear of the queue.
   */
  public void enqueue(T element) {
    Node<T> temp = new Node<T>(element);
    // if queue is empty, set front to temp
    if (isEmpty()) {
      front = temp;
    } else {
      // set the next node of the rear node to temp
      rear.setNext(temp);
    }
    rear = temp;
    length++;
  }

  /**
   * Removes the element at the front of the queue.
   *
   * @return The element at the front of the queue.
   * @throws NoSuchElementException If the queue is empty.
   */
  public T dqueue() throws NoSuchElementException {
    // if queue is empty, throw exception
    if (isEmpty()) {
      throw new NoSuchElementException("Queue is empty");
    }
    // store the data of the front node
    T result = front.getData();
    // move the front node to the next node
    front = front.getNext();
    // if the front node is null, set rear to null
    if (front == null) {
      rear = null;
    }
    length--;
    // return the data of the front node
    return result;
  }
}
