import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;

public class Graph {
    int weight;
    ArrayList<Node> nodeList;
    ArrayList<Edge> edgeList;
    Map<Node, Integer> distancesMap;
    Map<Integer, Node> copyMap;
    Node start;
    Node target;
    int opt;
    boolean calc;
    String edgeListString;

    /*
    *
    */
    @SuppressWarnings("unchecked")
    public Graph (int numNodes, int start, int target) { 
        this.weight = 1;
        this.nodeList = new ArrayList<Node>();
        this.edgeList = new ArrayList<Edge>();
        this.distancesMap = new TreeMap<Node, Integer>();
        this.opt = 0;
        this.calc = true;
        
        for (int i = 0; i < numNodes; ++i) {
            Node node = new Node(new ArrayList<Edge>(), i);
            nodeList.add(node);
            if (node.key == start) this.start = node;
            else if (node.key == target) this.target = node;
        }
    }
    @Deprecated
    public Graph(int numNodes, String edgeList, int start, int target) {
        this.nodeList = new ArrayList<Node>();
        this.edgeList = new ArrayList<Edge>();
        this.distancesMap = new TreeMap<Node, Integer>();
        this.opt = 0;
        this.calc = true;
        this.edgeListString = edgeList;
        
        // edge list is string of "{[to, from, weight],...}"
        // numNodes specifies number of nodes
        // parse edgeList and call construct graph
        for (int i = 0; i < numNodes; ++i) {
            Node node = new Node(new ArrayList<Edge>(), i);
            nodeList.add(node);
            if (node.key == start) this.start = node;
            else if (node.key == target) this.target = node;
        }
        parseEdgeList(edgeList);
    }
    //Deprecated
    /*public Graph(Graph superGraph, ArrayList<Edge> removalIndices) {
        this.start = superGraph.start;
        this.target = superGraph.target;
        this.nodeList = new ArrayList<Node>();
        this.edgeList = new ArrayList<Edge>();
        createNewNodeList(superGraph);

        //this.nodeList = (ArrayList<Node>) superGraph.nodeList.clone();
        //this.edgeList = (ArrayList<Edge>) superGraph.edgeList.clone();
        this.distancesMap = new TreeMap<Node, Integer>();
        
        
        for (Edge edgeToRemove : removalIndices) {
            this.edgeList.remove(edgeToRemove);
            
            for (Node node : this.nodeList) {
                node.edgeList.remove(edgeToRemove);
            }
        }
    }*/
    
    /*
     * start by copying the nodes from the supergraph meaning creating a node with the same index
     */
    public Graph(Graph superGraph, ArrayList<Edge> removalIndices) {
        this.copyMap = new TreeMap<Integer, Node>();
        this.distancesMap = new TreeMap<Node, Integer>();
        this.nodeList = new ArrayList<Node>();
        this.edgeList = new ArrayList<Edge>();
        
        for (int i = 0; i < superGraph.nodeList.size(); ++i) {
            Node newNode = new Node(superGraph.nodeList.get(i));
            this.nodeList.add(newNode);
            this.copyMap.put(newNode.key, newNode);
            if (newNode.equals(superGraph.start)) this.start = newNode;
            if (newNode.equals(superGraph.target)) this.target = newNode;
        }
        
        for (int i = 0; i < superGraph.edgeList.size(); ++i) {
            Node toNode = copyMap.get(superGraph.edgeList.get(i).toNode.key);
            Node fromNode = copyMap.get(superGraph.edgeList.get(i).fromNode.key);
            if (toNode == null || fromNode == null) {
                throw new NullPointerException();
            }
            Edge newEdge = new Edge(toNode, fromNode, superGraph.edgeList.get(i).weight);
            
            this.edgeList.add(newEdge);
        }
        
        int counter = 0;
        for (Edge edgeToRemove : removalIndices) {
            ++counter;
            
            this.edgeList.remove(edgeToRemove);

            this.edgeList.remove(edgeToRemove.flipEdge());
        }
        for (Edge edge : this.edgeList) {
            edge.fromNode.edgeList.add(edge);
        }
    }
    
    private void createNewNodeList(Graph superGraph) {
        for (int i = 0; i < superGraph.nodeList.size(); ++i) {
            this.nodeList.add(new Node(new ArrayList<Edge>(), i));
        }
        parseEdgeList(superGraph.edgeListString);
    }
    
    
    public Graph copy() {
        return new Graph(nodeList.size(), edgeListString, start.key, target.key);
    }
    
    public void parseEdgeList(String edgeList) {
        edgeList = edgeList.trim();
        edgeList = edgeList.substring(2, edgeList.length() - 2);
        String[] edges = edgeList.split("\\],\\[");
        
        
        ArrayList<Integer[]> holder = new ArrayList<Integer[]>();
        
        for (int i = 0; i < edges.length; ++i) {
            String[] temp = edges[i].split(",");
            int toNode = Integer.parseInt(temp[0]);
            int fromNode = Integer.parseInt(temp[1]);
            int weight = Integer.parseInt(temp[2]);
            
            holder.add(new Integer[] {toNode,fromNode,weight});
        }
        createEdgeList(holder);
    }
    
    public void createEdgeList(ArrayList<Integer[]> edges) {
        for (Integer[] edge : edges) {
            int toNodeKey = edge[0];
            int fromNodeKey = edge[1];
            int weight = edge[2];
            
            Node node1 = null;
            Node node2 = null;
            for (Node node : this.nodeList) {
                if (toNodeKey == node.key) {
                    node1 = node;
                }
                if (fromNodeKey == node.key) {
                    node2 = node;
                }
                if (node1 != null && node2 != null) {
                    Edge newEdge = new Edge(node2, node1, weight);
                    Edge altEdge = new Edge(node1, node2, weight);
                    node2.edgeList.add(newEdge);
                    node1.edgeList.add(altEdge);
                    edgeList.add(newEdge);
                    break;
                }
            }
        }
        return;
    }

    public int calcDistance(Node s, Node target) {
        int dist = 0;
        // map -> two nodes to distance

        // check if the values are in the map
        // if its not in the table, call dijkstras and record result in table
        // and return value 
        synchronized (this) {
        if (this.distancesMap.containsKey(s)) {
            return distancesMap.get(s);
        }
        
            Dijkstra path_length = new Dijkstra();
            dist = path_length.findShortestPath(this, s, target);
            distancesMap.put(s,dist);
        }
        
        return dist;
    }
    
    public ArrayList<Node> findShortestPath(Node s, Node target) {
        Dijkstra path = new Dijkstra();
        path.findShortestPath(this, s, target);
        
        return path.shortestPath;
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Node curNode: this.nodeList) {
            str.append(curNode + "\n");
        }
        
        return str.toString();
    }
}
