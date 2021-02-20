/*    */ public class testDijkstra
/*    */ {
/*    */   public static void testDijkstra(int paramInt1, int paramInt2, int paramInt3, long paramLong) {
/*  4 */     Grid grid = new Grid(paramInt1, paramInt2, paramInt3);
/*  5 */     Dijkstra dijkstra = new Dijkstra();
/*    */     
/*  7 */     System.out.println(dijkstra.findShortestPath(grid, grid.start, grid.target));
/*    */   }
/*    */   public static void main(String[] paramArrayOfString) {
/* 10 */     testDijkstra(3, 0, 8, 9001L);
/*    */   }
/*    */ }


/* Location:              /Users/alextaylor/Desktop/sim_class_v15/!/testDijkstra.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */