import java.util.ArrayList;
import java.util.Random;

public class Cyclic extends Graph {

    public Cyclic(int numNodes, int start, int target) {
        super(numNodes, start, target);

        constructEdgeList();
    }

    private void constructEdgeList() {
        for (int i = 0; i < nodeList.size(); ++i) {
            int weight = 1;
            Node node1 = nodeList.get(i);
            Node node2;
            if (i != nodeList.size() - 1) {
                node2 = nodeList.get(i + 1);
            } else {
                node2 = nodeList.get(0);
            }
            Edge newEdge = new Edge(node2, node1, weight);
            Edge altEdge = newEdge.flipEdge();
            node1.edgeList.add(newEdge);
            node2.edgeList.add(altEdge);
            edgeList.add(newEdge);
            edgeList.add(altEdge);
        }
    }
}
