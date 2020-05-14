package com.example.multithread.returning.executor;

import java.util.concurrent.*;

public class ReturningValuesUsingCallable {
    public static void main(String[] args) throws InterruptedException{
        System.out.println("Main thread starts here...");

        ExecutorService executorService = Executors.newCachedThreadPool();

        // Callable
        Future<Integer> result1 = executorService.submit(new MyCallableTask());
        Future<Integer> result2 = executorService.submit(new MyCallableTask());

        //Runnable
        Future<?> result3 = executorService.submit(new MyRunnableTask());
        Future<?> result4 = executorService.submit(new MyRunnableTask(), 110.1);

        executorService.shutdown();

        try {
            System.out.println("result1:" + result1.get());
            System.out.println("result2:" + result2.get());
            System.out.println("result3:" + result3.get());
            System.out.println("result4:" + result4.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread ends here...");
    }
}

class MyCallableTask implements Callable<Integer> {
    private static int count = 0;
    private int id;
    private static String taskName = "CallableTaks";
    private int randomSum = 0;

    @Override
    public Integer call() throws Exception {
        for(int i = 0; i < 5; i++) {
            System.out.println("<" + taskName +"-"+id + ">TICK TICK " + i);
            randomSum += Math.random()*1000;
            try {
                TimeUnit.MICROSECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return randomSum;
    }

    public MyCallableTask() {
        this.id = ++count;
    }
}

class MyRunnableTask implements Runnable {
    private static int count = 0;
    private int id;
    private static String taskName = "RunnableTaks";

    @Override
    public void run() {
        for(int i = 0; i < 5; i++) {
            System.out.println("<" + taskName +"-"+id + ">TICK TICK " + i);
            try {
                TimeUnit.MICROSECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public MyRunnableTask() {
        this.id = ++count;
    }
}
