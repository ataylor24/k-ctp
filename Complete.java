import java.util.ArrayList;
import java.util.Random;

public class Complete extends Graph {

    public Complete(int numNodes, int start, int target) {
        super(numNodes, start, target);

        constructEdgeList();
    }

    private void constructEdgeList() {
        for (int i = 0; i < nodeList.size(); ++i) {
            for (int j = 0; j < nodeList.size(); ++j) {
                if (i != j) {
                    int weight = this.weight;
                    Edge fromEdge = new Edge(nodeList.get(j), nodeList.get(i), weight);
                    nodeList.get(i).edgeList.add(fromEdge);
                    edgeList.add(fromEdge);
                }
            }
        }
    }
}
