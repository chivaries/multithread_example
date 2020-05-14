package com.example.multithread.terminating.executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TerminatingAllExecutors {
    public static void main(String argc[]) throws InterruptedException {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("[" + currentThreadName + "]" + " thread starts here...");

        ExecutorService execService = Executors.newCachedThreadPool();

        BlockingTask blockingTask = new BlockingTask();
        NonBlockingTask nonBlockingTask = new NonBlockingTask();
        CallableTask callableTask = new CallableTask();

        //Runnable
        execService.execute(blockingTask);
        execService.execute(nonBlockingTask);
        //Callable
        execService.submit(callableTask);

        TimeUnit.MILLISECONDS.sleep(1000);

        execService.shutdownNow(); //shutDownNow
        System.out.println("["+ currentThreadName + "]" + " shutdownNow() invoked ");

        System.out.println("["+ currentThreadName + "]" + " All threads terminated: " +
                execService.awaitTermination(500, TimeUnit.MILLISECONDS)); //awaitTermination

        System.out.println("["+ currentThreadName + "]" + " thread ends here...");
    }
}

class BlockingTask implements Runnable {
    private static int count = 0;
    private int id;
    private String taskId;

    public void run() {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("#### <" + currentThreadName + "," + taskId + "> starting...####");
        while (true) {
            System.out.println("<" + currentThreadName + "," + taskId + "> TICK TICK");
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("<" + currentThreadName + "," + taskId + "> Sleep Interrupted. Cancelling...");
                break;
            }
        }
        System.out.println("#### <" + currentThreadName + "," + taskId + "> done...####");
    }

    public BlockingTask() {
        this.id = ++count;
        this.taskId = "Task-" + id;
    }
}

class NonBlockingTask implements Runnable {
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
            doSomeWork();
            if(Thread.interrupted()){
                System.out.println("<" + currentThreadName + "," + taskId + "> Thread.interrupted() is true: Cancelling...");
                break;
            }
        }
        System.out.println("#### <" + currentThreadName + "," + taskId + "> done...####");
    }

    public NonBlockingTask() {
        this.id = ++count;
        this.taskId = "Task-" + id;
    }
    private void doSomeWork(){
        for(int i = 0; i<2 ; i++) {
            Collections.sort(generateDataSet());
        }
    }
    private List<Integer> generateDataSet(){
        List<Integer> intList = new ArrayList<>();
        Random randomGenerator = new Random();
        for(int i = 0; i<DATA_SIZE; i++) {
            intList.add(randomGenerator.nextInt(DATA_SIZE));
        }
        return intList;
    }
}

class CallableTask implements Callable<Integer> {
    private static int count = 0;
    private int id;
    private String taskId;
    private int randomSum = 0;

    @Override
    public Integer call() throws Exception {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("#### <" + currentThreadName +"," + taskId + "> starting...####");
        while(true) {
            System.out.println("<" + currentThreadName + "," + taskId + "> TICK TICK");
            randomSum += Math.random()*1000;
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("<" + currentThreadName + "," + taskId + "> Sleep Interrupted. Cancelling...");
                break;
            }
        }
        System.out.println("#### <" + currentThreadName + "," + taskId + "> done...####");
        return randomSum;
    }

    public CallableTask() {
        this.id = ++count;
        this.taskId = "Task-" + id;
    }
}
