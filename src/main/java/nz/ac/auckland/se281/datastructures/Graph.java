package nz.ac.auckland.se281.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A graph that is composed of a set of verticies and edges.
 *
 * <p>You must NOT change the signature of the existing methods or constructor of this class.
 *
 * @param <T> The type of each vertex, that have a total ordering.
 */
public class Graph<T extends Comparable<T>> {
  private Set<T> verticies;
  private Set<Edge<T>> edges;

  /**
   * Compares 2 integer strings to see which is larger.
   *
   * @param o1 The first integer string.
   * @param o2 The second integer string.
   * @return int 1 if o1 is larger, -1 if o2 is larger, 0 if they are equal.
   */
  private Comparator<T> comparator =
      new Comparator<T>() {
        @Override
        public int compare(T o1, T o2) {
          return Integer.compare(Integer.parseInt(o1.toString()), Integer.parseInt(o2.toString()));
        }
      };

  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    this.verticies = verticies;
    this.edges = edges;
  }

  /**
   * Gets the roots for this graph.
   *
   * @return The set of verticies in this graph which are roots.
   */
  public Set<T> getRoots() {

    Set<T> roots = new TreeSet<T>(comparator);
    roots.addAll(verticies);
    T min;
    int count;
    // loops through all edges and removes the destination vertex from the set of roots
    for (T vertix : verticies) {
      count = 0;
      for (Edge<T> edge : edges) {
        if (edge.contains(vertix)) {
          count++;
        }
        roots.remove(edge.getDestination());
        // if its an equivalence relation, add the minimum value of equivalence relation to roots
        if (this.isEquivalence()) {
          min = Collections.min(this.getEquivalenceClass(edge.getSource()), comparator);
          roots.add(min);
        }
      }
      if (count == 0) {
        roots.remove(vertix);
      }
    }

    return roots;
  }

  /**
   * Checks of graph is reflexive.
   *
   * @return boolean True or False if graph is reflexive.
   */
  public boolean isReflexive() {

    Edge<T> temp;
    int count = 0;
    // loops through all verticies and checks if there is an edge from the vertex to itself
    for (T vertex : verticies) {
      temp = new Edge<T>(vertex, vertex);
      // if there is an edge from the vertex to itself, increment count
      if (temp.isIn(edges)) {
        count++;
      }
    }
    // if count is equal to the number of verticies, then graph is reflexive
    if (count == verticies.size()) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Checks if graph is symmetric.
   *
   * @return boolean True or False if graph is symmetric.
   */
  public boolean isSymmetric() {

    T tempSource;
    T tempDestination;
    Edge<T> temp;
    // loops through all edges and checks if there is an edge from the destination to the source
    for (Edge<T> edge : edges) {
      tempSource = edge.getSource();
      tempDestination = edge.getDestination();
      temp = new Edge<T>(tempDestination, tempSource);
      // if there is an edge from the destination to the source, check if it is in the set of edges
      if (!temp.isIn(edges)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if graph is transitive.
   *
   * @return returns boolean True or False if graph is transitive.
   */
  public boolean isTransitive() {

    Edge<T> temp;
    // loops through all edges and checks if there is an edge from the source to the destination1

    for (Edge<T> edge : edges) {
      // checks to see if there is an  edge from the destination1 to desitnation2 then there checks
      // to see if there is a edge from source to destination2
      for (Edge<T> edge1 : edges) {
        if (edge.getDestination() == edge1.getSource()) {
          temp = new Edge<T>(edge.getSource(), edge1.getDestination());
          // return false if there is no edge from source to destination2
          if (!temp.isIn(edges)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * Checks if graph is anti symmetric.
   *
   * @return boolean True or False if graph is anti symmetric.
   */
  public boolean isAntiSymmetric() {

    Edge<T> temp;
    // loops through all edges and checks if there is an edge from the destination to the source
    for (Edge<T> edge : edges) {
      temp = new Edge<T>(edge.getDestination(), edge.getSource());
      // checks to make sure there is no edge from the destination to the source
      if (temp.isIn(edges) && edge.getSource() != edge.getDestination()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if graph is equivalence relation graph.
   *
   * @return boolean True or False if graph is equivalence relation graph.
   */
  public boolean isEquivalence() {
    // checks to see if its reflexive, symmetric, and transitive if it is its a equivalence relation
    if (this.isReflexive() && this.isSymmetric() && this.isTransitive()) {
      return true;
    }
    return false;
  }

  /**
   * Gets the equivalence class for a given vertex.
   *
   * @param vertex The vertex to get the equivalence class for.
   * @return The set of verticies in the equivalence class.
   */
  public Set<T> getEquivalenceClass(T vertex) {

    Set<T> equivalenceClass = new HashSet<T>();
    Set<T> newlyAdded;
    // if its not equivalence class return []
    if (!verticies.contains(vertex)) {
      return equivalenceClass;
    }
    if (!this.isEquivalence()) {
      return equivalenceClass;
    }
    equivalenceClass.add(vertex);

    // do loop that loops through while there is a newly added vertex
    do {
      newlyAdded = new HashSet<T>();
      for (Edge<T> edge : edges) {
        // if there is any connections between vertix and the edges in the graph add the vertex of
        // the edges to the equivalence class
        if (equivalenceClass.contains(edge.getSource())) {
          if (!equivalenceClass.contains(edge.getDestination())) {
            newlyAdded.add(edge.getDestination());
          }
        }
        if (equivalenceClass.contains(edge.getDestination())) {
          if (!equivalenceClass.contains(edge.getSource())) {
            newlyAdded.add(edge.getSource());
          }
        }
      }
      // adds all newly added verticies to the equivalence class
      equivalenceClass.addAll(newlyAdded);
    } while (!newlyAdded.isEmpty());

    return equivalenceClass;
  }

  /**
   * does a breadth first search on the graph.
   *
   * @return The list of verticies in the order they were visited.
   */
  public List<T> iterativeBreadthFirstSearch() {

    Queue<T> queue = new Queue<T>();
    List<T> result = new ArrayList<T>();
    Set<T> visited = new HashSet<T>();
    Set<T> remain = new HashSet<T>(this.getRoots());
    Set<T> neighbour;
    T temp;
    T dequeued;
    T index;

    // loops until there are no more verticies to visit
    while (!remain.isEmpty()) {
      // choses the minimum root to traverse first
      temp = Collections.min(remain, comparator);
      remain.remove(temp);

      queue.enqueue(temp);

      // loops until queue is empty
      while (!queue.isEmpty()) {
        neighbour = new HashSet<T>();
        // dequeue the first element in the queue
        dequeued = queue.dqueue();
        // if we havent visisted it add it to the visited list and the result list
        if (!visited.contains(dequeued)) {
          visited.add(dequeued);
          result.add(dequeued);
        }
        // loops through all edges and adds the destination vertex to the neighbour list if it hasnt
        // been visited
        for (Edge<T> edge : edges) {
          if (edge.getSource() == dequeued && !visited.contains(edge.getDestination())) {

            neighbour.add(edge.getDestination());
          }
        }
        // adds all the neighbours in order of smallest to largest to the queue and removes them
        // from the remain list
        while (neighbour.size() != 0) {
          index = Collections.min(neighbour, comparator);
          neighbour.remove(index);
          queue.enqueue(index);
          remain.remove(index);
        }
      }
    }
    return result;
  }

  /**
   * does a depth first search on the graph.
   *
   * @return The list of verticies in the order they were visited.
   */
  public List<T> iterativeDepthFirstSearch() {

    Stack<T> stack = new Stack<T>();
    List<T> result = new ArrayList<T>();
    Set<T> visited = new HashSet<T>();
    Set<T> remain = new HashSet<T>(this.getRoots());
    Set<T> neighbours = new HashSet<T>();
    T temp;
    T min;
    int count = 0;

    // loops until there are no more verticies to visit
    while (!remain.isEmpty()) {
      temp = Collections.min(remain, comparator);
      remain.remove(temp);
      stack.push(temp);
      result.add(temp);
      visited.add(temp);
      // loops until stack is empty
      while (!stack.isEmpty()) {
        temp = stack.peek();
        // loops through all edges and adds the destination vertex to the neighbour list if it hasnt
        // been visited
        for (Edge<T> edge : edges) {
          if (edge.getSource() == temp && !visited.contains(edge.getDestination())) {
            neighbours.add(edge.getDestination());
            count++;
          }
        }
        // if there are no neighbours pop the stack
        if (count == 0) {
          stack.pop();
        } else {
          // if there are neighbours add the smallest neighbour to the stack and remove it from the
          // remain list
          min = Collections.min(neighbours, comparator);
          remain.remove(min);
          stack.push(min);
          result.add(min);
          visited.add(min);
          count = 0;
          neighbours = new HashSet<T>();
        }
      }
    }
    return result;
  }

  /**
   * does a recursive breadth first search on the graph.
   *
   * @return The list of verticies in the order they were visited.
   */
  public List<T> recursiveBreadthFirstSearch() {

    Queue<T> queue = new Queue<T>();
    List<T> result = new ArrayList<T>();
    Set<T> visited = new HashSet<T>();
    Set<T> remain = new HashSet<T>(this.getRoots());
    T temp;

    // loops until there are no more verticies to visit
    while (!remain.isEmpty()) {
      temp = Collections.min(remain, comparator);
      remain.remove(temp);
      queue.enqueue(temp);
      // recursive call
      helperRecursiveBreadthFirstSearch(queue, remain, visited, result);
    }

    return result;
  }

  /**
   * Recursive helper method for recursiveBreadthFirstSearch.
   *
   * @param queue the queue of verticies to visit.
   * @param remain the set of verticies that have not been visited.
   * @param visited the set of verticies that have been visited.
   * @param result the list of verticies in the order they were visited.
   */
  private void helperRecursiveBreadthFirstSearch(
      Queue<T> queue, Set<T> remain, Set<T> visited, List<T> result) {
    // if queue is empty return
    if (queue.isEmpty()) {
      return;
    }
    T index;
    T dequeued = queue.dqueue();
    ArrayList<T> neighbours = new ArrayList<T>();

    // if we havent visisted it add it to the visited list and the result list
    if (!visited.contains(dequeued)) {
      visited.add(dequeued);
      result.add(dequeued);
    }

    // loops through all edges and adds the destination vertex to the neighbour list if it hasnt
    for (Edge<T> edge : edges) {
      if (edge.getSource().equals(dequeued) && !visited.contains(edge.getDestination())) {
        neighbours.add(edge.getDestination());
      }
    }

    // adds all the neighbours in order of smallest to largest to the queue and removes them
    while (neighbours.size() != 0) {
      index = Collections.min(neighbours, comparator);
      neighbours.remove(index);
      queue.enqueue(index);
      remain.remove(index);
    }
    // Recursive call
    helperRecursiveBreadthFirstSearch(queue, remain, visited, result);
  }

  /**
   * does a recursive depth first search on the graph.
   *
   * @return The list of verticies in the order they were visited.
   */
  public List<T> recursiveDepthFirstSearch() {
    Stack<T> stack = new Stack<T>();
    List<T> result = new ArrayList<T>();
    Set<T> visited = new HashSet<T>();
    Set<T> remain = new HashSet<T>(this.getRoots());
    T temp;

    // loops until there are no more verticies to visit
    while (!remain.isEmpty()) {
      // choses the minimum root to traverse first
      temp = Collections.min(remain, comparator);
      remain.remove(temp);
      stack.push(temp);
      visited.add(temp);
      result.add(temp);

      // recursive call
      helperRecursiveDepthFirstSearch(stack, remain, visited, result);
    }

    return result;
  }

  /**
   * Recursive helper method for recursiveDepthFirstSearch.
   *
   * @param stack the stack of verticies to visit.
   * @param remain the set of verticies that have not been visited.
   * @param visited the set of verticies that have been visited.
   * @param result the list of verticies in the order they were visited.
   */
  private void helperRecursiveDepthFirstSearch(
      Stack<T> stack, Set<T> remain, Set<T> visited, List<T> result) {
    // if stack is empty return
    if (stack.isEmpty()) {
      return;
    }

    T current = stack.peek();
    Set<T> neighbours = new HashSet<T>();

    // loops through all edges and adds the destination vertex to the neighbour list if it hasnt
    for (Edge<T> edge : edges) {
      if (edge.getSource().equals(current) && !visited.contains(edge.getDestination())) {
        neighbours.add(edge.getDestination());
      }
    }

    // if there are no neighbours pop the stack
    if (neighbours.isEmpty()) {
      stack.pop();
      helperRecursiveDepthFirstSearch(stack, remain, visited, result);
    } else {
      // if there are neighbours add the smallest neighbour to the stack and remove it from the
      T min = Collections.min(neighbours, comparator);
      remain.remove(min);
      stack.push(min);
      visited.add(min);
      result.add(min);

      // Recursive call
      helperRecursiveDepthFirstSearch(stack, remain, visited, result);
    }
  }
}
