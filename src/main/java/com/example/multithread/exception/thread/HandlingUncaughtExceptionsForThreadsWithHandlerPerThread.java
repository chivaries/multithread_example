package com.example.multithread.exception.thread;

public class HandlingUncaughtExceptionsForThreadsWithHandlerPerThread {
    public static void main(String argc[]) {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("["+ currentThreadName + "]" + " thread starts here...");
        //Thread.setDefaultUncaughtExceptionHandler(new ThreadExceptionHandler("DefaultHandler"));

        Thread thread1 = new Thread(new ExceptionLeakingTask(), "Mythread-1");
        thread1.setUncaughtExceptionHandler(new ThreadExceptionHandler("Handler-1"));

        Thread thread2 = new Thread(new ExceptionLeakingTask(), "Mythread-2");
        thread2.setUncaughtExceptionHandler(new ThreadExceptionHandler("Handler-2"));

        thread1.start();
        thread2.start();

        System.out.println("["+ currentThreadName + "]" + " thread ends here...");
    }
}
