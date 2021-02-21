import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;

public class SimpleRandWalkMemory extends Algorithm {
    LinkedList<Node> memory;
    int memorySize;
    /*
     * Traverses the graph according the specifications of the algorithm
     */
    public SimpleRandWalkMemory(Long seed, Graph superGraph, Graph subGraph, int memorySize) {
        super(seed, superGraph, subGraph); 
        // TODO Auto-generated constructor stub
        this.memory = new LinkedList<Node>();
        this.memorySize = memorySize;
    }
    public void traverseGraph() {
        
        Node curNode = subGraph.start;
        int traversals = 0;
        memory.add(curNode);
        while (!curNode.equals(subGraph.target)) {
            
            Edge tempEdge = findNextNodeWithMemConstraint(curNode, curNode.edgeList.size());
            
            if (tempEdge == null) tempEdge = findNextNodeWithoutMemConstraint(curNode, curNode.edgeList.size());
            
            distance = distance.add(BigInteger.valueOf(tempEdge.weight));
            
            memory.addLast(curNode);
            curNode = tempEdge.toNode;
            
            traversals++;
            if (traversals >= max_traversal) {
                distance = BigInteger.valueOf(-2);
                return;
            }
        }
    }
    private Edge findNextNodeWithoutMemConstraint(Node curNode, int size) {
            int nextEdge = rand.nextInt(size);
            Edge chosenEdge = curNode.edgeList.get(nextEdge);
            return chosenEdge;
    }
    private Edge findNextNodeWithMemConstraint(Node curNode, int size) {
        Edge chosenEdge = null;
        ArrayList<Edge> viableEdges = new ArrayList<Edge>();
        for (int i = 0; i < curNode.edgeList.size(); ++i) {
            //build an arrayList of viable edges by for loop, and then randomly choose from that size
            if (memory.contains(curNode.edgeList.get(i).toNode)) {
                continue;
            }
            else {
                viableEdges.add(curNode.edgeList.get(i));
            }
        }
       if (viableEdges.size() > 0) {
           int nextEdge = rand.nextInt(viableEdges.size());
           chosenEdge = viableEdges.get(nextEdge);
           return chosenEdge;
       }
       else return null;
    }
    
    
    /*
     * Reads in the config file for each algorithm
     */
    public void readConfig() {

    }
}