package com.example.multithread.terminating.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TerminatingNormalNonBlockingThreadWithInterrupt {
    public static void main(String argc[]) throws InterruptedException {
        String currentThreadName = Thread.currentThread().getName();

        System.out.println("[" + currentThreadName + "]" + " thread starts here...");

        LoopTask2 task1 = new LoopTask2();
        LoopTask2 task2 = new LoopTask2();

        Thread thread1 = new Thread(task1, "Thread-1");
        Thread thread2 = new Thread(task2, "Thread-2");
        thread1.start();
        thread2.start();

        TimeUnit.MILLISECONDS.sleep(500);

        thread1.interrupt();
        thread2.interrupt();

        System.out.println("["+ currentThreadName + "]" + " thread ends here...");
    }
}

class LoopTask2 implements Runnable {
    private static int count = 0;
    private int id;
    private String taskId;
    private final int DATA_SIZE = 100000;

    @Override
    public void run() {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("#### <" + currentThreadName +"," + taskId + "> starting...####");
        while(true) {
            System.out.println("<" + currentThreadName + "," + taskId + "> TICK TICK");
            doSomeWork(); //Interrupt can not work with timer because the timer is a blocking function
            if(Thread.interrupted()){  //Thread.interrupted()
                break;
            }
        }
        System.out.println("#### <" + currentThreadName + "," + taskId + "> done...####");
    }

    public LoopTask2() {
        this.id = ++count;
        this.taskId = "Task-" + id;
    }

    private void doSomeWork(){
        for(int i = 0; i < 2 ; i++) {
            Collections.sort(generateDataSet());
        }
    }

    private List<Integer> generateDataSet(){
        List<Integer> intList = new ArrayList<>();
        Random randomGenerator = new Random();
        for(int i = 0; i < DATA_SIZE; i++) {
            intList.add(randomGenerator.nextInt(DATA_SIZE));
        }
        return intList;
    }
}
