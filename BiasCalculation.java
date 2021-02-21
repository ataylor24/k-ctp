import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class BiasCalculation {
    Graph g;
    ArrayList<Edge> edgeList;
    Node subStart;
    Node instStart;
    Map <Edge,Integer> distances;
    
    Node orig_start;
    Node instance_start;
    //DONT NEED TO REMOVE THE WHOLE PATH, JUST THE EDGE
    
    /*
     * Start: current node
     * subEdgeList: subGraph edges of current node, used for building an edgelist of edges in graph g
     * g: super graph
     */
    
    public BiasCalculation(Node start, ArrayList<Edge> subEdgeList, Graph g) {
        this.g = new Graph(g, new ArrayList<Edge>());
        this.orig_start = start;
        this.instance_start = retrieveEquivNode(start);
        distances = new TreeMap<Edge,Integer>();
    }
    
    /*
     * Helper method for calculating the distances for each edge off of the current node
     */
    public Map<Edge, Integer> calcNodeDist() {
        findValidPaths();
        return distances;
    }
    
    
    private Node retrieveEquivNode(Node start) {
        for (Node node : this.g.nodeList) {
            if (node.equals(start)) return node;
        }
        return null;
    }
    
    private void findValidPaths() {
        ArrayList<Node> path;
        do {
            
            path = this.g.findShortestPath(instance_start, this.g.target);
            Edge candidate_edge;
            if (path != null) {
                candidate_edge = checkEdgeList(path.get(0), instance_start);
              
                if (candidate_edge != null) {
                    distances.put(candidate_edge, path.size());
                    this.g = updateGraph(this.g, path);
                    this.instance_start = retrieveEquivNode(instance_start);
                }
                
            }
            
            
        } while (this.g.edgeList.size() > 0 && path != null && path.size()>0);
        
    }
    
    private Edge checkEdgeList(Node next, Node cur) {
        Edge candidate = new Edge(next,cur,1);
        for (Edge check: cur.edgeList) {
            if (check.equals(candidate)) {
                return check;
            }
        }
        
        return null;
    }
    
    private Graph updateGraph(Graph g, ArrayList<Node> path) {
        ArrayList<Edge> removal = new ArrayList<Edge>();
        removal.add(new Edge(instance_start, path.get(0), 1));
        
        return new Graph(g, removal);
    }
    
}
