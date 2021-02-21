import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class BFS {
    Node s;
    Node t;
    Graph graph;

    public BFS(Graph graph, Node s, Node t) {
        this.graph = graph;
        this.s = s;
        this.t = t;
    }
    public boolean pathExistence() {
        clear();
        Queue<Node> q = new LinkedList<Node>();
        
        q.add(s);
        
        Node current = null;
        while(!q.isEmpty()) {
            //System.out.println("enters while");
            current = q.remove();
            if (current.visited == true) continue;
            if (current.equals(t)) { 
                return true;
            }
            
            else {
                current.visited = true;
                for (Edge neighbor: current.edgeList) {
                    //System.out.println(neighbor);
                    //System.out.println(neighbor.toNode);
                    if (!neighbor.toNode.visited) {
                        //System.out.println("adds neighbor");
                        q.add(neighbor.toNode);
                    }
                }
            }
        }
        
        return false;
    }
    private void clear() {
        for (Node current: graph.nodeList) {
            current.visited = false;
        }
    }
}
