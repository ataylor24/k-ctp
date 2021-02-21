
public class testDijkstra {
    public static void testDijkstra(int dimension, int start, int target, long seed) {
        Graph grid = new Grid(dimension,start,target);
        Dijkstra sp = new Dijkstra();
        //sp.findShortestPath(grid, grid.start, grid.target);
        System.out.println(sp.findShortestPath(grid, grid.start, grid.target));
    }
    public static void main(String[] args) {
        testDijkstra(3,0,8,9001);
    }
}
