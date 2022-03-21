import os
import sys
import collections
from collections import defaultdict
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np

k_data = defaultdict(lambda: set())
algos = defaultdict(lambda:defaultdict(lambda: defaultdict(lambda: [])))
graph_types = set()
val_number = defaultdict(lambda:defaultdict(lambda: defaultdict(lambda: 0)))
alg_denom = defaultdict(lambda:defaultdict(lambda: defaultdict(lambda: 0)))
runs_per_graph_size = defaultdict(lambda: 0)

def graph(graph_type):
    for size in algos.keys():
        for k in algos[size]:
            df = pd.DataFrame(algos[size][k])
            #print(pd.DataFrame(algos[size][k]))
            for alg in algos[size][k]:
                fil_name = os.path.join(os.getcwd(), ("kde_%s_%s_%s_%s.png" % (graph_type, size, k, alg)))
                sns.kdeplot(df[alg])
                '''x_arr = np.array(algos[size][k][alg])
                sns.kdeplot(x_arr)'''
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
        if i == 7:
            cr = data[i].strip().split(',')[1:-1]
            for i in range(len(cr)):
                algos[graph_size][k_val][raw_algos[i]].append(float(cr[i]))
    return algos

def read_file(fil):
    with open (fil, 'r') as fil:
        data = fil.readlines()
        return data

def process_file(graph_type, fil):
    data = read_file(fil)
    return iterate(graph_type,data,len(data))

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