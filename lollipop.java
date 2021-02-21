import java.util.ArrayList;
import java.util.Random;

public class lollipop extends Graph {
    // m,n lollipop graph

    int numNodes_line;

    public lollipop(int numNodes, int numNodes_line, int start, int target) {
            super(numNodes, start, target);
            this.numNodes_line = numNodes_line;
            constructEdgeList();
        }

    private void constructEdgeList() {
        int cutoff = nodeList.size() - numNodes_line;
        
        for (int i = 0; i < cutoff; ++i) {
            for (int j = 0; j < cutoff; ++j) {
                if (i != j) {
                    int weight = this.weight;
                    Edge fromEdge = new Edge(nodeList.get(j), nodeList.get(i), weight);
                    nodeList.get(i).edgeList.add(fromEdge);
                    edgeList.add(fromEdge);
                }
            }
        }

        for (int i = cutoff-1; i < nodeList.size() - 1; ++i) {
            Node node1 = nodeList.get(i);
            Node node2 = nodeList.get(i + 1);
            Edge newEdge = new Edge(node2, node1, weight);
            node1.edgeList.add(newEdge);
            node2.edgeList.add(newEdge.flipEdge());
            edgeList.add(newEdge);
            edgeList.add(newEdge.flipEdge());
        }
    }
    
}
