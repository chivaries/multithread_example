package com.example.multithread.implement.runnable;

import java.util.concurrent.TimeUnit;

public class RunnableStartAfterCreate {
    public static void main(String argc[]) throws InterruptedException {
        System.out.println("Main thread starts here...");

        new Thread(new MyThreadTaskRunnable2()).start();
        Thread t = new Thread(new MyThreadTaskRunnable2());
        t.start();

        System.out.println("Main thread ends here...");
    }
}


class MyThreadTaskRunnable2 implements Runnable {
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

    public MyThreadTaskRunnable2() {
        this.id = ++count;
        //new Thread(this).start();
    }
}
