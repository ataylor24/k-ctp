/*    */ import java.util.LinkedList;
/*    */ 
/*    */ 
/*    */ public class BFS
/*    */ {
/*    */   Node s;
/*    */   Node t;
/*    */   Graph graph;
/*    */   
/*    */   public BFS(Graph paramGraph, Node paramNode1, Node paramNode2) {
/* 11 */     this.graph = paramGraph;
/* 12 */     this.s = paramNode1;
/* 13 */     this.t = paramNode2;
/*    */   }
/*    */   public boolean pathExistence() {
/* 16 */     clear();
/* 17 */     LinkedList<Node> linkedList = new LinkedList();
/*    */     
/* 19 */     linkedList.add(this.s);
/*    */     
/* 21 */     Node node = null;
/* 22 */     while (!linkedList.isEmpty()) {
/*    */       
/* 24 */       node = linkedList.remove();
/* 25 */       if (node.visited == true)
/* 26 */         continue;  if (node.equals(this.t)) {
/* 27 */         return true;
/*    */       }
/*    */ 
/*    */       
/* 31 */       node.visited = true;
/* 32 */       for (Edge edge : node.edgeList) {
/*    */ 
/*    */         
/* 35 */         if (!edge.toNode.visited)
/*    */         {
/* 37 */           linkedList.add(edge.toNode);
/*    */         }
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 43 */     return false;
/*    */   }
/*    */   private void clear() {
/* 46 */     for (Node node : this.graph.nodeList)
/* 47 */       node.visited = false; 
/*    */   }
/*    */ }


/* Location:              /Users/alextaylor/Desktop/sim_class_v15/!/BFS.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */