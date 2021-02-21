import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class CsvWriter {
    public enum k_approach {
        ALL_K, FIXED_K, RAND_K
    }
    public enum algos {
        BiasedRandWalk, BiasedRandWalkMemory, BiasedRandWalkMemWeighting, SimpleRandomWalk, SimpleRandWalkMemory,
        BiasedRandWalkSmart, BiasedRandWalkMemorySmart, BiasedRandWalkMemWeightingSmart, SimpleRandomWalkSmart, 
        SimpleRandWalkMemorySmart, BiasedRandWalkMemoryFlush, BiasedRandWalkMemWeightingFlush, SimpleRandWalkMemoryFlush
    }

    public static void main(String[] args) throws FileNotFoundException {
        Random rand = new Random();
        PrintWriter pw = null;

        
        int[] k_sizes = new int[] {5, 50, 250,500, 1000};//new int[] {1};
        
        int[] memSize_sizes = new int[] {5, 50, 500, 5000};//new int[] {1};
        
        
        int k;
        int memSize;
        long seed = rand.nextLong();
        //String[] graphTypes = new String[] {"grid", "cyclic", "lollipop", "complete"};
        String[] graphTypes = new String[] {"cyclic"};
        for (String graphType : graphTypes) {
            int numNodes = 35;
            if (graphType.equals("grid")) numNodes = (int) Math.sqrt(numNodes);
            if (graphType.equals("complete")) numNodes = 625; // specific
            if (graphType.equals("lollipop")) numNodes = 200;
            pw = new PrintWriter(new File("/Users/alextaylor/Desktop/directed_study/cyclic_runs/" + graphType + "_Input_35.csv"));
            StringBuilder str = new StringBuilder();
            for (int index_k = 0; index_k < k_sizes.length; ++index_k) {
                k = k_sizes[index_k];
                for (int index_mem = 0; index_mem < memSize_sizes.length; ++index_mem) {
                    memSize = memSize_sizes[index_mem];
                
                    for (int i = 1; i < 2; ++i) {
                        
                        if (numNodes/2 < k_sizes[index_k] || numNodes < memSize_sizes[index_mem]) continue;
                        
                        int numNodes_line = -1; //LOLLIPOP
                        
                        if (graphType.equals("lollipop")) {
                            numNodes_line = numNodes/2; //REMOVE IF NOT LOLLIPOP;
                        }
                        //rand.setSeed(10009);
            
                        
                        int source = 0;
                        int target;
                        if (graphType.equals("grid")) target = (int) Math.pow(numNodes, 2) - 1; //grid
                        else if (graphType.equals("cyclic")) target = numNodes - 1; //cyclic
                        else target = numNodes-1; //lollipop or complete
            
                        int num_rand_s_and_t = 1;
            
                        int num_runs_per_instance = 1;
                        int num_rand_runs = 1;
            
                        if (graphType.equals("lollipop")) {
                            str.append(
                                "" + k + "," + k_approach.RAND_K.toString() + "," + seed + "," + numNodes +
                                "," + graphType + ","+ numNodes_line + ","  + source + "," + target + "," + 
                                num_rand_s_and_t + ",[");
                        }
                        else {
                            if (graphType.equals("cyclic")) k = 1;
                            if (graphType.equals("complete")) {
                                if (k > numNodes - 1) k = numNodes - 2;
                            }
                            str.append(
                                "" + k + "," + k_approach.RAND_K.toString() + "," + seed + "," + numNodes +
                                "," + graphType + ","  + source + "," + target + "," + 
                                num_rand_s_and_t + ",[");
                        }
                        
                        /*String algorithms = "" + algos.BiasedRandWalk.toString() + "_" + algos.BiasedRandWalkMemory + memSize
                                + "_" + algos.BiasedRandWalkMemWeighting + memSize + "_"
                                + algos.SimpleRandomWalk + "_" + algos.SimpleRandWalkMemory + memSize + "_"
                                + algos.BiasedRandWalkSmart + "_" + algos.BiasedRandWalkMemorySmart + memSize
                                + "_" + algos.BiasedRandWalkMemWeightingSmart + memSize + "_"
                                + algos.SimpleRandomWalkSmart + "_" + algos.SimpleRandWalkMemorySmart + memSize
                                + "_" + algos.BiasedRandWalkMemoryFlush + memSize + "_" 
                                + algos.BiasedRandWalkMemWeightingFlush + memSize + "_"
                                + algos.SimpleRandWalkMemoryFlush + memSize;*/
                        String algorithms = "" + algos.BiasedRandWalk.toString()  + "_" + algos.SimpleRandomWalk.toString();
                    
                        
                        str.append(algorithms);
                        str.append("]," + num_runs_per_instance + "," + num_rand_runs + "\n");
                        
                    }
                    System.out.println("mem complete");
                }
                System.out.println("k complete");
            }
            
            pw.write(str.toString());

            pw.close();
        }
        

    }
}
