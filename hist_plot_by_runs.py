import os
import sys
import collections
from collections import defaultdict
import matplotlib.pyplot as plt
import seaborn as sns

k_data = defaultdict(lambda: set())
algos = defaultdict(lambda:defaultdict(lambda: defaultdict(lambda: [])))
graph_types = set()
val_number = defaultdict(lambda:defaultdict(lambda: defaultdict(lambda: 0)))
alg_denom = defaultdict(lambda:defaultdict(lambda: defaultdict(lambda: 0)))
runs_per_graph_size = defaultdict(lambda: 0)

#num_bins = 300 #50 worked for cycle, 10 worked for others

def graph(graph_type):
    '''for size in algos.keys():
        print(size) 
        for k in algos[size]:
            print(k)   
            for alg in algos[size][k]:
                print(alg)
                print(int(float(runs_per_graph_size[size])))'''
    for size in algos.keys():
        for k in algos[size]:
            for alg in algos[size][k]:
                plt.figure()
                buckets = 100 #int(float(runs_per_graph_size[size])/10) if runs_per_graph_size[size] > 30 else runs_per_graph_size[size]
                fil_name = os.path.join(os.getcwd(), ("hist_%s_%s_%s_%s_by_runs.png" % (graph_type, size, k,alg)))
                n, bins, patches = plt.hist(algos[size][k][alg],buckets, alpha=0.5, histtype='bar', ec='black')
                plt.ylabel('Count of runs with a given Hitting Time')
                plt.xlabel('Hitting time (Number of Steps)')
                plt.savefig(fil_name)
                plt.clf()

def iterate(graph_type, data, fil_len):
    for i in range(fil_len):
        if i == 0:
            input_ = data[i+ 1].split(',')
            k_val = int(input_[0])
            k_data[graph_type].add(k_val)
            graph_size = int(input_[3])
            runs_per_graph_size[graph_size] += 1
            
        if i == 5:
            raw_algos = data[i].strip().split(',')[1:-1]
        if i > 9:
            hitting_time_run_i = data[i].strip().split(',')[1:-1]
            for i in range(len(hitting_time_run_i)):
                algos[graph_size][k_val][raw_algos[i]].append(float(hitting_time_run_i[i]))

def read_file(fil):
    with open (fil, 'r') as fil:
        data = fil.readlines()
        return data

def process_file(graph_type, fil):
    data = read_file(fil)
    iterate(graph_type,data,len(data))

def move_dir(ddir):
    os.chdir(ddir)

def main(ddir):
    count =0
    move_dir(ddir)
    for fil in os.listdir(os.getcwd()):
        if "_output.csv" not in fil: continue
        count += 1
        graph_type = fil.split('_')[0]
        graph_types.add(graph_type)
        process_file(graph_type,fil)
    for graph_type in graph_types:
        graph(graph_type)
            
                
if __name__ == "__main__":
    ddir = sys.argv[1]
    main(ddir)