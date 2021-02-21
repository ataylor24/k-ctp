import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;

public class SimpleRandWalkMemSmart extends Algorithm {
    LinkedList<Node> memory;
    int memorySize;
    /*
     * Traverses the graph according the specifications of the algorithm
     */
    public SimpleRandWalkMemSmart(Long seed, Graph superGraph, Graph subGraph, int memorySize) {
        super(seed, superGraph, subGraph); 
        // TODO Auto-generated constructor stub
        this.memory = new LinkedList<Node>();
        this.memorySize = memorySize;
    }
    public void traverseGraph() {
        
        Node curNode = subGraph.start;
        int traversals = 0;
        
        while (!curNode.equals(subGraph.target)) {
            
            Edge tempEdge = findNextNodeWithMemConstraintSmart(curNode, curNode.edgeList.size());
            
            if (tempEdge == null) tempEdge = findNextNodeWithoutMemConstraintSmart(curNode, curNode.edgeList.size());
            
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
    @SuppressWarnings("unlikely-arg-type")
    private Edge findNextNodeWithoutMemConstraintSmart(Node curNode, int size) {
        Edge nextEdge = contains_target(curNode.edgeList);
        
        
        if (nextEdge == null) {
            int nextNode = rand.nextInt(curNode.edgeList.size());
            nextEdge = curNode.edgeList.get(nextNode);
        }
        
        return nextEdge;
    }
    private Edge findNextNodeWithMemConstraintSmart(Node curNode, int size) {
        Edge chosenEdge = null;
        ArrayList<Edge> viableEdges = new ArrayList<Edge>();
        for (int i = 0; i < curNode.edgeList.size(); ++i) {
            //build an arrayList of viable edges by for loop, and then randomly choose from that size
            if (curNode.edgeList.get(i).toNode.equals(subGraph.target)) return curNode.edgeList.get(i);
            if (!memory.contains(curNode.edgeList.get(i).toNode)) {
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
    
    public Edge contains_target(ArrayList<Edge> edgeList) {
        for (int i = 0; i < edgeList.size(); ++i) {
            
            if (edgeList.get(i).toNode.equals(superGraph.target)) return edgeList.get(i);
        }
        return null;
    }
}