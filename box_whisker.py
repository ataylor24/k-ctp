import os
import sys
import collections
from collections import defaultdict
import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd
import numpy as np
from matplotlib import rcParams
from sklearn.preprocessing import MinMaxScaler
from sklearn.preprocessing import StandardScaler

scaler = StandardScaler()#MinMaxScaler()

# figure size in inches
rcParams['figure.figsize'] = 14.5, 11.5#ORIG figure quality:11.7,8.27

sns.set()
sns.set_style("darkgrid")
sns.color_palette("cubehelix", 8)

k_data = defaultdict(lambda: set())
algos = defaultdict(lambda:defaultdict(lambda:defaultdict(lambda: defaultdict(lambda: []))))
graph_types = set()
val_number = defaultdict(lambda:defaultdict(lambda: defaultdict(lambda: 0)))
alg_denom = defaultdict(lambda:defaultdict(lambda: defaultdict(lambda: 0)))
runs_per_graph_size = defaultdict(lambda: 0)
dfs = defaultdict(lambda: pd.DataFrame())

num_bins = 50 #50 worked for cycle, 10 worked for others
def plot_agg(graph_type, size, source, k, alg):
    '''plt.figure()
    buckets = 30 #int(float(runs_per_graph_size[size])/10) if runs_per_graph_size[size] > 30 else runs_per_graph_size[size]
    fil_name = os.path.join(os.getcwd(), ("hist_type:%s_source:%s_size:%s_k:%s_alg:%sd.png" % (graph_type, source, size, k,alg)))
    n, bins, patches = plt.hist(algos[size][k][alg][source],buckets, alpha=0.5, histtype='bar', ec='black')
    plt.ylabel('Count')
    plt.xlabel('CR_AVG count')
    plt.savefig(fil_name)
    #plt.clf()'''
    
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
                        dfs[alg] = pd.DataFrame({source : pd.Series(algos[size][k][alg][source]) for source in algos[size][k][alg]}).sort_index(axis=1, level=None, ascending=True, inplace=False, kind='quicksort', na_position='last', sort_remaining=True)
                        #for 
                        #print(pd.DataFrame({source : pd.Series(algos[size][k][alg][source]) for source in algos[size][k][alg]}).sort_index(axis=1, level=None, ascending=True, inplace=False, kind='quicksort', na_position='last', sort_remaining=True))
                        #pd.set_option("display.max_rows", None, "display.max_columns", None)
                        #5with open ("column 24", 'w') as f: f.write(str(pd.DataFrame({source : pd.Series(algos[size][k][alg][source]) for source in algos[size][k][alg]}).sort_index(axis=1, level=None, ascending=True, inplace=False, kind='quicksort', na_position='last', sort_remaining=True)[24]))
                        #window = 5
                        for window in [1]:
                            for num_sources in range(0,15,window): #range(size-3,15,-window): #
                                for i in range(num_sources, num_sources + window, 1): #range(num_sources, num_sources - window, -1): #  
                                    try:
                                        #dfs[alg][~np.isnan(dfs[alg])][i].plot.kde(legend=True)
                                        print(dfs[alg][~np.isnan(dfs[alg])].to_numpy()[i])
                                        sns.boxplot(dfs[alg][~np.isnan(dfs[alg])].to_numpy()[i])
                                        #if alg == "BRS" and i != 24 and i != 20: sns.kdeplot(dfs[alg][~np.isnan(dfs[alg])][i], shade=True)
                                        
                                    except:
                                        print("exception")
                                        pass

                                #plt.ylabel('Density')
                                plt.xlabel('Scaled Hitting Time (# of steps taken)')
                                
                                #print('Density of hitting times from source(s) %s%s to target=31' % (num_sources, "..." + str(num_sources + window - 1) if window != 1 else ""))
                                plt.title('Density of hitting times from source(s) %s%s to target=31' % (num_sources, "..." + str(num_sources + window - 1) if window != 1 else ""))
                                #plt.title('Normalized Boxplot of hitting times from source(s) %s%s to target=31' % (num_sources - window + 1, "..." + str(num_sources) if window != 1 else ""))
                                plt.savefig("%s_hist_by_source(source(s)=%s%s).png" % (alg, num_sources, " to " + str(num_sources + window - 1) if window != 1 else ""))

                                #plt.savefig("%s_Stand_scaled_boxplot_by_source(source(s)=%s%s).png" % (alg, num_sources, " to " + str(num_sources - window + 1) if window != 1 else ""))
                                plt.clf()
                                
                                
def iterate(graph_type, data, fil_len):
    for i in range(fil_len):
        if i == 0:
            input_ = data[i+ 1].split(',')
            k_val = int(input_[0])
            k_data[graph_type].add(k_val)
            source = int(input_[6])
            graph_size = int(input_[3])
            runs_per_graph_size[graph_size] += 1
            
        if i == 5:
            raw_algos = data[i].strip().split(',')[1:-1]
        if i > 9:
            hitting_time_run_i = data[i].strip().split(',')[1:-1]
            for i in range(len(hitting_time_run_i)):
                algos[graph_size][k_val][raw_algos[i]][source].append(float(hitting_time_run_i[i]))
    return algos

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