package com.example.multithread.returning.executor;

import java.util.concurrent.*;

public class ReturningValuesUsingCompletionService2 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main thread starts here...");

        ExecutorService executorService = Executors.newCachedThreadPool();

        CompletionService<TaskResult<String, Integer>> tasks = new ExecutorCompletionService<>(executorService);

        tasks.submit(new MyCallableTaskWithTaskResult());
        tasks.submit(new MyCallableTaskWithTaskResult());
        tasks.submit(new MyRunnableTask(), new TaskResult<>("RunnableTask", 101));

        executorService.shutdown();

        for(int i = 0; i< 3; i++) {
            try {
                System.out.println(tasks.take().get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Main thread ends here...");
    }
}

class MyCallableTaskWithTaskResult implements Callable<TaskResult<String, Integer>> {
    private static int count = 0;
    private int id;
    private static String taskName = "CallableTaks";
    private int randomSum = 0;

    @Override
    public TaskResult<String, Integer> call() throws Exception {
        for(int i = 0; i < 5; i++) {
            System.out.println("<" + taskName +"-"+id + ">TICK TICK " + i);
            randomSum += Math.random()*1000;
            try {
                TimeUnit.MICROSECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new TaskResult<>(taskName + id, randomSum);
    }

    public MyCallableTaskWithTaskResult() {
        this.id = ++count;
    }
}
