public class Tuple<S extends Comparable, T> implements Comparable<Tuple<S,T>> {
    public S val1;
    public T val2;

    public Tuple(S val1, T val2) { 
        this.val1 = val1;
        this.val2 = val2;
    }
    @Override
    public int compareTo(Tuple<S,T> tup) {
        return val1.compareTo(tup.val1);
    }
}