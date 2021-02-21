import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class CsvLineWriter {
    public enum k_approach {
        ALL_K, FIXED_K, RAND_K
    }
    public enum algos {
        BiasedRandWalk, BiasedRandWalkMemory, BiasedRandWalkMemWeighting, SimpleRandomWalk, SimpleRandWalkMemory, BiasedRandWalkSmart, BiasedRandWalkMemorySmart, BiasedRandWalkMemWeightingSmart, SimpleRandomWalkSmart, SimpleRandWalkMemorySmart, BiasedRandWalkMemoryFlush, BiasedRandWalkMemWeightingFlush, SimpleRandWalkMemoryFlush
    }

    public static void main(String[] args) throws FileNotFoundException {
        Random rand = new Random();
        PrintWriter pw = null;


        int[] k_sizes = new int[] {5};//new int[] {5, 50, 250, 500, 1000};// 

        int[] memSize_sizes = new int[] {5};//new int[] {5, 50, 500, 5000};// 


        int k;
        int memSize;
        long seed = rand.nextLong();
        // String[] graphTypes = new String[] {"grid", "cyclic", "lollipop", "complete"};
        String[] graphTypes = new String[] {"lollipop", "complete"};

        int line_counter = 0; // COUNTS THE LINE OF INPUT

        
            for (String graphType : graphTypes) {
                // int numNodes = 20;
                for (int numNodes = 20; numNodes < 60; ++numNodes) {
                    for (int j = 0; j < 1; ++j) {



                    /*if (graphType.equals("complete"))
                        numNodes = 50; // set to 625 when generating results specific
                    if (graphType.equals("lollipop"))
                        numNodes = 50;*/


                    for (int index_k = 0; index_k < k_sizes.length; ++index_k) {
                        

                        k = k_sizes[index_k];

                        for (int index_mem = 0; index_mem < memSize_sizes.length; ++index_mem) {
                            memSize = memSize_sizes[index_mem];


                            if (numNodes / 2 < k)
                                continue;

                            int numNodes_line = -1; // LOLLIPOP

                            if (graphType.equals("lollipop")) {
                                numNodes_line = numNodes / 2; // REMOVE IF NOT LOLLIPOP;
                                if (k > numNodes_line)
                                    continue;
                            }

                            StringBuilder str = new StringBuilder();
                            pw = new PrintWriter(
                                new File("/Users/alextaylor/Desktop/directed_study/LDLollipop_complete/"
                                    + graphType + "_" + line_counter++ + "_Input.csv"));

                            int source = 0;
                            int target;
                            if (graphType.equals("grid"))
                                target = (int) Math.pow(numNodes, 2) - 1; // grid
                            else
                                target = numNodes - 1; // lollipop, cyclic, complete

                            int num_rand_s_and_t = 5;

                            int num_runs_per_instance = 10;
                            int num_rand_runs = 10;

                            if (graphType.equals("lollipop")) {
                                str.append("" + k + "," + k_approach.RAND_K.toString() + "," + seed
                                    + "," + numNodes + "," + graphType + "," + numNodes_line + ","
                                    + source + "," + target + "," + num_rand_s_and_t + ",[");
                            } else {
                                if (graphType.equals("cyclic"))
                                    k = 1;
                                if (graphType.equals("complete")) {
                                    if (k > numNodes - 1)
                                        k = numNodes - 2;
                                }

                                str.append("" + k + "," + k_approach.RAND_K.toString() + "," + seed
                                    + "," + numNodes + "," + graphType + "," + source + "," + target
                                    + "," + num_rand_s_and_t + ",[");
                            }

                            String algorithms = "" + algos.BiasedRandWalk.toString() + "_"
                                + algos.BiasedRandWalkMemory + memSize + "_"
                                + algos.BiasedRandWalkMemWeighting + memSize + "_"
                                + algos.SimpleRandomWalk + "_" + algos.SimpleRandWalkMemory
                                + memSize + "_" + algos.BiasedRandWalkSmart + "_"
                                + algos.BiasedRandWalkMemorySmart + memSize + "_"
                                + algos.BiasedRandWalkMemWeightingSmart + memSize + "_"
                                + algos.SimpleRandomWalkSmart + "_"
                                + algos.SimpleRandWalkMemorySmart + memSize + "_"
                                + algos.BiasedRandWalkMemoryFlush + memSize + "_"
                                + algos.BiasedRandWalkMemWeightingFlush + memSize + "_"
                                + algos.SimpleRandWalkMemoryFlush + memSize;


                            str.append(algorithms);
                            str.append("]," + num_runs_per_instance + "," + num_rand_runs + "\n");

                            pw.write(str.toString());
                            pw.close();

                        }


                    }

                }


            }
        }

    }
}
