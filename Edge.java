//package com.ssaurel.dijsktra;
//credit to Sylvain Saurel
public class Edge implements Comparable<Edge>{
    Node fromNode;
    Node toNode;
    int weight;

    public Edge (Node toNode, Node fromNode, int weight) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.weight = weight;
    }
    
    //implementing equals
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Edge)) return false;
        
        Edge compEdge = (Edge) o;
        
        if (compEdge.toNode.key == toNode.key && compEdge.fromNode.key == fromNode.key) return true;
        return false;
    }
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(toNode.key + "," +fromNode.key);
        return str.toString();
    }
    
    public Edge flipEdge() {
        return new Edge(this.fromNode, this.toNode, this.weight);
    }

    @Override
    public int compareTo(Edge arg0) {
        if (this.equals(arg0)) return 0;
        if (this.fromNode.key == arg0.fromNode.key) {
            return this.toNode.key - arg0.toNode.key;
        }
        return this.fromNode.key - arg0.fromNode.key;
    }
}