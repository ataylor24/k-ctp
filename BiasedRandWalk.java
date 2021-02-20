 import java.math.BigInteger;
 
 public class BiasedRandWalk
   extends Algorithm {
   public BiasedRandWalk(Long seed, Graph superGraph, Graph subGraph) {
    super(seed, superGraph, subGraph);
}

   public void traverseGraph() {
       Bias bias = new Bias(rand);
       Node curNode = subGraph.start;
       int traversals = 0;
       
       while (!curNode.equals(subGraph.target)) {
         //create a Double ArrayList of 1's
           Edge tempEdge = bias.findNextEdgeForBiasRandWalk(curNode.edgeList, null, superGraph);
           
           distance = distance.add(BigInteger.valueOf(tempEdge.weight));
           
           curNode = tempEdge.toNode;
           
           traversals++;
           if (traversals >= max_traversal) {
               distance = BigInteger.valueOf(-2);
               return;
           }
       }
       
   }

   /*
    * Reads in the config file for each algorithm
    */
   public void readConfig() {

   }
 }


/* Location:              /Users/alextaylor/Desktop/sim_class_v15/!/BiasedRandWalk.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */