package com.yx;

import java.util.concurrent.locks.LockSupport;

/**
 * 以睡眠的方式阻塞策略实现
 */
public final class SleepingWaitStrategy implements WaitStrategy{

    //默认的自旋次数，自旋就是空转
    private static final int DEFAULT_RETRIES = 200;

    // 自旋切换CPU次数
    private static final int DEFAULT_YIELD = 100;

    //默认的睡眠时间
    private static final long DEFAULT_SLEEP = 100;

    //自旋次数
    private  int retries;
    //睡眠时间
    private final long sleepTimeNs;

    public void setRetries(int retries) {
        this.retries = retries;
    }

    // 无参构造方法
    public SleepingWaitStrategy() {
        this(DEFAULT_RETRIES,DEFAULT_SLEEP);
    }

    public SleepingWaitStrategy(int retries, long sleepTimeNs) {
        this.retries = retries;
        this.sleepTimeNs = sleepTimeNs;
    }

    @Override
    public void waitFor(){
        // 如果自旋次数目前仍然在100以上，则直接减一，不需要处理，仍然需要自旋
        if(retries > DEFAULT_YIELD){
            --retries;
        }
        // 此时100次，仍然需要自旋，但是需要让出cpu
        else if(retries > 0){
            --retries;
            Thread.yield();
        }else {
            //如果队列已满或者队列已空，就让生产者或消费者线程自己睡100纳秒
            LockSupport.parkNanos(sleepTimeNs);
        }
    }
}
