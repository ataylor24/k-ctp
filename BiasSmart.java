import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

/*
 * contains all of the methods for calculating the bias for the biased algorithms
 */
public class BiasSmart {
    Random rand;
    
    public BiasSmart(Random rand) {
        this.rand = rand;
    }
    
    /*
     * @param edgeList: available edges for the graph to traverse
     * @param superGraph: graph with all possible edges
     */
    public Edge findNextEdgeForBiasRandWalk(ArrayList<Edge> edgeList, ArrayList<Double> weighting, Graph superGraph) {
        //System.out.println(superGraph.target);
        //System.out.println("smart bias");
        for (int i = 0; i < edgeList.size(); ++i) {
            
            if (edgeList.get(i).toNode.equals(superGraph.target)) return edgeList.get(i);
        }
        
        ArrayList<Pair<Integer,Double>> biases = calculateBias_2(edgeList, weighting, superGraph);
        //for (Pair bias : biases) System.out.println("bias " + bias.val2);
      
        double choice = rand.nextDouble();
        double stack = 0.0;
        for (Pair<Integer,Double> bias : biases) {
            stack += (Double) bias.val2;
            int index = (int) bias.val1;
            
            
            
            if (choice <= stack) {
                return edgeList.get(index);
            }
            
        }
        return null;
    }
    
    static Edge findSuperEdge(Edge subEdge, Graph superGraph) {
        for (Edge supEdge : superGraph.edgeList) {
            if (subEdge.equals(supEdge)) {
                return supEdge;
            }
        }
        return null;
    }
    
    @Deprecated
    static ArrayList<Pair<Integer,Double>> calculateBias(ArrayList<Edge> edgeList, ArrayList<Double> weighting, Graph graph) {
        ArrayList<Pair<Integer,Double>> biases = new ArrayList<Pair<Integer,Double>>();
        double sum_edge_paths = 0;
        double inverse_total_weight = 0;
        for (Edge subEdge : edgeList) {
            Edge edge = findSuperEdge(subEdge, graph);
            sum_edge_paths += graph.calcDistance(edge.toNode, graph.target); //fix dijkstras CAUSING PROBLEM
        }
        
        for (int i = 0; i < edgeList.size(); ++i) {
            Edge edge = edgeList.get(i);
            inverse_total_weight += sum_edge_paths / graph.calcDistance(edge.toNode, graph.target);
        }
        
        Integer counter = 0;
        for (Edge edge : edgeList) {
            Double bias = (sum_edge_paths /graph.calcDistance(edge.toNode, graph.target)) / inverse_total_weight;
            Pair<Integer,Double> holder = new Pair<Integer,Double>(counter, bias);
            biases.add(holder);
            ++counter;
        }
        
        return biases;
    }
    static ArrayList<Pair<Integer,Double>> calculateBias_2(ArrayList<Edge> edgeList, ArrayList<Double> weighting, Graph graph) {
        ArrayList<Pair<Integer,Double>> biases = new ArrayList<Pair<Integer,Double>>();
        Map<Edge, Integer> distances;
        double sum_edge_paths = 0;
        double inverse_total_weight = 0;
        
        BiasCalculation calculateBias = new BiasCalculation(edgeList.get(0).fromNode, edgeList, graph);
        distances = calculateBias.calcNodeDist();
        
        //System.out.println(graph);
        //System.out.println(edgeList);
        for (int i = 0; i < edgeList.size(); ++i) {
            
            //System.out.println(edgeList.get(i));
            
            if (distances.get(edgeList.get(i)) != null ) sum_edge_paths += distances.get(edgeList.get(i)); //fix dijkstras CAUSING PROBLEM
        }
        
        
        for (int i = 0; i < edgeList.size(); ++i) {
            if (distances.get(edgeList.get(i)) != null ) inverse_total_weight += sum_edge_paths / distances.get(edgeList.get(i));
        }
        
        Integer counter = 0;
        for (int i = 0; i < edgeList.size(); ++i) {
            Double bias = (distances.get(edgeList.get(i)) != null ) ? 
                (sum_edge_paths /distances.get(edgeList.get(i))) / inverse_total_weight : 0;
            Pair<Integer,Double> holder = new Pair<Integer,Double>(counter, bias);
            biases.add(holder);
            ++counter;
        }
        
        return biases;
    }
}