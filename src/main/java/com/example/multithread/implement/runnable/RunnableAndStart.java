package com.example.multithread.implement.runnable;

import java.util.concurrent.TimeUnit;

public class RunnableAndStart {
    public static void main(String argc[]) throws InterruptedException {
        System.out.println("Main thread starts here...");

        new MyThreadTaskRunnable();
        //Thread t = new MyThreadTaskRunnable(); 	//Thread 타입으로 받을 수 없음
        new MyThreadTaskRunnable();

        System.out.println("Main thread ends here...");
    }
}

class MyThreadTaskRunnable implements Runnable {
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

    public MyThreadTaskRunnable() {
        this.id = ++count;
        new Thread(this).start();
    }
}
