/*    */ public class TimeLimit implements ExceptionRunnable {
/*    */   private final ExceptionRunnable r;
/*    */   private final int limit;
/*    */   private Throwable t;
/*    */   
/*    */   public TimeLimit(int paramInt, ExceptionRunnable paramExceptionRunnable) {
/*  7 */     this.limit = paramInt;
/*  8 */     this.r = paramExceptionRunnable;
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized void run() throws Throwable {
/* 13 */     Thread thread = new Thread(new Runnable()
/*    */         {
/*    */           public void run() {
/*    */             try {
/* 17 */               TimeLimit.this.r.run();
/* 18 */             } catch (Throwable throwable) {
/* 19 */               TimeLimit.this.t = throwable;
/*    */             } 
/*    */           }
/*    */         });
/* 23 */     thread.start();
/*    */     try {
/* 25 */       thread.join(this.limit);
/* 26 */       if (thread.isAlive()) {
/* 27 */         thread.stop();
/* 28 */         throw new InterruptedException("Timeout");
/*    */       } 
/* 30 */     } catch (InterruptedException interruptedException) {
/* 31 */       if (this.t == null) {
/* 32 */         this.t = interruptedException;
/*    */       }
/*    */     } 
/* 35 */     if (this.t != null) {
/* 36 */       Throwable throwable = this.t;
/* 37 */       this.t = null;
/* 38 */       throw throwable;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/alextaylor/Desktop/sim_class_v15/!/TimeLimit.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */