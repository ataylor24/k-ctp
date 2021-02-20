import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;



public class BiasCalculation {
    Graph g;
    ArrayList<Edge> edgeList;
    Node subStart;
    Node instStart;
    Map<Edge, Integer> distances;
    Node orig_start;
    Node instance_start;

    public BiasCalculation(Node start, ArrayList<Edge> subEdgeList, Graph g) {
        this.g = new Graph(g, new ArrayList<Edge>());
        this.orig_start = start;
        this.instance_start = retrieveEquivNode(start);
        distances = new TreeMap<Edge,Integer>();
    }


    public Map<Edge, Integer> calcNodeDist() {
        findValidPaths();
        return this.distances;
    }


    private Node retrieveEquivNode(Node paramNode) {
        for (Node node : this.g.nodeList) {
            if (node.equals(paramNode))
                return node;
        }
        return null;
    }


    private void findValidPaths() {
        ArrayList<Node> arrayList;
        do {
            arrayList = this.g.findShortestPath(this.instance_start, this.g.target);

            if (arrayList == null)
                continue;
            Edge edge = checkEdgeList(arrayList.get(0), this.instance_start);

            if (edge == null)
                continue;
            this.distances.put(edge, Integer.valueOf(arrayList.size()));
            this.g = updateGraph(this.g, arrayList);
            this.instance_start = retrieveEquivNode(this.instance_start);



        } while (this.g.edgeList.size() > 0 && arrayList != null && arrayList.size() > 0);
    }


    private Edge checkEdgeList(Node paramNode1, Node paramNode2) {
        Edge edge = new Edge(paramNode1, paramNode2, 1);
        for (Edge edge1 : paramNode2.edgeList) {
            if (edge1.equals(edge)) {
                return edge1;
            }
        }

        return null;
    }

    private Graph updateGraph(Graph paramGraph, ArrayList<Node> paramArrayList) {
        ArrayList<Edge> arrayList = new ArrayList();
        arrayList.add(new Edge(this.instance_start, paramArrayList.get(0), 1));

        return new Graph(paramGraph, arrayList);
    }
}


/*
 * Location: /Users/alextaylor/Desktop/sim_class_v15/!/BiasCalculation.class Java compiler version:
 * 9 (53.0) JD-Core Version: 1.1.3
 */
