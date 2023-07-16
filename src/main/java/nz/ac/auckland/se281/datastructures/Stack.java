package nz.ac.auckland.se281.datastructures;

/** A data structure thats a stack that follows the last in first out principle. */
public class Stack<T> {
  private Node<T> top;
  private int length = 0;

  /** Constructor for Stack. */
  public Stack() {
    top = null;
  }

  /**
   * Returns the number of elements in the stack.
   *
   * @return The number of elements in the stack.
   */
  public int size() {
    return length;
  }

  /**
   * Checks if the stack is empty.
   *
   * @return True if the stack is empty, false otherwise.
   */
  public boolean isEmpty() {
    return length == 0;
  }

  /**
   * Returns the element at the top of the stack without removing it.
   *
   * @return The element at the top of the stack.
   */
  public T peek() {
    return top.getData();
  }

  /**
   * Adds the given element to the top of the stack.
   *
   * @param element The element to add to the top of the stack.
   */
  public void push(T element) {
    Node<T> temp = new Node<T>(element);
    // if stack is empty, set top to temp
    if (isEmpty()) {
      top = temp;
    } else {
      // set the next node of temp to top
      temp.setNext(top);
      top = temp;
    }
    length++;
  }

  /**
   * Removes the element at the top of the stack and returns it.
   *
   * @return The element at the top of the stack.
   * @throws RuntimeException If the stack is empty.
   */
  public T pop() throws RuntimeException {
    try {
      // stores the data of the top node
      T out = top.getData();
      // sets the top node to the next node
      top = top.getNext();
      length--;
      return out;
    } catch (Exception e) {
      throw new RuntimeException("Stack is empty");
    }
  }
}
