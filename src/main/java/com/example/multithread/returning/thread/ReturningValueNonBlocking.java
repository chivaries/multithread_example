package com.example.multithread.returning.thread;

import java.util.concurrent.TimeUnit;

public class ReturningValueNonBlocking {
    public static void main(String[] args) {
        System.out.println("Main thread starts here...");

        MyThreadTaskWithListener task1 = new MyThreadTaskWithListener(new RandomSumObserver("task1"));
        MyThreadTaskWithListener task2 = new MyThreadTaskWithListener(new RandomSumObserver("task2"));

        new Thread(task1).start();
        new Thread(task2).start();

        System.out.println("Main thread ends here...");
    }
}

/** Listener interface **/
interface ResultListener<T> {
    public void notifyResult(T t);
}

/** Listener **/
class RandomSumObserver implements ResultListener<Integer> {
    private String taskId;

    public RandomSumObserver(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public void notifyResult(Integer result) {
        System.out.println(taskId + " result" + result);
    }
}


class MyThreadTaskWithListener implements Runnable {
    private static int count = 0;
    private int id;
    private int randomSum = 0;
    private ResultListener<Integer> listener; //listener

    @Override
    public void run() {
        for(int i = 0; i < 5; i++) {
            System.out.println("<" + id + ">TICK TICK " + i);
            randomSum += Math.random()*1000;
            try {
                TimeUnit.MICROSECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.listener.notifyResult(randomSum);
    }

    public MyThreadTaskWithListener(ResultListener<Integer> listener) {
        this.id = ++count;
        this.listener = listener;
    }
}
