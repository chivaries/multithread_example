package com.example.multithread.exception.thread;

public class HandlingUncaughtExceptionsForThreadsWithHandler {
    public static void main(String argc[]) {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("[" + currentThreadName + "]" + " thread starts here...");

        Thread.setDefaultUncaughtExceptionHandler(new ThreadExceptionHandler("DefaultHandler"));

        Thread thread1 = new Thread(new ExceptionLeakingTask(), "Mythread-1");
        Thread thread2 = new Thread(new ExceptionLeakingTask(), "Mythread-2");

        thread1.start();
        thread2.start();

        System.out.println("["+ currentThreadName + "]" + " thread ends here...");
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
