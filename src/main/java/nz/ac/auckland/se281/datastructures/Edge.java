package nz.ac.auckland.se281.datastructures;

import java.util.Set;

/**
 * An edge in a graph that connects two verticies.
 *
 * <p>You must NOT change the signature of the constructor of this class.
 *
 * @param <T> The type of each vertex.
 */
public class Edge<T> {
  private T source;
  private T destination;

  /**
   * Constructor for Edge.
   *
   * @param source The source vertex of this edge.
   * @param destination The destination vertex of this edge.
   */
  public Edge(T source, T destination) {
    this.source = source;
    this.destination = destination;
  }

  /**
   * Returns the source vertex of this edge.
   *
   * @return The source vertex of this edge.
   */
  public T getSource() {
    return source;
  }

  /**
   * Returns the destination vertex of this edge.
   *
   * @return The destination vertex of this edge.
   */
  public T getDestination() {
    return destination;
  }

  /**
   * Checks if the edges are equal.
   *
   * @param edge The edge to compare to this edge.
   * @return Boolean True or False if the given edge is equal to this edge.
   */
  public boolean isEquals(Edge<T> edge) {
    if (edge.getDestination() == this.getDestination() && edge.getSource() == this.getSource()) {
      return true;
    }
    return false;
  }

  /**
   * Checks if the edge is in the given set of edges.
   *
   * @param set The set of edges to check if this edge is in.
   * @return Boolean True or False if the given edge is in the set of edges.
   */
  public boolean isIn(Set<Edge<T>> set) {

    // Loop through the set of edges and check if this edge is in the set.
    for (Edge<T> edge : set) {
      if (edge.isEquals(this)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if the given vertex is in this edge.
   *
   * @param vertex The vertex to check if it is in this edge.
   * @return Boolean True or False if the given vertex is in this edge.
   */
  public boolean contains(T vertex) {
    if (vertex.equals(this.getDestination()) || vertex.equals(this.getSource())) {
      return true;
    }
    return false;
  }
}
