package com.example.multithread.implement.runnable;

import java.util.concurrent.TimeUnit;

public class RunnableAnomymous {
    public static void main(String[] args) {
        System.out.println("Main thread starts here...");

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 5; i++) {
                    System.out.println("<" + 5 + ">TICK TICK " + i);
                    try {
                        TimeUnit.MICROSECONDS.sleep((long)Math.random()*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        System.out.println("Main thread ends here...");
    }
}
