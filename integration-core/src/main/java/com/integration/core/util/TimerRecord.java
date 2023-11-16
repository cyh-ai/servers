package com.integration.core.util;

/**
 * @author cyh
 * 待补充
 */
public class TimerRecord {

    private long start = 0;

    public static TimerRecord start(){
        TimerRecord timerRecord = new TimerRecord();
        timerRecord.start = System.currentTimeMillis();
        return timerRecord;
    }

    public void reset(){
        start = System.currentTimeMillis();
    }

    public long getCost(){
        return System.currentTimeMillis()-start;
    }

    public long getCostAndReset(){
        long cost = System.currentTimeMillis() - start;
        start = System.currentTimeMillis();
        return cost;
    }
}
