package com.yx;

/**
 * 阻塞队列接口
 *
 * @param <E>
 */
public class ArrayBlockingQueue<E> {

    //存放数据的数组
    final Object[] items;

    //队列中存放数据的个数
    int count;

    // 写指针
    int putIndex;

    // 读指针
    int takeIndex;

    SleepingWaitStrategy waitStrategy = new SleepingWaitStrategy();

    // 队列的构造方法
    public ArrayBlockingQueue(int capacity) {
        this.items = new Object[capacity];
    }

    //存放数据到队列的方法，不限时阻塞，直到能够放入数据
    public void put(E e) {
        while (count == items.length) {
            // 如果队列数据个数和长度一样，此时阻塞，因为阻塞唤醒存在虚假唤醒，所以这里用while todo
            System.out.println(Thread.currentThread().getName() + "线程写数据发现没位置，发生阻塞");
            waitStrategy.waitFor();
        }
        waitStrategy.setRetries(200);
        System.out.println(Thread.currentThread().getName() + "线程操作队列有位置了，生产者线程被唤醒，继续执行把数据写入的操作,数据为" + e);
        items[putIndex] = e;
        if (++ putIndex == items.length) {
            putIndex = 0;
        }
        count ++;
    }


    //取出数据的方法，不限时阻塞
    public E take(){
        while (count == 0){
            // 如果此时队列里面没有数据，就发生阻塞 todo
            System.out.println(Thread.currentThread().getName() + "线程取数据发现没数据，发生阻塞");
            waitStrategy.waitFor();
        }
        waitStrategy.setRetries(200);
        E item = (E)items[takeIndex];
        System.out.println(Thread.currentThread().getName() + "线程操作队列有数据了，消费者线程被唤醒，继续执行把数据取走的操作,数据为" + item);
        items[takeIndex] = null;
        if(++ takeIndex == items.length){
            takeIndex = 0;
        }
        count --;
        return item;
    }
}
