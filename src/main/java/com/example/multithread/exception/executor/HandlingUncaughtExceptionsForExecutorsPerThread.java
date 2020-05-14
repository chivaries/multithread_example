package com.example.multithread.exception.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class HandlingUncaughtExceptionsForExecutorsPerThread {
    public static void main(String argc[]) {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("["+ currentThreadName + "]" + " thread starts here...");

        //Thread.setDefaultUncaughtExceptionHandler(new ThreadExceptionHandler("Default Handler"));

        //ExecutorService execService1 = Executors.newCachedThreadPool();
        ExecutorService execService1 = Executors.newCachedThreadPool(new ThreadFactoryWithExceptionHandler());
        execService1.execute(new ExceptionLeakingTask());
        execService1.execute(new ExceptionLeakingTask());

        //ExecutorService execService2 = Executors.newCachedThreadPool();
        ExecutorService execService2 = Executors.newCachedThreadPool(new ThreadFactoryWithExceptionHandler());
        execService2.execute(new ExceptionLeakingTask());
        execService2.execute(new ExceptionLeakingTask());


        execService1.shutdown();
        execService2.shutdown();

        System.out.println("["+ currentThreadName + "]" + " thread ends here...");
    }
}

class NamedThreadsFactory implements ThreadFactory {
    private static int count = 0;
    private static String Name = "MyThread-";

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, Name + ++count);
    }
}

class ThreadFactoryWithExceptionHandler extends NamedThreadsFactory {
    public Thread newThread(Runnable r) {
        Thread thread = super.newThread(r);
        thread.setUncaughtExceptionHandler(new ThreadExceptionHandler("This"));
        return thread;
    }
}
