package com.example.multithread.implement.thread;

import java.util.concurrent.TimeUnit;

public class ExtendThreadCreateAndStart {
    public static void main(String[] args) {
        System.out.println("Main thread start here...");

        new MyThreadTask();
        Thread t = new MyThreadTask();

        System.out.println("Main thread ends here...");
    }
}


class MyThreadTask extends Thread {
    private static int count = 0;
    private int id;

    @Override
    public void run() {
        for(int i = 0; i < 5; i++) {
            System.out.println("<" + id + ">TICK TICK " + i);
            try {
                TimeUnit.MICROSECONDS.sleep((long)Math.random()*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public MyThreadTask() {
        this.id = ++count;
        this.start();
    }
}