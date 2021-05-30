public class Pair<S extends Comparable<S>, T extends Comparable<T>> implements Comparable<Pair<S,T>>{
    public S val1; 
    public T val2;

    public Pair(S val1, T val2) { 
        this.val1 = val1;
        this.val2 = val2;
    }

    @Override
    public int compareTo(Pair<S, T> pair) {
        if (val1.equals(pair.val1) && val2.equals(pair.val2)) return 0;
        
        if (Math.abs(val1.compareTo(pair.val1)) - Math.abs(val2.compareTo(pair.val2)) > 0) 
            return (Math.abs(val1.compareTo(pair.val1)) - Math.abs(val2.compareTo(pair.val2))) * (val1.compareTo(pair.val1)/Math.abs(val1.compareTo(pair.val1)));
        else if (Math.abs(val1.compareTo(pair.val1)) - Math.abs(val2.compareTo(pair.val2)) < 0) {
            return (Math.abs(val2.compareTo(pair.val2)) - Math.abs(val1.compareTo(pair.val1))) * (val2.compareTo(pair.val2)/Math.abs(val2.compareTo(pair.val2)));
        }
        
        return -1;
    }
}