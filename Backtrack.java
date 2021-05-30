import java.util.*;


public class Backtrack {
    int dist;
    Graph guide_g;
    Graph sub_g;
    
    public Backtrack(Graph sup_g, Graph sub_g) {
        this.guide_g = sup_g;
        this.sub_g = sub_g;
    }
    
    public void run() {
        Node curNode = sub_g.start;
        Edge next;
        
        while (curNode != sub_g.target) {
            int path_distance = 0;
            ArrayList<Node> path = guide_g.findShortestPath(guide_g.start, guide_g.target);
            
            
            for (Node potential : path) {
                path_distance = 0;
                next = checkEdgeList(curNode, potential);
                
                if (next == null) {
                    dist += path_distance;
                    guide_g = updateGraph(guide_g, curNode, potential);
                    
                    curNode = sub_g.start;
                    break;
                }
                else {
                    curNode = next.toNode;
                    path_distance++;
                    dist++;
                }
            } 
            
        }
    }
    
    public Edge checkEdgeList(Node cur, Node next) {
        for (Edge check: cur.edgeList) {
            if (check.toNode.equals(next)) {
                return check;
            }
        }
        
        return null;
    }
    
    public Graph updateGraph(Graph g, Node cur, Node to_remove) {
        Edge missing = null;
        for (Node check : guide_g.nodeList) {
            if (check.equals(cur)) {
                missing = checkEdgeList(check, to_remove);
                break;
            }
        }
        
        ArrayList<Edge> removal = new ArrayList<Edge>();
        removal.add(missing);
        return new Graph(g, removal);
    }

}
