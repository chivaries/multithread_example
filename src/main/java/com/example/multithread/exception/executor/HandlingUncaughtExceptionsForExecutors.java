package com.example.multithread.exception.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HandlingUncaughtExceptionsForExecutors {
    public static void main(String argc[]) {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("["+ currentThreadName + "]" + " thread starts here...");

        Thread.setDefaultUncaughtExceptionHandler(new ThreadExceptionHandler("Default Handler"));

        ExecutorService execService1 = Executors.newCachedThreadPool();
        execService1.execute(new ExceptionLeakingTask());
        execService1.execute(new ExceptionLeakingTask());

        ExecutorService execService2 = Executors.newCachedThreadPool();
        execService2.execute(new ExceptionLeakingTask());
        execService2.execute(new ExceptionLeakingTask());


        execService1.shutdown();
        execService2.shutdown();

        System.out.println("["+ currentThreadName + "]" + " thread ends here...");
    }
}

class ExceptionLeakingTask implements Runnable {
    @Override
    public void run() {
        throw new RuntimeException();
    }
}

class ThreadExceptionHandler implements Thread.UncaughtExceptionHandler {
    private String handlerName;

    public ThreadExceptionHandler(String handlerName) {
        this.handlerName = handlerName;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println(handlerName + " caught Exception in Thread - " + t.getName() + " => " + e);
    }
}
