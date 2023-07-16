package nz.ac.auckland.se281.datastructures;

/** A data structure of a node that stores data and a pointer to the next node. */
public class Node<T> {

  private T data;
  private Node<T> next;

  /**
   * Constructor for ListNode.
   *
   * @param data The data to be stored in the node.
   */
  public Node(T data) {
    this.data = data;
    this.next = null;
  }

  /**
   * Returns the data stored in the node.
   *
   * @return The data stored in the node.
   */
  public T getData() {
    return data;
  }

  /**
   * Returns the next node in the list.
   *
   * @return The next node in the list.
   */
  public Node<T> getNext() {
    return next;
  }

  /**
   * Sets the next node in the list.
   *
   * @param next The next node in the list.
   */
  public void setNext(Node<T> next) {
    this.next = next;
  }

  /**
   * Sets the data stored in the node.
   *
   * @param data The data stored in the node.
   */
  public void setData(T data) {
    this.data = data;
  }
}
