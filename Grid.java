import java.util.ArrayList;
import java.util.Random;

public class Grid extends Graph {


    public Grid(int dimension, int start, int target) {
        super((int) Math.pow(dimension, 2), start, target);

        constructEdgeList(dimension);
    }

    private void constructEdgeList(int dimension) {
        for (int i = 0; i < nodeList.size(); ++i) {
            
            if (i - dimension >= 0) {
                int weight = this.weight;
                Edge upEdge = new Edge(nodeList.get(i - dimension), nodeList.get(i), weight);
                Edge altUpEdge = new Edge(nodeList.get(i), nodeList.get(i - dimension), weight);
                nodeList.get(i).edgeList.add(upEdge);
                nodeList.get(i - dimension).edgeList.add(altUpEdge);
                edgeList.add(upEdge);
                edgeList.add(altUpEdge);
            }
            if ((i + 1) % dimension != 0 && i + 1 < nodeList.size()) {
                int weight = this.weight;
                Edge adjEdge = new Edge(nodeList.get(i + 1), nodeList.get(i), weight);
                Edge altAdjEdge = new Edge(nodeList.get(i), nodeList.get(i + 1), weight);
                nodeList.get(i).edgeList.add(adjEdge);
                nodeList.get(i + 1).edgeList.add(altAdjEdge);
                edgeList.add(adjEdge);
                edgeList.add(altAdjEdge);
            }
            //consolodate edge creation into method
        }
    }
    //create edge method, checks if the edge is valid 
}
