package com.example.multithread.scheduling.executor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SchedulingTasksFixedDelayRepeatedExecution {
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

    public static void main(String argc[]) throws InterruptedException {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("Main thread starts here...");

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(new NamedThreadsFactory());

        System.out.println("["+currentThreadName+"] Current time " + dateFormatter.format(new Date()));

        //ScheduledFuture<?> scheduledFuture = executorService.scheduleWithFixedDelay(new ScheduledRunnableTask(0), 4,2, TimeUnit.SECONDS);

        ScheduledFuture<?> schedFuture = executorService.scheduleAtFixedRate(new ScheduledRunnableTask(0), 4, 2, TimeUnit.SECONDS);

        TimeUnit.MILLISECONDS.sleep(10000);
        executorService.shutdown();

        System.out.println("Main thread ends here...");
    }
}
