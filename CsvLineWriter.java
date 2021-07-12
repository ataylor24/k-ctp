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
        PrintWriter pw_sec = null;
        PrintWriter pw_ter = null;


        int[] k_sizes = new int[] {2};// new int[] {5, 50, 250, 500, 1000};//

        int[] memSize_sizes = new int[] {5};// new int[] {5, 50, 500, 5000};//


        int k;
        int memSize;
        long seed = rand.nextLong();
        // String[] graphTypes = new String[] {"grid", "cyclic", "lollipop", "complete"};
        String[] graphTypes = new String[] {"cyclic"};//{"complete"};

        int line_counter = 0; // COUNTS THE LINE OF INPUT

        String proj_directory = "5D_Cyclic";
        
        //cyclic needs to be 32 nodes

        for (String graphType : graphTypes) {
            // int numNodes = 20;
            for (int numNodes = 32; numNodes < 33; ++numNodes) {
                for (int sourceShift = 0; sourceShift < numNodes; ++sourceShift) {
                for (int numCopies = 0; numCopies < 50 ; ++numCopies) {

                    

                    /*
                     * if (graphType.equals("complete")) numNodes = 50; // set to 625 when
                     * generating results specific if (graphType.equals("lollipop")) numNodes = 50;
                     */


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
                            
                            StringBuilder main_str = new StringBuilder();
                            StringBuilder sec_str = null;
                            StringBuilder ter_str = null;

                            if (graphType.equals("cyclic") || graphType.equals("lollipop")) {
                                sec_str = new StringBuilder();
                                ter_str = new StringBuilder();
                            }
                            
                            
                            

                            int source = sourceShift;
                            int target;
                            if (graphType.equals("grid"))
                                target = (int) Math.pow(numNodes, 2) - 1; // grid
                            else
                                target = numNodes - 1; // lollipop, cyclic, complete

                            int num_rand_s_and_t = 5;

                            int num_runs_per_instance = 50;
                            int num_rand_runs = 1;
                            boolean graph_variant = false;
                            
                            String graph_params = "" + k + "," + k_approach.RAND_K.toString()
                            + "," + seed + "," + numNodes + "," + graphType + "," + graph_variant + ",";
                            
                            if (graphType.equals("lollipop")) {
                                String lollipop_params =
                                   graph_params + numNodes_line + ","
                                        + source + "," + target + "," + num_rand_s_and_t + ",[";

                                main_str.append(lollipop_params);
                                sec_str.append(lollipop_params);
                                ter_str.append(lollipop_params);

                            } else if (graphType.equals("grid")) {
                                
                                
                                graph_params = graph_params + source
                                    + "," + target + "," + num_rand_s_and_t + ",[";
                                
                                main_str.append(graph_params);
                                
                            } else {
                                if (graphType.equals("cyclic"))
                                    k = 1;
                                if (graphType.equals("complete")) {
                                    if (k > numNodes - 1)
                                        k = numNodes - 2;
                                }

                                graph_params = graph_params + source
                                    + "," + target + "," + num_rand_s_and_t + ",[";

                                main_str.append(graph_params);

                                if (graphType.equals("cyclic")) {
                                    sec_str.append(graph_params);
                                    ter_str.append(graph_params);
                                }
                            }

                            String algorithms = null;

                            if (graphType.equals("cyclic")) {
                                sec_str.append("" + algos.BiasedRandWalk.toString());
                                ter_str.append("" + algos.BiasedRandWalkSmart.toString());

                                algorithms = "" + algos.BiasedRandWalkMemory + memSize + "_"
                                    + algos.BiasedRandWalkMemWeighting + memSize + "_"
                                    + algos.SimpleRandomWalk + "_" + algos.SimpleRandWalkMemory
                                    + memSize + "_"
                                    + algos.BiasedRandWalkMemorySmart + memSize + "_"
                                    + algos.BiasedRandWalkMemWeightingSmart + memSize + "_"
                                    + algos.SimpleRandomWalkSmart + "_"
                                    + algos.SimpleRandWalkMemorySmart + memSize + "_"
                                    + algos.BiasedRandWalkMemoryFlush + memSize + "_"
                                    + algos.BiasedRandWalkMemWeightingFlush + memSize + "_"
                                    + algos.SimpleRandWalkMemoryFlush + memSize;
                            } else if (graphType.equals("lollipop")) {
                                sec_str.append("" + algos.SimpleRandomWalk.toString());
                                ter_str.append("" + algos.SimpleRandomWalkSmart.toString());

                                algorithms = "" + algos.BiasedRandWalk + "_" + algos.BiasedRandWalkMemory + memSize + "_"
                                    + algos.BiasedRandWalkMemWeighting + memSize + "_"
                                    + algos.SimpleRandWalkMemory
                                    + memSize + "_" + algos.BiasedRandWalkSmart + "_"
                                    + algos.BiasedRandWalkMemorySmart + memSize + "_"
                                    + algos.BiasedRandWalkMemWeightingSmart + memSize + "_"
                                    
                                    + algos.SimpleRandWalkMemorySmart + memSize + "_"
                                    + algos.BiasedRandWalkMemoryFlush + memSize + "_"
                                    + algos.BiasedRandWalkMemWeightingFlush + memSize + "_"
                                    + algos.SimpleRandWalkMemoryFlush + memSize;
                            } else {


                                algorithms = "" + algos.BiasedRandWalk + "_" + algos.BiasedRandWalkMemory + memSize + "_"
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
                            }
                            // String algorithms = "" + algos.BiasedRandWalk.toString() + "_";

                            main_str.append(algorithms);

                            String alg_suffix =
                                "]," + num_runs_per_instance + "," + num_rand_runs + "\n";
                            main_str.append(alg_suffix);
                            
                            pw = new PrintWriter(new File(
                                "/Users/alextaylor/Desktop/directed_study/" + proj_directory + "/"
                                    + graphType + "_" + line_counter++ + ((graph_variant) ? "_variant":"") +"_Input.csv"));

                            if (graphType.equals("cyclic")) {
                                pw_sec = new PrintWriter(
                                    new File("/Users/alextaylor/Desktop/directed_study/"
                                        + proj_directory + "/" + graphType + "_" + "BR_"
                                        + line_counter++ + "_Input.csv"));
                                pw_ter = new PrintWriter(
                                    new File("/Users/alextaylor/Desktop/directed_study/"
                                        + proj_directory + "/" + graphType + "_" + "BRS_"
                                        + line_counter++ + "_Input.csv"));
                            }
                            if (graphType.equals("lollipop")) {
                                pw_sec = new PrintWriter(
                                    new File("/Users/alextaylor/Desktop/directed_study/"
                                        + proj_directory + "/" + graphType + "_" + "SR_"
                                        + line_counter++ + ((graph_variant) ? "_variant":"") + "_Input.csv"));
                                pw_ter = new PrintWriter(
                                    new File("/Users/alextaylor/Desktop/directed_study/"
                                        + proj_directory + "/" + graphType + "_" + "SRS_"
                                        + line_counter++ + ((graph_variant) ? "_variant":"") + "_Input.csv"));
                            }

                            if (graphType.equals("cyclic") || graphType.equals("lollipop")) {
                                sec_str.append(alg_suffix);
                                ter_str.append(alg_suffix);
                                
                                pw_sec.write(sec_str.toString());
                                pw_ter.write(ter_str.toString());
                                
                                pw_sec.close();
                                pw_ter.close();
                            }

                            pw.write(main_str.toString());
                            pw.close();

                        }


                    }

                }

                }
            }
        }

    }
}
