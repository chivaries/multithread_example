package com.example.multithread.returning.executor;

import java.util.concurrent.*;

public class ReturningValuesUsingCompletionService1 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main thread starts here...");

        ExecutorService executorService = Executors.newCachedThreadPool();

        CompletionService<Integer> tasks = new ExecutorCompletionService<>(executorService);

        //Callable
        tasks.submit(new MyCallableTask());
        tasks.submit(new MyCallableTask());

        //Runnable
        tasks.submit(new MyRunnableTask(), 110);

        executorService.shutdown();

        for(int i = 0; i< 3; i++) {
            try {
                System.out.println("Result: " + tasks.take().get()); //block
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }


        System.out.println("Main thread ends here...");
    }
}
