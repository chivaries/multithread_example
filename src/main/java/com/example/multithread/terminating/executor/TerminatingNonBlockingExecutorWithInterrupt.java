package com.example.multithread.terminating.executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TerminatingNonBlockingExecutorWithInterrupt {
    public static void main(String[] args) throws InterruptedException {
        String currentThreadName = Thread.currentThread().getName();

        System.out.println("["+ currentThreadName + "]" + " thread starts here...");

        ExecutorService execService = Executors.newSingleThreadExecutor();

        LoopTask2 task1 = new LoopTask2();
        LoopTask2 task2 = new LoopTask2();

        Future<?> future1 = execService.submit(task1);
        Future<?> future2 = execService.submit(task2);

        execService.shutdown();

        TimeUnit.MILLISECONDS.sleep(200);
        future1.cancel(true);

        TimeUnit.MILLISECONDS.sleep(100);
        future2.cancel(true);

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
            doSomeWork();
            if(Thread.interrupted()){
                System.out.println("<" + currentThreadName + "," + taskId + "> got an interrupt! ..canceling...");
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