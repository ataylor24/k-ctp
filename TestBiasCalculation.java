/*    */ import java.util.ArrayList;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class TestBiasCalculation {
/*    */   public static void main(String[] paramArrayOfString) {
/*  6 */     Cyclic cyclic = new Cyclic(10, 0, 9);
/*  7 */     ArrayList<Edge> arrayList = new ArrayList();
/*  8 */     arrayList.add(new Edge(cyclic.target, cyclic.start, 1));
/*  9 */     Graph graph = new Graph(cyclic, arrayList);
/*    */ 
/*    */     
/* 12 */     for (Node node : graph.nodeList) {
/* 13 */       BiasCalculation biasCalculation = new BiasCalculation(node, node.edgeList, cyclic);
/* 14 */       Map<Edge, Integer> map = biasCalculation.calcNodeDist();
/* 15 */       for (Node node1 : cyclic.nodeList);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/alextaylor/Desktop/sim_class_v15/!/TestBiasCalculation.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */