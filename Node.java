import java.util.ArrayList;

public class Node implements Comparable<Node>{
    Integer key;
    ArrayList<Edge> edgeList;
    boolean visited;
    int distance;
    int totalWeight;
    Node predecessor;
    int visits;
    
    //copy constructpr
    public Node(Node orig) {
        this.edgeList = new ArrayList<Edge>();
        this.visited = false;
        this.distance = Integer.MAX_VALUE;
        this.totalWeight = 0;
        this.predecessor = null;
        this.key = orig.key;
        this.visits = 0;
    }
    
    public Node(ArrayList<Edge> edgeList, Integer key) {
        this.edgeList = edgeList;
        this.visited = false;
        this.distance = Integer.MAX_VALUE;
        this.totalWeight = 0;
        this.predecessor = null;
        this.key = key;
        this.visits = 0;
    }

    @Override
    public int compareTo(Node node) {
        if (node.key > this.key) return 1;
        else if (node.key < this.key) return -1;
        else return 0;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node compared = (Node) o;
        
        return this.key == compared.key;
    }
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.key + ": " + this.edgeList);
        return str.toString();
    }
}