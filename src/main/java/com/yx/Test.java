package com.yx;

public class Test {

    public static void main(String[] args) {
        ArrayBlockingQueue <Integer>queue = new ArrayBlockingQueue<>(8);
        //启动一个生产者线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true) {
                    //在一个循环中一直向队列中放入数据
                    queue.put(i++);
                }
            }
        },"thread-produce").start();

        //启动一个消费者线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //在一个循环中不断地从队列中取出数据
                    queue.take();
                }
            }
        },"thread-cosumer").start();
    }
}
