import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;



public class Simulate {
    static String rand_k = "RAND_K";
    static String fixed_k = "FIXED_K";
    static String all_k = "ALL_K";
    static Random rand;
    static int count_failures = 0;

    static String removed_k_edges;

    static int num_of_rand_instances;
    static int k_approach_chosen;
    static ArrayList<Edge> edgeListRemoved = null;

    static int k;

    static String k_approach;

    static Long seed;

    static int numNodes;

    static String graphType;

    static int numNodes_line;

    static int start;

    static int target;

    static int num_rand_s_and_t;

    static String algorithms;
    static int num_runs_per_instance;
    static int num_rand_runs;
    static String human_readable_file;
    static MathContext mc = new MathContext(2, RoundingMode.HALF_UP);



    public static void main(String[] args) throws Throwable {
        String input = "";
        File csv = new File(args[0]);
        Scanner scnr;
        long startTime = System.currentTimeMillis();

        String output_filename = args[0].substring(0, args[0].length() - 4) + "_output.csv";
        human_readable_file = args[0].substring(0, args[0].length() - 4) + "_hr_output.csv";
        System.out.println(output_filename);

        PrintWriter pw = new PrintWriter(new File(output_filename));

        System.out.println("starting run...");
        int prog_check = 0;
        try {
            scnr = new Scanner(csv);
            while (scnr.hasNextLine()) {
                prog_check++;

                input = scnr.nextLine().trim();
                pw.write(readCSVLine(input, prog_check));
                pw.flush();
                long endTime_run = System.currentTimeMillis();

                System.out
                    .println("" + (endTime_run - startTime) / 1000.0D + " seconds to run input");
            }
        } catch (FileNotFoundException fileNotFoundException) {

            fileNotFoundException.printStackTrace();
        } finally {
            System.out.println(count_failures);
            pw.close();
        }
        long endTime = System.currentTimeMillis();

        System.out.println("" + (endTime - startTime) / 1000.0D + " seconds to finish");

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

        //Write_to_csv times_elapsed_csv = new Write_to_csv(new long[algorithms_for_instance.size()]);

        ArrayList<ArrayList<Node>> start_target_list = start_target_list(superGraph);

        ArrayList<Node> start_list = start_target_list.get(0);
        ArrayList<Node> target_list = start_target_list.get(1);

        for (Node startPoint : start_list) {
            for (Node targetPoint : target_list) {
                superGraph.start = startPoint;
                superGraph.target = targetPoint;


                ArrayList<ArrayList<Edge>> instance_runs = edgeRemovalPerInstance(superGraph);

                BigDecimal[] avg_hit_time_all = new BigDecimal[algorithms_for_instance.size()];
                for (int i = 0; i < avg_hit_time_all.length; i++) {
                    avg_hit_time_all[i] = BigDecimal.ZERO;
                }

                BigDecimal[] av_avCR = new BigDecimal[algorithms_for_instance.size()];

                for (int i = 0; i < avg_hit_time_all.length; i++) {
                    av_avCR[i] = BigDecimal.ZERO;
                }

                double sum_OPT = 0;
                double sum_BT = 0;
                BigDecimal[] avg_hit_time_inst = new BigDecimal[algorithms_for_instance.size()];
                ArrayList<ArrayList<BigDecimal[]>> arrayList5 =
                    new ArrayList<ArrayList<BigDecimal[]>>();

                
                for (ArrayList<Edge> edges_removed_from_sub : instance_runs) {

                    BigDecimal[] sums = new BigDecimal[algorithms_for_instance.size()];
                    for (int i = 0; i < avg_hit_time_all.length; i++) {
                        sums[i] = BigDecimal.ZERO;
                    }

                    ArrayList<BigDecimal[]> indiv_runs = new ArrayList<BigDecimal[]>();

                    for (int i = 0; i < algorithms_for_instance.size(); i++) {
                        indiv_runs.add(new BigDecimal[num_rand_runs]);
                    }


                    Graph subGraph = new Graph(superGraph, edges_removed_from_sub);

                    int opt = subGraph.calcDistance(subGraph.start, subGraph.target);

                    sum_OPT += opt;

                    BFS pathCheck = null;

                    if (!graphType.equals("complete") || k > numNodes - 2) {
                        pathCheck = new BFS(subGraph, subGraph.start, subGraph.target);
                    }
                    if (pathCheck != null && !pathCheck.pathExistence()) {
                        count_failures++;
                        System.out.println("does not pass path check");
                        continue;
                    }

                    Algorithm[][] algs = new Algorithm[num_rand_runs][];
                    for (int i = 0; i < num_rand_runs; i++) {
                        algs[i] = algorithmsForRun(algorithms_for_instance, superGraph, subGraph,
                            sums.length);
                    }



                    Backtrack backtrack = new Backtrack(superGraph, subGraph);

                    backtrack.run();
                    int bt_dist = backtrack.dist;
                    sum_BT += bt_dist;

                    System.out.println("BT " + bt_dist);
                    System.out.println("OPT " + opt);

                    for (int i = 0; i < num_rand_runs; i++) {
                        Thread[] threads = new Thread[(algs[i]).length];

                        for (int j = 0; j < (algs[i]).length; j++) {
                            threads[j] = new Thread(algs[i][j]);
                            threads[j].start();
                        }


                        for (int j = 0; j < threads.length; j++) {
                            threads[j].join();
                        }


                        for (int j = 0; j < (algs[i]).length; j++) {

                            BigDecimal bigDist = new BigDecimal((algs[i][j]).distance);

                            sums[j] = sums[j].add(bigDist);

                            indiv_runs.get(j)[i] = bigDist;
                        }
                    }

                    BigDecimal[] avCr =
                        new BigDecimal[algorithms_for_instance.size()];


                    for (int m = 0; m < sums.length; m++) {
                        avCr[m] = sums[m].divide(BigDecimal.valueOf(opt), mc)
                            .divide(BigDecimal.valueOf(num_rand_runs), mc);
                        av_avCR[m] = av_avCR[m].add(avCr[m]);
                        avg_hit_time_inst[m] = sums[m].divide(BigDecimal.valueOf(num_rand_runs));
                        avg_hit_time_all[m] = avg_hit_time_all[m]
                            .add(avg_hit_time_inst[m].divide(BigDecimal.valueOf(num_rand_runs)));
                    }


                    arrayList5.add(indiv_runs);
                }
                fileOut = updateCSVOutput1(fileOut, avg_hit_time_inst, avg_hit_time_all,
                    sum_OPT / num_rand_runs, sum_BT / num_rand_runs, algorithms_for_instance);


                fileOut.append("CR_AVG,");
                fileOut = updateCSVOutput2(fileOut, av_avCR, algorithms_for_instance, arrayList5);


                fileOut.append("\n");
            }
        }
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
                algorithm_memory.add(new Pair<Integer,Integer>(0, memSize));
            }
            if (algorithm.equals("BiasedRandWalkMemory")) {
                algorithm_memory.add(new Pair<Integer,Integer>(1, memSize));
            }
            if (algorithm.equals("BiasedRandWalkMemWeighting")) {
                algorithm_memory.add(new Pair<Integer,Integer>(2, memSize));
            }
            if (algorithm.equals("SimpleRandomWalk")) {
                algorithm_memory.add(new Pair<Integer,Integer>(3, memSize));
            }
            if (algorithm.equals("SimpleRandWalkMemory")) {
                algorithm_memory.add(new Pair<Integer,Integer>(4, memSize));
            }
            if (algorithm.equals("BiasedRandWalkSmart")) {
                algorithm_memory.add(new Pair<Integer,Integer>(5, memSize));
            }
            if (algorithm.equals("BiasedRandWalkMemorySmart")) {
                algorithm_memory.add(new Pair<Integer,Integer>(6, memSize));
            }
            if (algorithm.equals("BiasedRandWalkMemWeightingSmart")) {
                algorithm_memory.add(new Pair<Integer,Integer>(7, memSize));
            }
            if (algorithm.equals("SimpleRandomWalkSmart")) {
                algorithm_memory.add(new Pair<Integer,Integer>(8, memSize));
            }
            if (algorithm.equals("SimpleRandWalkMemorySmart")) {
                algorithm_memory.add(new Pair<Integer,Integer>(9, memSize));
            }
            if (algorithm.equals("BiasedRandWalkMemoryFlush")) {
                algorithm_memory.add(new Pair<Integer,Integer>(10, memSize));
            }
            if (algorithm.equals("BiasedRandWalkMemWeightingFlush")) {
                algorithm_memory.add(new Pair<Integer,Integer>(11, memSize));
            }
            if (algorithm.equals("SimpleRandWalkMemoryFlush")) {
                algorithm_memory.add(new Pair<Integer,Integer>(12, memSize));
            }
        }

        return algorithm_memory;
    }



    public static Algorithm[] algorithmsForRun(ArrayList<Pair<Integer, Integer>> paramArrayList,
        Graph paramGraph1, Graph paramGraph2, int paramInt) {
        Algorithm[] arrayOfAlgorithm = new Algorithm[paramInt];
        int b = 0;

        for (Pair<Integer, Integer> pair : paramArrayList) {

            switch (((Integer) pair.val1).intValue()) {

                case 0:
                    arrayOfAlgorithm[b] = new BiasedRandWalk(seed, paramGraph1, paramGraph2);
                    break;

                case 1:
                    arrayOfAlgorithm[b] = new BiasedRandWalkMemory(seed, paramGraph1, paramGraph2,
                        ((Integer) pair.val2).intValue());
                    break;

                case 2:
                    arrayOfAlgorithm[b] = new BiasedRandWalkMemWeighting(seed, paramGraph1,
                        paramGraph2, ((Integer) pair.val2).intValue());
                    break;


                case 3:
                    arrayOfAlgorithm[b] = new SimpleRandomWalk(seed, paramGraph1, paramGraph2);
                    break;

                case 4:
                    arrayOfAlgorithm[b] = new SimpleRandWalkMemory(seed, paramGraph1, paramGraph2,
                        ((Integer) pair.val2).intValue());
                    break;

                case 5:
                    arrayOfAlgorithm[b] = new BiasedRandWalkSmart(seed, paramGraph1, paramGraph2);
                    break;

                case 6:
                    arrayOfAlgorithm[b] = new BiasedRandWalkMemSmart(seed.longValue(), paramGraph1,
                        paramGraph2, ((Integer) pair.val2).intValue());
                    break;

                case 7:
                    arrayOfAlgorithm[b] = new BiasedRandWalkMemWeightSmart(seed.longValue(),
                        paramGraph1, paramGraph2, ((Integer) pair.val2).intValue());
                    break;


                case 8:
                    arrayOfAlgorithm[b] = new SimpleRandWalkSmart(seed, paramGraph1, paramGraph2);
                    break;

                case 9:
                    arrayOfAlgorithm[b] = new SimpleRandWalkMemSmart(seed, paramGraph1, paramGraph2,
                        ((Integer) pair.val2).intValue());
                    break;

                case 10:
                    arrayOfAlgorithm[b] = new BiasedRandWalkMemFlush(seed, paramGraph1, paramGraph2,
                        ((Integer) pair.val2).intValue());
                    break;

                case 11:
                    arrayOfAlgorithm[b] = new BiasedRandWalkMemWeightFlush(seed, paramGraph1,
                        paramGraph2, ((Integer) pair.val2).intValue());
                    break;

                case 12:
                    arrayOfAlgorithm[b] = new SimpleRandWalkMemFlush(seed, paramGraph1, paramGraph2,
                        ((Integer) pair.val2).intValue());
                    break;

                default:
                    throw new RuntimeException("Unknown Algorithm");
            }

            b++;
        }

        return arrayOfAlgorithm;
    }


    public static ArrayList<Edge> parseEdgeList(String paramString, int paramInt) {
        String[] arrayOfString = paramString.split("\\],\\[");

        ArrayList<Integer[]> arrayList = new ArrayList<Integer[]>();

        for (int b = 0; b < arrayOfString.length; b++) {
            if (b == 0) {
                arrayOfString[b] = arrayOfString[b].substring(2);
                String[] arrayOfString1 = arrayOfString[b].split(",");
                int i = Integer.parseInt(arrayOfString1[0]);
                int j = Integer.parseInt(arrayOfString1[1]);
                int k = Integer.parseInt(arrayOfString1[2]);

                arrayList.add(
                    new Integer[] {Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k)});
            } else if (b == arrayOfString.length - 1) {
                arrayOfString[b] = arrayOfString[b].substring(0, arrayOfString[b].length() - 2);
                String[] arrayOfString1 = arrayOfString[b].split(",");
                int i = Integer.parseInt(arrayOfString1[0]);
                int j = Integer.parseInt(arrayOfString1[1]);
                int k = Integer.parseInt(arrayOfString1[2]);

                arrayList.add(
                    new Integer[] {Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k)});
            } else {
                String[] arrayOfString1 = arrayOfString[b].split(",");
                int i = Integer.parseInt(arrayOfString1[0]);
                int j = Integer.parseInt(arrayOfString1[1]);
                int k = Integer.parseInt(arrayOfString1[2]);

                arrayList.add(
                    new Integer[] {Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k)});
            }
        }
        return createEdgeList(arrayList, paramInt);
    }

    public static ArrayList<Edge> createEdgeList(ArrayList<Integer[]> paramArrayList,
        int paramInt) {
        ArrayList<Edge> arrayList = new ArrayList<Edge> ();
        ArrayList<Node> arrayList1 = new ArrayList<Node>();
        for (int b = 0; b < paramInt; b++) {
            Node node = new Node(new ArrayList<>(), Integer.valueOf(b));
            arrayList1.add(node);
        }

        for (Integer[] arrayOfInteger : paramArrayList) {
            int i = arrayOfInteger[0].intValue();
            int j = arrayOfInteger[1].intValue();
            int k = arrayOfInteger[2].intValue();

            Node node1 = null;
            Node node2 = null;
            for (Node node : arrayList1) {
                if (i == node.key.intValue()) {
                    node1 = node;
                }
                if (j == node.key.intValue()) {
                    node2 = node;
                }
                if (node1 != null && node2 != null) {
                    Edge edge1 = new Edge(node2, node1, k);
                    Edge edge2 = new Edge(node1, node2, k);
                    node2.edgeList.add(edge1);
                    node1.edgeList.add(edge2);
                    arrayList.add(edge1);
                }
            }
        }

        return arrayList;
    }



    @Deprecated
    public static StringBuilder deprecated_updateCSVOutput1(StringBuilder paramStringBuilder,
        BigDecimal[] paramArrayOfBigDecimal1, BigDecimal[] paramArrayOfBigDecimal2,
        double paramDouble, int paramInt, ArrayList<Pair<Integer, Integer>> paramArrayList,
        ArrayList<BigDecimal[]> paramArrayList1) {
        int b = 0;
        for (Pair<Integer, Integer> pair : paramArrayList) {

            paramStringBuilder.append(retrieve_name(((Integer) pair.val1).intValue()));

            int b1 = b;

            paramStringBuilder
                .append("" + paramArrayOfBigDecimal1[b] + "," + paramArrayOfBigDecimal1[b] + ","
                    + paramArrayOfBigDecimal2[b++] + "," + paramDouble);


            if (b >= paramArrayList.size()) {
                paramStringBuilder.append("\n");
                for (int b2 = 0; b2 < ((BigDecimal[]) paramArrayList1.get(0)).length; b2++) {
                    for (int b3 = 0; b3 < paramArrayList.size(); b3++) {

                        paramStringBuilder.append("," + retrieve_name(
                            ((Integer) ((Pair) paramArrayList.get(b3)).val1).intValue()));
                        paramStringBuilder.append(((BigDecimal[]) paramArrayList1.get(b3))[b2]);
                        if (b3 == paramArrayList.size() - 1) {
                            paramStringBuilder.append("\n");
                        } else {
                            paramStringBuilder.append(",,,");
                        }
                    }
                }
                continue;
            }
            paramStringBuilder.append(",");
        }

        return paramStringBuilder;
    }



    @Deprecated
    public static StringBuilder deprecated_updateCSVOutput2(StringBuilder paramStringBuilder,
        BigDecimal[] paramArrayOfBigDecimal1, BigDecimal[] paramArrayOfBigDecimal2,
        ArrayList<Pair<Integer, Integer>> paramArrayList) {
        int b = 0;
        for (Pair<Integer, Integer> pair : paramArrayList) {

            paramStringBuilder.append(retrieve_name(((Integer) pair.val1).intValue()));
            if (paramArrayOfBigDecimal1[b].equals(BigDecimal.valueOf(0L))) {
                paramStringBuilder.append(" sum_s_and_t is 0,");
                if (b >= paramArrayList.size())
                    paramStringBuilder.append("\n");
            } else {
                paramStringBuilder.append(
                    paramArrayOfBigDecimal2[b].divide(BigDecimal.valueOf(num_runs_per_instance)));
                if (b >= paramArrayList.size()) {
                    paramStringBuilder.append("\n");
                } else {
                    paramStringBuilder.append(",,,");
                }
            }
            b++;
        }
        return paramStringBuilder;
    }
