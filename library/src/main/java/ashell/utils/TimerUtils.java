package ashell.utils;

/**
 * Created by Manne Öhlund on 09/04/17.
 * Copyright © 2017 All rights reserved.
 */

public class TimerUtils {
    long start;
    long end;
    long threshold;

    public TimerUtils() {}

    public TimerUtils(long threshold) {
        this.threshold = threshold;
    }

    public void start() {
        start = System.currentTimeMillis();
    }

    public void stop() {
        end = System.currentTimeMillis();
    }

    public long diff() {
        end = System.currentTimeMillis();
        return end - start;
    }

    public boolean hasReachedThreshold() {
        return diff() > threshold;
    }

    public void reset() {
        end = System.currentTimeMillis();
        start = System.currentTimeMillis();
    }

    public long getTime() {
        return end - start;
    }
}
