import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class Dijkstra {
    ArrayList<Node> shortestPath;

    public int findShortestPath(Graph graph, Node start, Node target) {
        for (Node current : graph.nodeList) {
            current.visited = false;
            current.totalWeight = Integer.MAX_VALUE;
            current.predecessor = null;
        }
        start.totalWeight = 0;

        PriorityQueue<Tuple<Integer, Node>> pq = new PriorityQueue<>();
        Tuple<Integer, Node> tuple = new Tuple<Integer, Node>(start.totalWeight, start);
        pq.add(tuple);

        while (!pq.isEmpty()) {
            Tuple<Integer, Node> tup = pq.remove();
            Node current = tup.val2;
            if (current == target)
                break;
            current.visited = true;

            for (Edge successor : current.edgeList) {
                if (!successor.toNode.visited) {
                    if (successor.toNode.totalWeight > successor.weight + current.totalWeight) {
                        successor.toNode.totalWeight = successor.weight + current.totalWeight;
                        successor.toNode.predecessor = current;
                        pq.add(new Tuple<Integer, Node>(successor.toNode.totalWeight,
                            successor.toNode));
                    }
                }
            }
        }
        if (target.totalWeight != Integer.MAX_VALUE) {

            shortestPath = new ArrayList<Node>();
            Node current = target;
            while (current != null && current != start) {
                shortestPath.add(current);
                current = current.predecessor;
            }
            Collections.reverse(shortestPath);
        }
        return target.totalWeight;
    }
}

/*
 * Location: /Users/alextaylor/Desktop/sim_class_v15/!/Dijkstra.class Java compiler version: 9
 * (53.0) JD-Core Version: 1.1.3
 */
