

public class Cyclic extends Graph {

    public Cyclic(int numNodes, int start, int target) {
        super(numNodes, start, target);

        constructEdgeList();
    }

    private void constructEdgeList() {
        for (int i = 0; i < nodeList.size(); ++i) {
            // int weight = 1;
            Node node1 = nodeList.get(i);
            Node node2;
            if (i != nodeList.size() - 1) {
                node2 = nodeList.get(i + 1);
            } else {
                node2 = nodeList.get(0);
            }
            Edge newEdge = new Edge(node2, node1, this.weight);
            Edge altEdge = newEdge.flipEdge();
            node1.edgeList.add(newEdge);
            node2.edgeList.add(altEdge);
            edgeList.add(newEdge);
            edgeList.add(altEdge);
        }
    }
}


/*
 * Location: /Users/alextaylor/Desktop/sim_class_v15/!/Cyclic.class Java compiler version: 9 (53.0)
 * JD-Core Version: 1.1.3
 */
