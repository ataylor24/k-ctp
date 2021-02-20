/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class CsvLineWriter
/*     */ {
/*     */   public enum k_approach {
/*   9 */     ALL_K, FIXED_K, RAND_K; }
/*     */   
/*     */   public enum algos {
/*  12 */     BiasedRandWalk, BiasedRandWalkMemory, BiasedRandWalkMemWeighting, SimpleRandomWalk, SimpleRandWalkMemory, BiasedRandWalkSmart, BiasedRandWalkMemorySmart, BiasedRandWalkMemWeightingSmart, SimpleRandomWalkSmart, SimpleRandWalkMemorySmart, BiasedRandWalkMemoryFlush, BiasedRandWalkMemWeightingFlush, SimpleRandWalkMemoryFlush;
/*     */   }
/*     */   
/*     */   public static void main(String[] paramArrayOfString) throws FileNotFoundException {
/*  16 */     Random random = new Random();
/*  17 */     PrintWriter printWriter = null;
/*     */ 
/*     */     
/*  20 */     int[] arrayOfInt1 = { 5 };
/*     */     
/*  22 */     int[] arrayOfInt2 = { 5 };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  27 */     long l = random.nextLong();
/*     */     
/*  29 */     String[] arrayOfString = { "lollipop", "complete" };
/*     */     
/*  31 */     byte b = 0;
/*     */ 
/*     */     
/*  34 */     for (String str : arrayOfString) {
/*     */       
/*  36 */       for (byte b1 = 20; b1 < 60; b1++) {
/*  37 */         for (byte b2 = 0; b2 < 1; b2++) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  47 */           for (byte b3 = 0; b3 < arrayOfInt1.length; b3++) {
/*     */ 
/*     */             
/*  50 */             int i = arrayOfInt1[b3];
/*     */             
/*  52 */             for (byte b4 = 0; b4 < arrayOfInt2.length; b4++) {
/*  53 */               int m, j = arrayOfInt2[b4];
/*     */ 
/*     */               
/*  56 */               if (b1 / 2 < i) {
/*     */                 continue;
/*     */               }
/*  59 */               int k = -1;
/*     */               
/*  61 */               if (str.equals("lollipop")) {
/*  62 */                 k = b1 / 2;
/*  63 */                 if (i > k) {
/*     */                   continue;
/*     */                 }
/*     */               } 
/*  67 */               StringBuilder stringBuilder = new StringBuilder();
/*  68 */               printWriter = new PrintWriter(new File("/Users/alextaylor/Desktop/directed_study/LDLollipop_complete/" + str + "_" + b++ + "_Input.csv"));
/*     */ 
/*     */ 
/*     */               
/*  72 */               boolean bool = false;
/*     */               
/*  74 */               if (str.equals("grid")) {
/*  75 */                 m = (int)Math.pow(b1, 2.0D) - 1;
/*     */               } else {
/*  77 */                 m = b1 - 1;
/*     */               } 
/*  79 */               byte b5 = 5;
/*     */               
/*  81 */               byte b6 = 10;
/*  82 */               byte b7 = 10;
/*     */               
/*  84 */               if (str.equals("lollipop")) {
/*  85 */                 stringBuilder.append("" + i + "," + i + "," + k_approach.RAND_K.toString() + "," + l + "," + b1 + "," + str + "," + k + "," + bool + "," + m + ",[");
/*     */               }
/*     */               else {
/*     */                 
/*  89 */                 if (str.equals("cyclic"))
/*  90 */                   i = 1; 
/*  91 */                 if (str.equals("complete") && 
/*  92 */                   i > b1 - 1) {
/*  93 */                   i = b1 - 2;
/*     */                 }
/*     */                 
/*  96 */                 stringBuilder.append("" + i + "," + i + "," + k_approach.RAND_K.toString() + "," + l + "," + b1 + "," + str + "," + bool + "," + m + ",[");
/*     */               } 
/*     */ 
/*     */ 
/*     */               
/* 101 */               String str1 = algos.BiasedRandWalk.toString() + "_" + algos.BiasedRandWalk.toString() + algos.BiasedRandWalkMemory + "_" + j + algos.BiasedRandWalkMemWeighting + "_" + j + "_" + algos.SimpleRandomWalk + algos.SimpleRandWalkMemory + "_" + j + "_" + algos.BiasedRandWalkSmart + algos.BiasedRandWalkMemorySmart + "_" + j + algos.BiasedRandWalkMemWeightingSmart + "_" + j + "_" + algos.SimpleRandomWalkSmart + algos.SimpleRandWalkMemorySmart + "_" + j + algos.BiasedRandWalkMemoryFlush + "_" + j + algos.BiasedRandWalkMemWeightingFlush + "_" + j + algos.SimpleRandWalkMemoryFlush;
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
/*     */               
/* 115 */               stringBuilder.append(str1);
/* 116 */               stringBuilder.append("]," + b6 + "," + b7 + "\n");
/*     */               
/* 118 */               printWriter.write(stringBuilder.toString());
/* 119 */               printWriter.close();
/*     */               continue;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/alextaylor/Desktop/sim_class_v15/!/CsvLineWriter.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */