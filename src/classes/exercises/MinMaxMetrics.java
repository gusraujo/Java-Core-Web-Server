package classes.exercises;


import java.util.ArrayList;
import java.util.List;

public class MinMaxMetrics {

    // Add all necessary member variables
    List<Long> samples = new ArrayList<>();
    Object lock = new Object();

    /**
     * Initializes all member variables
     */
    public MinMaxMetrics() {
        // Add code here
    }

    /**
     * Adds a new sample to our metrics.
     */
    public void addSample(long newSample) {
        // Add code here
        samples.add(newSample);
    }

    /**
     * Returns the smallest sample we've seen so far.
     */
    public long getMin() {
        // Add code here
        synchronized (this.lock) {
            long min = Long.MAX_VALUE;
            for (long sample : samples) {
                if (sample < min) {
                    min = sample;
                }
            }
            return min;
        }
    }

    /**
     * Returns the biggest sample we've seen so far.
     */
    public long getMax() {
        // Add code here
        synchronized (this.lock) {
            long max = Long.MIN_VALUE;
            for (long sample : samples) {
                if (sample > max) {
                    max = sample;
                }
            }
            return max;
        }
    }
}


/* RESPOSTA :

public class MinMaxMetrics {

    private volatile long minValue;
    private volatile long maxValue;

public MinMaxMetrics() {
    this.maxValue = Long.MIN_VALUE;
    this.minValue = Long.MAX_VALUE;
}


    public void addSample(long newSample) {
        synchronized (this) {
            this.minValue = Math.min(newSample, this.minValue);
            this.maxValue = Math.max(newSample, this.maxValue);
        }
    }


    public long getMin() {
        return this.minValue;
    }

    public long getMax() {
        return this.maxValue;
    }
}
 */