/*
 * fileOut = updateCSVOutput1(fileOut, avg_hit_time_inst, avg_hit_time_all,
                    sum_BT / num_rand_runs, sum_OPT / num_rand_runs, algorithms_for_instance);


                
                fileOut = updateCSVOutput2(fileOut, av_avCR, algorithms_for_instance, arrayList5);
 * 
 * */

    public static StringBuilder updateCSVOutput1(StringBuilder fileOut,
        BigDecimal[] avg_hit_time_inst, BigDecimal[] avg_hit_time_all,
        double avg_OPT, double avg_BT,
        ArrayList<Pair<Integer, Integer>> algorithms_for_instance) {
        fileOut.append("OPT," + avg_OPT + "\n");
        fileOut.append("BT," + avg_BT + "\n");
        fileOut.append(",");

        for (Pair<Integer, Integer> pair : algorithms_for_instance) {

            fileOut.append(retrieve_name(((Integer) pair.val1).intValue()));
        }
        fileOut.append("\n");
        fileOut.append("AVG_COST,");
        for (int b = 0; b < avg_hit_time_inst.length; b++) {
            fileOut.append("" + avg_hit_time_all[b] + ",");
        }
        fileOut.append("\n");
        return fileOut;
    }



    public static StringBuilder updateCSVOutput2(StringBuilder fileOut,
        BigDecimal[] av_avCR, ArrayList<Pair<Integer, Integer>> algorithms_for_instance,
        ArrayList<ArrayList<BigDecimal[]>> paramArrayList1) {
        int b1 = 0;
        for (Pair<Integer, Integer> pair : algorithms_for_instance) {
            fileOut.append(
                av_avCR[b1].divide(BigDecimal.valueOf(num_runs_per_instance)));
            if (b1 >= algorithms_for_instance.size()) {
                fileOut.append("\n");
            } else {
                fileOut.append(",");
            }
            b1++;
        }
        fileOut.append("\n");

        fileOut.append("INDIV RUN DATA\n");
        for (int b2 = 0; b2 < paramArrayList1.size(); b2++) {
            fileOut.append("Run " + b2 + ",");
            for (int b = 0; b < (paramArrayList1.get(b2)).size(); b++) {
                BigDecimal bigDecimal1 = BigDecimal.ZERO;
                BigDecimal bigDecimal2 = BigDecimal
                    .valueOf(((BigDecimal[]) (paramArrayList1.get(b2)).get(b)).length);
                for (int b3 = 0; b3 < ((BigDecimal[]) (paramArrayList1.get(b2))
                    .get(b)).length; b3++) {
                    bigDecimal1 = bigDecimal1
                        .add(((BigDecimal[]) (paramArrayList1.get(b2)).get(b))[b3]);
                }
                fileOut.append("" + bigDecimal1.divide(bigDecimal2) + ",");
            }
            fileOut.append("\n");
        }
        return fileOut;
    }

    public static String retrieve_name(int paramInt) {
        switch (paramInt) {

            case 0:
                return "BR,";

            case 1:
                return "BRM,";

            case 2:
                return "BRMW,";

            case 3:
                return "SR,";

            case 4:
                return "SRM,";

            case 5:
                return "BRS,";

            case 6:
                return "BRMS,";

            case 7:
                return "BRMWS,";

            case 8:
                return "SRS,";

            case 9:
                return "SRMS,";

            case 10:
                return "BRMF,";

            case 11:
                return "BRMWF,";

            case 12:
                return "SRMF,";
        }

        throw new RuntimeException("Unknown Algorithm");
    }



    public static Graph graphType() {
        if (graphType.toLowerCase().equals("grid"))
            return new Grid(numNodes, start, target);
        if (graphType.toLowerCase().equals("cyclic"))
            return new Cyclic(numNodes, start, target);
        if (graphType.toLowerCase().equals("lollipop")) {
            return new lollipop(numNodes, numNodes_line, start, target);
        }
        return new Complete(numNodes, start, target);
    }


    public static ArrayList<ArrayList<Node>> start_target_list(Graph paramGraph) {
        ArrayList<Node> arrayList1 = new ArrayList();
        ArrayList<Node> arrayList2 = new ArrayList();



        if (start == -1 || target == -1) {
            for (int b = 0; b < num_rand_s_and_t; b++) {
                while (start == target) {
                    if (start == -1)
                        start = rand.nextInt(numNodes);
                    if (target == -1) {
                        target = rand.nextInt(numNodes);
                    }
                }
                arrayList1.add(paramGraph.nodeList.get(start));
                arrayList2.add(paramGraph.nodeList.get(target));
            }

        } else if (start == -2 || target == -2) {
            for (Node node : paramGraph.nodeList) {
                if (start == -2 || node.key.intValue() == start) {
                    for (Node node1 : paramGraph.nodeList) {
                        if ((target == -2 || node1.key.intValue() == target)
                            && node.key != node1.key) {

                            arrayList1.add(node);
                            arrayList2.add(node1);
                        }
                    }
                }
            }
        } else {
            arrayList1.add(paramGraph.nodeList.get(start));
            arrayList2.add(paramGraph.nodeList.get(target));
        }

        ArrayList<ArrayList<Node>> arrayList = new ArrayList();
        arrayList.add(arrayList1);
        arrayList.add(arrayList2);

        return arrayList;
    }

    public static ArrayList<ArrayList<Edge>> edgeRemovalPerInstance(Graph paramGraph) {
        ArrayList<ArrayList<Edge>> arrayList = new ArrayList();
        if (graphType.equals("cyclic")) {
            ArrayList<Edge> arrayList1 = new ArrayList();
            int b;
            for (b = 0; b < paramGraph.edgeList.size(); b++) {
                Edge edge = paramGraph.edgeList.get(b);
                if (edge.toNode.key.intValue() == start && edge.fromNode.key.intValue() == target) {
                    arrayList1.add(paramGraph.edgeList.get(b));
                    break;
                }
            }
            for (b = 0; b < num_runs_per_instance; b++)
                arrayList.add(arrayList1);
            return arrayList;
        }

        if (k_approach_chosen == 0) {
            for (int b = 0; b < num_runs_per_instance; b++) {
                ArrayList<Edge> arrayList1 = new ArrayList();



                ArrayList<Edge> arrayList2 = null;

                if (graphType.equals("lollipop")) {
                    arrayList2 = new ArrayList();
                    int i = numNodes / 2;
                    for (int b2 = 0; b2 < paramGraph.edgeList.size(); b2++) {
                        Edge edge = paramGraph.edgeList.get(b2);
                        if (edge.toNode.key.intValue() < i && edge.fromNode.key.intValue() < i)
                            arrayList2.add(paramGraph.edgeList.get(b2));
                    }
                } else {
                    arrayList2 = paramGraph.edgeList;
                }

                for (int b1 = 0; b1 < k; b1++) {
                    Edge edge = arrayList2.get(rand.nextInt(arrayList2.size()));



                    arrayList1.add(edge);
                }
                arrayList.add(arrayList1);
            }
        } else if (k_approach_chosen == 1) {
            arrayList.add(parseEdgeList(removed_k_edges, numNodes));
        } else if (k_approach_chosen == 2) {

        }
        return arrayList;
    }
}


/*
 * Location: /Users/alextaylor/Desktop/sim_class_v15/!/Simulate.class Java compiler version: 9
 * (53.0) JD-Core Version: 1.1.3
 */
