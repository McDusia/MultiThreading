package printers;

import java.util.Random;

/**
 * Created by joanna on 05.11.17.
 */
public class TaskCreator implements Runnable {

    private int taskNumber;
    private PrintersMonitor monitor;
    private int printerNumber;

    public TaskCreator(PrintersMonitor monitor) {
        this.monitor = monitor;
    }

    public void run() {
        while(true) {
            createTaskToPrint();
            try {
                printerNumber = monitor.take();
                print(printerNumber);
                monitor.release(printerNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void createTaskToPrint() {
        Random rand = new Random();
        taskNumber = rand.nextInt((100 - 1) + 1) + 1;
    }

    private void print(int printerNumber) {
        System.out.println("Zadanie nr " + taskNumber + " drukuje drukarka nr " + printerNumber);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
