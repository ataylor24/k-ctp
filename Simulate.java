import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.lang.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.math.MathContext;

public class Simulate {
    static String rand_k = "RAND_K";
    static String fixed_k = "FIXED_K";
    static String all_k = "ALL_K";
    static Random rand;
    static int count_failures = 0;

    // fields for reading in csv
    static String removed_k_edges;
    static int num_of_rand_instances;
    static int k_approach_chosen;
    static ArrayList<Edge> edgeListRemoved = null;

    // process input INPUT BEGINS
    static int k;

    static String k_approach; // randomly select k edges from edge list of superGraph and

    static Long seed; // used
    static int numNodes; // used
    static String graphType; // used
    static int numNodes_line;

    static int start; // used //-1 random, -2 all
    static int target; // used // same as above

    // add constructor without start and target if possible
    static int num_rand_s_and_t; // used

    static String algorithms; // used

    static int num_runs_per_instance;
    static int num_rand_runs;

    static String human_readable_file;
    static MathContext mc = new MathContext(2, RoundingMode.HALF_UP) ;

    public static void main(String[] args) throws Throwable {
        /*
         * begin with circular, fully-connected, and grid graphs. write csv to produce instances of
         * growing graphs of these kinds (later)
         * 
         * memory size: 1, 3, 10
         * 
         * output: describe what was run as an experiment; (input), ALG1, DIST_OPT, AVG_DIST_ALG,
         * COMP_RATIO,
         */

        // add method to get opt distance in graph

        // if all k, no further info. all possible k edges (only small graphs)
        // if rand_k, num of random edge removals
        // if given_edges, expect a list of specific edges to remove
        // avoid repeats in random runs. maintain an arraylist of memory for all of the runs so as
        // not to repeat instances


        // NUM_OF_RAND_S_AND_T: IF S OR T IS FIXED, DRAW THE RANDOM S/T THAT MANY TIMES

        // memory size is included in the algorithm field


        String input = "";
        File csv = new File(args[0]);
        Scanner scnr;
        long startTime = System.currentTimeMillis();

        String output_filename = "" + args[0].substring(0, args[0].length() - 4) + "_output.csv";
        human_readable_file = "" + args[0].substring(0, args[0].length() - 4) + "_hr_output.csv";
        System.out.println(output_filename);

        PrintWriter pw = new PrintWriter(new File(output_filename));

        System.out.println("starting run...");
        int prog_check = 0;
        try {
            scnr = new Scanner(csv);
            while (scnr.hasNextLine()) {
                ++prog_check;
                // if (prog_check % 50 == 0) System.out.println("Working... " + prog_check);
                input = scnr.nextLine().trim();
                pw.write(readCSVLine(input, prog_check));
                pw.flush();
                long endTime_run = System.currentTimeMillis();

                System.out
                    .println((endTime_run - startTime) / (double) 1000 + " seconds to run input");
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            System.out.println(count_failures);
            pw.close();
        }
        long endTime = System.currentTimeMillis();

        System.out.println((endTime - startTime) / (double) 1000 + " seconds to finish");

        System.out.println(output_filename);
    }

    public static String readCSVLine(String input, int line_num) throws Throwable {

        String[] data = input.split(",");
        if (data.length > 13 || data.length < 11)
            System.out.println("Input not of Correct Length");
        int i = 0;

        // k_approach specific declarations
        removed_k_edges = null;
        num_of_rand_instances = 0;
        k_approach_chosen = 0;
        edgeListRemoved = null;

        // process input INPUT BEGINS
        k = Integer.parseInt(data[i++]);

        k_approach = data[i++]; // randomly select k edges from edge list of superGraph and
                                // remove from subGraph, All_k, fixed_k, random_k
        if (k_approach.equals(rand_k)) {
            k_approach_chosen = 0; // 0
        }

        else if (k_approach.equals(fixed_k)) {
            k_approach_chosen = 1;
            removed_k_edges = data[i++];

        }

        else if (k_approach.equals(all_k))
            k_approach_chosen = 2;

        seed = Long.parseLong(data[i++]); // used
        numNodes = Integer.parseInt(data[i++]); // used
        graphType = data[i++]; // used

        if (graphType.equals("lollipop"))
            numNodes_line = Integer.parseInt(data[i++]);

        start = Integer.parseInt(data[i++]); // used //-1 random, -2 all
        target = Integer.parseInt(data[i++]); // used // same as above

        // add constructor without start and target if possible
        num_rand_s_and_t = Integer.parseInt(data[i++]); // used

        algorithms = data[i++]; // used

        num_runs_per_instance = Integer.parseInt(data[i++]);
        num_rand_runs = Integer.parseInt(data[i++]);

        if (rand == null)
            rand = new Random(seed);


        StringBuilder fileOut = new StringBuilder();
        fileOut.append(
            "INPUT,REMOVAL_MODE,SEED,NUM_NODES,GRAPH_TYPE,SOURCE,TARGET,NUM_RAND_S&T,ALGORITHMS,NUM_RUNS_PER_INST,NUM_RAND_RUNS\n");
        fileOut.append(input + "\n");
        fileOut.append("OUTPUT\n");

        processCSVLine(fileOut);

        return fileOut.toString();
    }


    public static void processCSVLine(StringBuilder fileOut) throws Throwable {


        Graph superGraph = graphType();

        ArrayList<Pair<Integer, Integer>> algorithms_for_instance = processAlgorithms(algorithms);

        Write_to_csv times_elapsed_csv = new Write_to_csv(new long[algorithms_for_instance.size()]);

        ArrayList<ArrayList<Node>> start_target_list = start_target_list(superGraph);

        ArrayList<Node> start_list = start_target_list.get(0);
        ArrayList<Node> target_list = start_target_list.get(1);

        // double[] sum_super_graph = new double[algorithms_for_instance.size()];
        // double[] avg_avg_comp_ratio = new double[algorithms_for_instance.size()];

        for (Node startPoint : start_list) {
            for (Node targetPoint : target_list) {
                superGraph.start = startPoint;
                superGraph.target = targetPoint;


                ArrayList<ArrayList<Edge>> instance_runs = edgeRemovalPerInstance(superGraph);
                // System.out.println(instance_runs);

                // for loop for each edgelist removal to create a given subgraph
                // create for loop for algo's

                /*
                 * for (int m = 0; m < algorithms_for_instance.size(); ++m)
                 * fileOut.append(",ALG,AVG_COST,COMP_RATIO,OPT,BT"); fileOut.append("\n");
                 */

                int inst_counter = 0;

                BigDecimal[] sum_s_and_t = new BigDecimal[algorithms_for_instance.size()];
                for (int i = 0; i < sum_s_and_t.length; ++i) {
                    sum_s_and_t[i] = BigDecimal.ZERO;
                }

                BigDecimal[] av_avCr = new BigDecimal[algorithms_for_instance.size()];
                for (int i = 0; i < sum_s_and_t.length; ++i) {
                    av_avCr[i] = BigDecimal.ZERO;
                }

                int counter = 0;

                double avg_bt_dist = 0; // Average backtrack distance
                double avg_opt = 0; // average optimal distance
                BigDecimal[] average_cost = new BigDecimal[algorithms_for_instance.size()];
                ArrayList<ArrayList<BigDecimal[]>> comp_indiv_runs =
                    new ArrayList<ArrayList<BigDecimal[]>>();

                for (ArrayList<Edge> edges_removed_from_sub : instance_runs) {
                    // System.out.println("THIS IS ONE SUBGRAPH " + counter++);

                    BigDecimal[] sums = new BigDecimal[algorithms_for_instance.size()];
                    for (int i = 0; i < sum_s_and_t.length; ++i) {
                        sums[i] = BigDecimal.ZERO;
                    }

                    ArrayList<BigDecimal[]> indiv_runs = new ArrayList<>();
                    // System.out.println("size of alg " + algorithms_for_instance.size());
                    for (int i = 0; i < algorithms_for_instance.size(); ++i) {
                        indiv_runs.add(new BigDecimal[num_rand_runs]);
                    }

                    // double[][] opts = new double[num_rand_runs][algorithms_for_instance.size()];

                    Graph subGraph = new Graph(superGraph, edges_removed_from_sub);

                    int opt = subGraph.calcDistance(subGraph.start, subGraph.target);

                    avg_opt += opt;

                    BFS pathCheck = null;

                    if (!(graphType.equals("complete") && k <= numNodes - 2))
                        pathCheck = new BFS(subGraph, subGraph.start, subGraph.target);

                    if (pathCheck != null && !pathCheck.pathExistence()) {
                        count_failures++;
                        System.out.println("does not pass path check");
                        continue;
                    }

                    // System.out.println("OPT VALUE " + opt);

                    Algorithm[][] algs = new Algorithm[num_rand_runs][];
                    for (int i = 0; i < num_rand_runs; ++i) {
                        algs[i] = algorithmsForRun(algorithms_for_instance, superGraph, subGraph,
                            sums.length);
                    }

                    // 3- for loops to thread, one to start, one to join, and one to store (sums +=
                    // dist)

                    // PRINT OUT A COMPLETE GRAPH AND A GRID
                    // THEY NEED REFACTORING FOR SOME REASON

                    Backtrack bt = new Backtrack(superGraph, subGraph); // POTENTIALLY NOT
                                                                        // THREADSAFE
                    bt.run();
                    int bt_dist = bt.dist;
                    avg_bt_dist += bt_dist;

                    System.out.println("BT " + bt_dist);
                    System.out.println("OPT " + opt);

                    for (int i = 0; i < num_rand_runs; ++i) {
                        Thread[] threads = new Thread[algs[i].length];
                        for (int j = 0; j < algs[i].length; ++j) {
                            threads[j] = new Thread(algs[i][j]);
                            threads[j].start();
                        }


                        for (int j = 0; j < threads.length; ++j) {
                            long startTime = System.nanoTime();
                            threads[j].join();
                            // times_elapsed_by_alg[i][j] = System.nanoTime() - startTime;
                        }

                        for (int j = 0; j < algs[i].length; ++j) {

                            BigDecimal bigDist = new BigDecimal(algs[i][j].distance);

                            sums[j] = sums[j].add(bigDist);

                            indiv_runs.get(j)[i] = bigDist;
                        }
                    }

                    BigDecimal[] avCr = new BigDecimal[algorithms_for_instance.size()];


                    for (int m = 0; m < sums.length; ++m) {
                        avCr[m] = (sums[m].divide(BigDecimal.valueOf(opt), mc))
                            .divide(BigDecimal.valueOf(num_rand_runs), mc);
                        av_avCr[m] = av_avCr[m].add(avCr[m]);
                        average_cost[m] = sums[m].divide(BigDecimal.valueOf(num_rand_runs)); // was
                        sum_s_and_t[m] = sum_s_and_t[m].add(average_cost[m].divide(BigDecimal.valueOf(num_rand_runs)));
                                                                                             // num_runs_per_instance
                    }

                    inst_counter++;
                    comp_indiv_runs.add(indiv_runs);
                }
                fileOut = updateCSVOutput1(fileOut, average_cost, sum_s_and_t, avg_opt / num_rand_runs,
                    avg_bt_dist / num_rand_runs, algorithms_for_instance);

                fileOut.append("CR_AVG,");
                fileOut = updateCSVOutput2(fileOut, av_avCr, algorithms_for_instance,
                    comp_indiv_runs);

                fileOut.append("\n");
            }
            // times_elapsed_csv.writeToCsv();

        }

        // good average time has good mixing point,
        // worst case don't want a good mixing time


        return;
    }

    public static ArrayList<Pair<Integer, Integer>> processAlgorithms(String algorithms) {
        ArrayList<Pair<Integer, Integer>> algorithm_memory =
            new ArrayList<Pair<Integer, Integer>>();
        String[] algorithms_to_use = algorithms.substring(1, algorithms.length() - 1).split("_");
        // [BiasedRandWalk_BiasedRandWalkMemory5_BiasedRandWalkMemWeighting5_SimpleRandomWalk_SimpleRandWalkMemory]

        for (String algorithm : algorithms_to_use) {
            int memSize = 0;

            if (algorithm.contains("Mem")) {
                int counter = 0;
                for (int i = algorithm.length() - 1; i >= 0; --i) {

                    if (Character.isDigit(algorithm.charAt(i))) {
                        counter += 1;
                    } else
                        break;
                }

                if (counter > 0) {
                    memSize = Integer.parseInt(algorithm.substring(algorithm.length() - counter));
                }
                algorithm = algorithm.substring(0, algorithm.length() - counter);

            }
            if (algorithm.equals("BiasedRandWalk")) {
                algorithm_memory.add(new Pair(0, memSize));
            }
            if (algorithm.equals("BiasedRandWalkMemory")) {
                algorithm_memory.add(new Pair(1, memSize));
            }
            if (algorithm.equals("BiasedRandWalkMemWeighting")) {
                algorithm_memory.add(new Pair(2, memSize));
            }
            if (algorithm.equals("SimpleRandomWalk")) {
                algorithm_memory.add(new Pair(3, memSize));
            }
            if (algorithm.equals("SimpleRandWalkMemory")) {
                algorithm_memory.add(new Pair(4, memSize));
            }
            if (algorithm.equals("BiasedRandWalkSmart")) {
                algorithm_memory.add(new Pair(5, memSize));
            }
            if (algorithm.equals("BiasedRandWalkMemorySmart")) {
                algorithm_memory.add(new Pair(6, memSize));
            }
            if (algorithm.equals("BiasedRandWalkMemWeightingSmart")) {
                algorithm_memory.add(new Pair(7, memSize));
            }
            if (algorithm.equals("SimpleRandomWalkSmart")) {
                algorithm_memory.add(new Pair(8, memSize));
            }
            if (algorithm.equals("SimpleRandWalkMemorySmart")) {
                algorithm_memory.add(new Pair(9, memSize));
            }
            if (algorithm.equals("BiasedRandWalkMemoryFlush")) {
                algorithm_memory.add(new Pair(10, memSize));
            }
            if (algorithm.equals("BiasedRandWalkMemWeightingFlush")) {
                algorithm_memory.add(new Pair(11, memSize));
            }
            if (algorithm.equals("SimpleRandWalkMemoryFlush")) {
                algorithm_memory.add(new Pair(12, memSize));
            }
        }

        return algorithm_memory;
    }

    public static Algorithm[] algorithmsForRun(
        ArrayList<Pair<Integer, Integer>> algorithms_for_instance, Graph superGraph, Graph subGraph,
        int size) {
        Algorithm[] algs = new Algorithm[size];
        int index_alg = 0;

        for (Pair<Integer, Integer> alg_memsize : algorithms_for_instance) {

            switch ((int) alg_memsize.val1) {

                case 0: {
                    algs[index_alg] = new BiasedRandWalk(seed, superGraph, subGraph);
                    break;
                }
                case 1: {
                    algs[index_alg] = new BiasedRandWalkMemory(seed, superGraph, subGraph,
                        (int) alg_memsize.val2);
                    break;
                }
                case 2: {
                    algs[index_alg] = new BiasedRandWalkMemWeighting(seed, superGraph, subGraph,
                        (int) alg_memsize.val2);
                    break;
                }

                case 3: {
                    algs[index_alg] = new SimpleRandomWalk(seed, superGraph, subGraph);
                    break;
                }
                case 4: {
                    algs[index_alg] = new SimpleRandWalkMemory(seed, superGraph, subGraph,
                        (int) alg_memsize.val2);
                    break;
                }
                case 5: {
                    algs[index_alg] = new BiasedRandWalkSmart(seed, superGraph, subGraph);
                    break;
                }
                case 6: {
                    algs[index_alg] = new BiasedRandWalkMemSmart(seed, superGraph, subGraph,
                        (int) alg_memsize.val2);
                    break;
                }
                case 7: {
                    algs[index_alg] = new BiasedRandWalkMemWeightSmart(seed, superGraph, subGraph,
                        (int) alg_memsize.val2);
                    break;
                }

                case 8: {
                    algs[index_alg] = new SimpleRandWalkSmart(seed, superGraph, subGraph);
                    break;
                }
                case 9: {
                    algs[index_alg] = new SimpleRandWalkMemSmart(seed, superGraph, subGraph,
                        (int) alg_memsize.val2);
                    break;
                }
                case 10: {
                    algs[index_alg] = new BiasedRandWalkMemFlush(seed, superGraph, subGraph,
                        (int) alg_memsize.val2);
                    break;
                }
                case 11: {
                    algs[index_alg] = new BiasedRandWalkMemWeightFlush(seed, superGraph, subGraph,
                        (int) alg_memsize.val2);
                    break;
                }
                case 12: {
                    algs[index_alg] = new SimpleRandWalkMemFlush(seed, superGraph, subGraph,
                        (int) alg_memsize.val2);
                    break;
                }
                default: {
                    throw new RuntimeException("Unknown Algorithm");
                }
            }
            ++index_alg;
        }

        return algs;
    }

    public static ArrayList<Edge> parseEdgeList(String edgeList, int numNodes) {

        String[] edges = edgeList.split("\\],\\[");

        ArrayList<Integer[]> holder = new ArrayList<Integer[]>();

        for (int i = 0; i < edges.length; ++i) {
            if (i == 0) {
                edges[i] = edges[i].substring(2);
                String[] temp = edges[i].split(",");
                int toNode = Integer.parseInt(temp[0]);
                int fromNode = Integer.parseInt(temp[1]);
                int weight = Integer.parseInt(temp[2]);

                holder.add(new Integer[] {toNode, fromNode, weight});
            } else if (i == edges.length - 1) {
                edges[i] = edges[i].substring(0, edges[i].length() - 2);
                String[] temp = edges[i].split(",");
                int toNode = Integer.parseInt(temp[0]);
                int fromNode = Integer.parseInt(temp[1]);
                int weight = Integer.parseInt(temp[2]);

                holder.add(new Integer[] {toNode, fromNode, weight});
            } else {
                String[] temp = edges[i].split(",");
                int toNode = Integer.parseInt(temp[0]);
                int fromNode = Integer.parseInt(temp[1]);
                int weight = Integer.parseInt(temp[2]);

                holder.add(new Integer[] {toNode, fromNode, weight});
            }
        }
        return createEdgeList(holder, numNodes);
    }

    public static ArrayList<Edge> createEdgeList(ArrayList<Integer[]> edges, int numNodes) {
        ArrayList<Edge> edgeList = new ArrayList<Edge>();
        ArrayList<Node> nodeList = new ArrayList<Node>();
        for (int i = 0; i < numNodes; ++i) {
            Node node = new Node(new ArrayList<Edge>(), i);
            nodeList.add(node);
        }

        for (Integer[] edge : edges) {
            int toNodeKey = edge[0];
            int fromNodeKey = edge[1];
            int weight = edge[2];

            Node node1 = null;
            Node node2 = null;
            for (Node node : nodeList) {
                if (toNodeKey == node.key) {
                    node1 = node;
                }
                if (fromNodeKey == node.key) {
                    node2 = node;
                }
                if (node1 != null && node2 != null) {
                    Edge newEdge = new Edge(node2, node1, weight);
                    Edge altEdge = new Edge(node1, node2, weight);
                    node2.edgeList.add(newEdge);
                    node1.edgeList.add(altEdge);
                    edgeList.add(newEdge);
                    break;
                }
            }
        }
        return edgeList;
    }

    @Deprecated
    public static StringBuilder deprecated_updateCSVOutput1(StringBuilder fileOut,
        BigDecimal[] average_cost, BigDecimal[] avCr, double opt, int bt_dist,
        ArrayList<Pair<Integer, Integer>> algorithms_for_instance,
        ArrayList<BigDecimal[]> indiv_runs) {
        int count_alg = 0;
        for (Pair<Integer, Integer> alg_memsize : algorithms_for_instance) {

            fileOut.append(retrieve_name(((int) alg_memsize.val1)));

            int temp = count_alg;

            fileOut.append(
                average_cost[count_alg] + "," + avCr[count_alg++] + "," + opt + "," + bt_dist);

            if (count_alg >= algorithms_for_instance.size()) {
                fileOut.append("\n");
                for (int j = 0; j < indiv_runs.get(0).length; ++j) {
                    for (int i = 0; i < algorithms_for_instance.size(); ++i) {

                        fileOut
                            .append("," + retrieve_name((int) algorithms_for_instance.get(i).val1));
                        fileOut.append(indiv_runs.get(i)[j]);
                        if (i == algorithms_for_instance.size() - 1)
                            fileOut.append("\n");
                        else
                            fileOut.append(",,,");
                    }
                }
            } else
                fileOut.append(",");
        }
        // System.out.println(fileOut.toString());
        return fileOut;
    }

    @Deprecated
    public static StringBuilder deprecated_updateCSVOutput2(StringBuilder fileOut,
        BigDecimal[] sum_s_and_t, BigDecimal[] av_avCr,
        ArrayList<Pair<Integer, Integer>> algorithms_for_instance) {
        int count_alg = 0;
        for (Pair<Integer, Integer> alg_memsize : algorithms_for_instance) {

            fileOut.append(retrieve_name(((int) alg_memsize.val1)));
            if (sum_s_and_t[count_alg].equals(BigDecimal.valueOf(0))) {
                fileOut.append(" sum_s_and_t is 0,");
                if (count_alg >= algorithms_for_instance.size())
                    fileOut.append("\n");
            } else {
                fileOut
                    .append(av_avCr[count_alg].divide(BigDecimal.valueOf(num_runs_per_instance)));
                if (count_alg >= algorithms_for_instance.size())
                    fileOut.append("\n");
                else
                    fileOut.append(",,,");
            }
            ++count_alg;
        }
        return fileOut;
    }

    public static StringBuilder updateCSVOutput1(StringBuilder fileOut, BigDecimal[] average_cost, BigDecimal[] sum_s_and_t,
        double opt, double bt_dist, ArrayList<Pair<Integer, Integer>> algorithms_for_instance) {
        fileOut.append("OPT," + opt + "\n");
        fileOut.append("BT," + bt_dist + "\n");
        fileOut.append(",");

        for (Pair<Integer, Integer> alg_memsize : algorithms_for_instance) {


            fileOut.append(retrieve_name(((int) alg_memsize.val1)));
        }
        fileOut.append("\n");
        fileOut.append("AVG_COST,");
        for (int i = 0; i < average_cost.length; i++) {
            fileOut.append(sum_s_and_t[i] + ",");
        }
        fileOut.append("\n");
        return fileOut;
    }

    public static StringBuilder updateCSVOutput2(StringBuilder fileOut,
        BigDecimal[] av_avCr, ArrayList<Pair<Integer, Integer>> algorithms_for_instance,
        ArrayList<ArrayList<BigDecimal[]>> comp_indiv_runs) {
        int count_alg = 0;
        for (Pair<Integer, Integer> alg_memsize : algorithms_for_instance) {
            fileOut.append(av_avCr[count_alg].divide(BigDecimal.valueOf(num_runs_per_instance)));
            if (count_alg >= algorithms_for_instance.size())
                fileOut.append("\n");
            else
                fileOut.append(",");
            ++count_alg;
        }
        fileOut.append("\n");

        fileOut.append("INDIV RUN DATA\n");
        for (int i = 0; i < comp_indiv_runs.size(); i++) {
            fileOut.append("Run " + i + ",");
            for (int j = 0; j < comp_indiv_runs.get(i).size(); j++) {
                BigDecimal sum = BigDecimal.ZERO;
                BigDecimal num_rand_runs = BigDecimal.valueOf(comp_indiv_runs.get(i).get(j).length);
                for (int k = 0; k < comp_indiv_runs.get(i).get(j).length; k++) {
                    sum = sum.add(comp_indiv_runs.get(i).get(j)[k]);
                }
                fileOut.append(sum.divide(num_rand_runs) + ",");
            }
            fileOut.append("\n");
        }
        return fileOut;
    }

    public static String retrieve_name(int algo_index) {
        switch (algo_index) {

            case 0: {
                return "BR,";
            }
            case 1: {
                return "BRM,";
            }
            case 2: {
                return "BRMW,";
            }
            case 3: {
                return "SR,";
            }
            case 4: {
                return "SRM,";
            }
            case 5: {
                return "BRS,";
            }
            case 6: {
                return "BRMS,";
            }
            case 7: {
                return "BRMWS,";
            }
            case 8: {
                return "SRS,";
            }
            case 9: {
                return "SRMS,";
            }
            case 10: {
                return "BRMF,";
            }
            case 11: {
                return "BRMWF,";
            }
            case 12: {
                return "SRMF,";
            }
            default: {
                throw new RuntimeException("Unknown Algorithm");
            }
        }
    }

    public static Graph graphType() {
        if (graphType.toLowerCase().equals("grid")) {
            return new Grid(numNodes, start, target);
        } else if (graphType.toLowerCase().equals("cyclic")) {
            return new Cyclic(numNodes, start, target);
        } else if (graphType.toLowerCase().equals("lollipop")) {
            return new lollipop(numNodes, numNodes_line, start, target);
        } else {
            return new Complete(numNodes, start, target);
        }
    }

    public static ArrayList<ArrayList<Node>> start_target_list(Graph superGraph) {
        ArrayList<Node> start_list = new ArrayList<Node>();
        ArrayList<Node> target_list = new ArrayList<Node>();

        // add a check for start == -1 and target == -2 and vice versa throw error Ille
        // random source target check
        if (start == -1 || target == -1) {
            for (int j = 0; j < num_rand_s_and_t; ++j) {

                while (start == target) {
                    if (start == -1)
                        start = rand.nextInt(numNodes);
                    if (target == -1)
                        target = rand.nextInt(numNodes);
                }

                start_list.add(superGraph.nodeList.get(start));
                target_list.add(superGraph.nodeList.get(target));
            }
        }

        else if (start == -2 || target == -2) {
            for (Node currentStart : superGraph.nodeList) {
                if (start == -2 || currentStart.key == start) {
                    for (Node currentTarget : superGraph.nodeList) {
                        if ((target == -2 || currentTarget.key == target)
                            && currentStart.key != currentTarget.key) {
                            start_list.add(currentStart);
                            target_list.add(currentTarget);
                        }
                    }
                }
            }
        } else {
            start_list.add(superGraph.nodeList.get(start));
            target_list.add(superGraph.nodeList.get(target));
        }

        ArrayList<ArrayList<Node>> start_target_list = new ArrayList<ArrayList<Node>>();
        start_target_list.add(start_list);
        start_target_list.add(target_list);

        return start_target_list;
    }

    public static ArrayList<ArrayList<Edge>> edgeRemovalPerInstance(Graph superGraph) {
        ArrayList<ArrayList<Edge>> instance_runs = new ArrayList<ArrayList<Edge>>();
        if (graphType.equals("cyclic")) {
            ArrayList<Edge> edgesToRemove = new ArrayList<Edge>();
            for (int i = 0; i < superGraph.edgeList.size(); ++i) {
                Edge temp = superGraph.edgeList.get(i);
                if (temp.toNode.key == start && temp.fromNode.key == target) {
                    edgesToRemove.add(superGraph.edgeList.get(i));
                    break;
                }
            }
            for (int x = 0; x < num_runs_per_instance; ++x)
                instance_runs.add(edgesToRemove);
            return instance_runs;
        }

        if (k_approach_chosen == 0) {
            for (int x = 0; x < num_runs_per_instance; ++x) {
                ArrayList<Edge> edgesToRemove = new ArrayList<Edge>(); // make this a set to avoid
                                                                       // removing the same edge,
                                                                       // loop until set is of size
                                                                       // k
                ArrayList<Edge> superEdgeList = null;

                if (graphType.equals("lollipop")) {
                    superEdgeList = new ArrayList<Edge>();
                    int cutoff = numNodes / 2;
                    for (int i = 0; i < superGraph.edgeList.size(); ++i) {
                        Edge temp = superGraph.edgeList.get(i);
                        if (temp.toNode.key < cutoff && temp.fromNode.key < cutoff)
                            superEdgeList.add(superGraph.edgeList.get(i));
                    }
                } else
                    superEdgeList = superGraph.edgeList;


                for (int f = 0; f < k; ++f) {
                    Edge origEdge = superEdgeList.get(rand.nextInt(superEdgeList.size())); // removes
                                                                                           // from
                                                                                           // superGraph.clone
                                                                                           // and
                                                                                           // adds
                                                                                           // reference
                                                                                           // to
                                                                                           // edges
                                                                                           // to
                                                                                           // remove

                    // if super edgelist contains the flipped edge, it may remove the same edge
                    // twice
                    // Make sure edges exist, and only increment f when an edge is successfully
                    // chose correctly
                    edgesToRemove.add(origEdge);
                }
                instance_runs.add(edgesToRemove);
            }
        } else if (k_approach_chosen == 1) { // chosen
            instance_runs.add(parseEdgeList(removed_k_edges, numNodes));
        } else if (k_approach_chosen == 2) { // all
            // bitshifting
        }
        return instance_runs;
    }
}
