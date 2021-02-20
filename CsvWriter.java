/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class CsvWriter
/*     */ {
/*     */   public enum k_approach {
/*   9 */     ALL_K, FIXED_K, RAND_K; }
/*     */   
/*     */   public enum algos {
/*  12 */     BiasedRandWalk, BiasedRandWalkMemory, BiasedRandWalkMemWeighting, SimpleRandomWalk, SimpleRandWalkMemory,
/*  13 */     BiasedRandWalkSmart, BiasedRandWalkMemorySmart, BiasedRandWalkMemWeightingSmart, SimpleRandomWalkSmart,
/*  14 */     SimpleRandWalkMemorySmart, BiasedRandWalkMemoryFlush, BiasedRandWalkMemWeightingFlush, SimpleRandWalkMemoryFlush;
/*     */   }
/*     */   
/*     */   public static void main(String[] paramArrayOfString) throws FileNotFoundException {
/*  18 */     Random random = new Random();
/*  19 */     PrintWriter printWriter = null;
/*     */ 
/*     */     
/*  22 */     int[] arrayOfInt1 = { 5, 50, 250, 500, 1000 };
/*     */     
/*  24 */     int[] arrayOfInt2 = { 5, 50, 500, 5000 };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  29 */     long l = random.nextLong();
/*     */     
/*  31 */     String[] arrayOfString = { "cyclic" };
/*  32 */     for (String str : arrayOfString) {
/*  33 */       int i = 35;
/*  34 */       if (str.equals("grid")) i = (int)Math.sqrt(i); 
/*  35 */       if (str.equals("complete")) i = 625; 
/*  36 */       if (str.equals("lollipop")) i = 200; 
/*  37 */       printWriter = new PrintWriter(new File("/Users/alextaylor/Desktop/directed_study/cyclic_runs/" + str + "_Input_35.csv"));
/*  38 */       StringBuilder stringBuilder = new StringBuilder();
/*  39 */       for (byte b = 0; b < arrayOfInt1.length; b++) {
/*  40 */         int j = arrayOfInt1[b];
/*  41 */         for (byte b1 = 0; b1 < arrayOfInt2.length; b1++) {
/*  42 */           int k = arrayOfInt2[b1];
/*     */           
/*  44 */           for (byte b2 = 1; b2 < 2; b2++) {
/*     */             
/*  46 */             if (i / 2 >= arrayOfInt1[b] && i >= arrayOfInt2[b1]) {
/*     */               
/*  48 */               int n, m = -1;
/*     */               
/*  50 */               if (str.equals("lollipop")) {
/*  51 */                 m = i / 2;
/*     */               }
/*     */ 
/*     */ 
/*     */               
/*  56 */               boolean bool1 = false;
/*     */               
/*  58 */               if (str.equals("grid")) { n = (int)Math.pow(i, 2.0D) - 1; }
/*  59 */               else if (str.equals("cyclic")) { n = i - 1; }
/*  60 */               else { n = i - 1; }
/*     */               
/*  62 */               boolean bool2 = true;
/*     */               
/*  64 */               boolean bool3 = true;
/*  65 */               boolean bool4 = true;
/*     */               
/*  67 */               if (str.equals("lollipop")) {
/*  68 */                 stringBuilder.append("" + j + "," + j + "," + k_approach.RAND_K
/*  69 */                     .toString() + "," + l + "," + i + "," + str + "," + m + "," + bool1 + "," + n + ",[");
/*     */               
/*     */               }
/*     */               else {
/*     */                 
/*  74 */                 if (str.equals("cyclic")) j = 1; 
/*  75 */                 if (str.equals("complete") && 
/*  76 */                   j > i - 1) j = i - 2;
/*     */                 
/*  78 */                 stringBuilder.append("" + j + "," + j + "," + k_approach.RAND_K
/*  79 */                     .toString() + "," + l + "," + i + "," + str + "," + bool1 + "," + n + ",[");
/*     */               } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*  93 */               String str1 = algos.BiasedRandWalk.toString() + "_" + algos.BiasedRandWalk.toString();
/*     */ 
/*     */               
/*  96 */               stringBuilder.append(str1);
/*  97 */               stringBuilder.append("]," + bool3 + "," + bool4 + "\n");
/*     */             } 
/*     */           } 
/* 100 */           System.out.println("mem complete");
/*     */         } 
/* 102 */         System.out.println("k complete");
/*     */       } 
/*     */       
/* 105 */       printWriter.write(stringBuilder.toString());
/*     */       
/* 107 */       printWriter.close();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/alextaylor/Desktop/sim_class_v15/!/CsvWriter.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */