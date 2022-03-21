import os
import sys
import collections
from collections import defaultdict
import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd

k_data = defaultdict(lambda: set())
algos = defaultdict(lambda:defaultdict(lambda: defaultdict(lambda: [])))
graph_types = set()
val_number = defaultdict(lambda:defaultdict(lambda: defaultdict(lambda: 0)))
alg_denom = defaultdict(lambda:defaultdict(lambda: defaultdict(lambda: 0)))
runs_per_graph_size = defaultdict(lambda: 0)

def lollipop_dropping_outliers(graph_type):
    df = pd.DataFrame()
    for size in algos.keys():
        for k in algos[size]:
            df[int(size)]=pd.DataFrame(algos[size][k]).mean(axis=0)
            

            '''for alg in algos[size][k]:
                fil_name = os.path.join(os.getcwd(), ("line_%s_%s_%s_%s.png" % (graph_type, size, k,alg)))
                sns.lineplot(size,df[alg],label = alg, ax=ax)
                plt.ylabel('competitive ratio')
                plt.xlabel('Graph Size (Nodes)')
                plt.title('Algorithmic Performance on Grid Graphs of increasing size') 
                plt.savefig(fil_name)
                plt.clf()'''
    print(df.T.sort_index(axis=0, level=None, ascending=True, inplace=False, kind='quicksort', na_position='last', sort_remaining=True))
    fil_name = os.path.join(os.getcwd(), ("line_%s_%s_%s_drop_sr_srs.png" % (graph_type, size, k)))
    #sns.lineplot(data=df, dashes=False)
    df.T.sort_index(axis=0, level=None, ascending=True, inplace=False, kind='quicksort', na_position='last', sort_remaining=True).drop("SRS", axis= 1).drop("SR", axis=1).plot()
    plt.ylabel('competitive ratio')
    plt.xlabel('Graph Size (Nodes)')
    plt.title('Algorithmic Performance on Graphs of increasing size without SR and SRS') 
    plt.savefig(fil_name)

def cyclic_dropping_outliers(graph_type):
    df = pd.DataFrame()
    for size in algos.keys():
        for k in algos[size]:
            #df[int(size)]=pd.DataFrame(algos[size][k]).mean(axis=0)
            df[int(size)]=pd.DataFrame({algo : pd.Series(algos[size][k][algo]) for algo in algos[size][k]}).mean(axis=0)

            '''for alg in algos[size][k]:
                fil_name = os.path.join(os.getcwd(), ("line_%s_%s_%s_%s.png" % (graph_type, size, k,alg)))
                sns.lineplot(size,df[alg],label = alg, ax=ax)
                plt.ylabel('competitive ratio')
                plt.xlabel('Graph Size (Nodes)')
                plt.title('Algorithmic Performance on Grid Graphs of increasing size') 
                plt.savefig(fil_name)
                plt.clf()'''
    print(df.T.sort_index(axis=0, level=None, ascending=True, inplace=False, kind='quicksort', na_position='last', sort_remaining=True).drop("BRS", axis= 1))
    fil_name = os.path.join(os.getcwd(), ("line_%s_%s_%s_drop_br_brs.png" % (graph_type, size, k)))
    #sns.lineplot(data=df, dashes=False)
    df.T.sort_index(axis=0, level=None, ascending=True, inplace=False, kind='quicksort', na_position='last', sort_remaining=True).drop("BRS", axis= 1).drop("BR", axis=1).plot()
    plt.ylabel('competitive ratio')
    plt.xlabel('Graph Size (Nodes)')
    plt.title('Algorithmic Performance on Graphs of increasing size without BR and BRS') 
    plt.savefig(fil_name)

def graph(graph_type):
    df = pd.DataFrame()
    for size in algos.keys():
        #print(algos[size])
        for k in algos[size]: 
            #print()
            #print()
            #print(algos[size][k])
           
            #df[int(size)]=pd.DataFrame(algos[size][k]).mean(axis=0)
            df[int(size)]=pd.DataFrame({algo : pd.Series(algos[size][k][algo]) for algo in algos[size][k]}).mean(axis=0)
            
            '''for alg in algos[size][k]:
                fil_name = os.path.join(os.getcwd(), ("line_%s_%s_%s_%s.png" % (graph_type, size, k,alg)))
                sns.lineplot(size,df[alg],label = alg, ax=ax)
                plt.ylabel('competitive ratio')
                plt.xlabel('Graph Size (Nodes)')
                plt.title('Algorithmic Performance on Grid Graphs of increasing size') 
                plt.savefig(fil_name)
                plt.clf()'''
    print(df.T.sort_index(axis=0, level=None, ascending=True, inplace=False, kind='quicksort', na_position='last', sort_remaining=True))
    fil_name = os.path.join(os.getcwd(), ("line_%s_%s_%s.png" % (graph_type, size, k)))
    #sns.lineplot(data=df, dashes=False)
    df.T.sort_index(axis=0, level=None, ascending=True, inplace=False, kind='quicksort', na_position='last', sort_remaining=True).plot() #CYCLIC: .drop("BRS", axis= 1).drop("BR", axis=1).plot() LOLLIPOP: .drop("SRS", axis= 1).drop("SR", axis=1).plot()
    plt.ylabel('competitive ratio')
    plt.xlabel('Graph Size (Nodes)')
    plt.title('Algorithmic Performance on Graphs of increasing size') 
    plt.savefig(fil_name)

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
        if "_output.csv" in fil:
            #print(fil)
            count += 1
            graph_type = fil.split('_')[0]
            graph_types.add(graph_type)
            process_file(graph_type,fil)
    for graph_type in graph_types:
        graph(graph_type)
        if graph_type == "cyclic":
            cyclic_dropping_outliers(graph_type)
        elif graph_type == "lollipop":
            lollipop_dropping_outliers(graph_type)
            
                
if __name__ == "__main__":
    ddir = sys.argv[1]
    main(ddir)