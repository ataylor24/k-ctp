

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
                    nodeList.get(j).edgeList.add(fromEdge);
                    edgeList.add(fromEdge);
                }
            }
        }
    }
}


/*
 * Location: /Users/alextaylor/Desktop/sim_class_v15/!/Complete.class Java compiler version: 9
 * (53.0) JD-Core Version: 1.1.3
 */
