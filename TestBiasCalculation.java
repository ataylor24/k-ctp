import java.util.ArrayList;
import java.util.Map;

public class TestBiasCalculation {
    public static void main(String[] args) {
        Graph test_cycle = new Cyclic(10, 0, 9);
        ArrayList<Edge> removal = new ArrayList<Edge>();
        removal.add(new Edge(test_cycle.target,test_cycle.start,1));
        Graph sub_cycle = new Graph(test_cycle, removal);
        
        Map<Edge, Integer> distances;
        for (Node cur_node : sub_cycle.nodeList) {
            BiasCalculation biasCalc = new BiasCalculation(cur_node, cur_node.edgeList, test_cycle);
            distances = biasCalc.calcNodeDist();
            for (Node node : test_cycle.nodeList) {
                
            }
        }
        
        
    }
}
