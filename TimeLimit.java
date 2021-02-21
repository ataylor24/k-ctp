public class TimeLimit implements ExceptionRunnable {
    private final ExceptionRunnable r;
    private final int limit;
    private Throwable t;

    public TimeLimit(final int limit, final ExceptionRunnable r) {
        this.limit = limit;
        this.r = r;
    }

    @SuppressWarnings("deprecation")
    public synchronized void run() throws Throwable {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    r.run();
                } catch (final Throwable t) {
                    TimeLimit.this.t = t;
                }
            }
        });
        thread.start();
        try {
            thread.join(limit);
            if (thread.isAlive()) {
                thread.stop();
                throw new InterruptedException("Timeout");
            }
        } catch (final InterruptedException e) {
            if (t == null) {
                t = e;
            }
        }
        if (t != null) {
            final Throwable tt = t;
            t = null;
            throw tt;
        }
    }
}

class Future<V> {
    private V value;

    public V getValue() {
        return value;
    }

    public void setValue(final V value) {
        this.value = value;
    }
}

interface ExceptionRunnable {
    public void run() throws Throwable;
}