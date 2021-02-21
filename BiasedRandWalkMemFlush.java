import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;

public class BiasedRandWalkMemFlush extends Algorithm{
    LinkedList<Node> memory;
    int memorySize;
        public BiasedRandWalkMemFlush(Long seed, Graph superGraph, Graph subGraph, int memorySize) {
        super(seed, superGraph, subGraph);
        // TODO Auto-generated constructor stub
        this.memory = new LinkedList<Node>();
        this.memorySize = memorySize;
    }
    /*
     * Traverses the graph according the specifications of the algorithm
     */
    public void  traverseGraph() {
        
        Bias bias = new Bias(rand);
        Node curNode = subGraph.start;
        int traversals = 0;
        memory.add(curNode);
        while (!curNode.equals(subGraph.target)) {
            
            Edge chosenEdge = null;
            ArrayList<Edge> viableEdges = new ArrayList<Edge>();
            for (int i = 0; i < curNode.edgeList.size(); ++i) {
                //build an arrayList of viable edges by for loop, and then randomly choose from that size
                if (!memory.contains(curNode.edgeList.get(i).toNode)) {
                    viableEdges.add(curNode.edgeList.get(i));
                }
            }
            Edge tempEdge = null;
            //create a Double ArrayList of 1's
            if (viableEdges.size() > 0) {
               
                tempEdge = bias.findNextEdgeForBiasRandWalk(viableEdges, null, superGraph);
            }
            else {
                memory.clear();
                tempEdge = bias.findNextEdgeForBiasRandWalk(curNode.edgeList, null, superGraph);
            }
            
            distance = distance.add(BigInteger.valueOf(tempEdge.weight));
            
            memory.addLast(curNode);
            
            curNode = tempEdge.toNode;
            
            if (memory.size() >= memorySize) memory.removeFirst();
            
            traversals++;
            if (traversals >= max_traversal) {
                distance = BigInteger.valueOf(-2);
                return;
            }
        }
    }
}
