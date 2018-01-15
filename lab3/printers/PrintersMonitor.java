package printers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by joanna on 24.10.17.
 */
public class PrintersMonitor {

    private final Lock lock = new ReentrantLock();
    private final Condition anyPrintersFree = lock.newCondition();
    private final Condition anyPrintersBusy = lock.newCondition();

    private int printersAmount;
    private final Boolean[] printersAvailable;
    private int printersTaken;

    public PrintersMonitor(int printersAmount) {
        this.printersAmount = printersAmount;
        this.printersAvailable = new Boolean[printersAmount];
        for (int i = 0; i < printersAmount; i++) {
            this.printersAvailable[i] = true;
        }
    }

    private int getFreePrinter() {
        for (int i = 0; i < printersAmount; i++) {
            if (printersAvailable[i]) return i;
        }
        return -1;
    }

    public int take() throws InterruptedException {
        lock.lock();
        try {
            while (printersTaken == printersAvailable.length)
                anyPrintersFree.await();
            int printerNumber = getFreePrinter();
            printersAvailable[printerNumber] = false;
            ++printersTaken;
            anyPrintersBusy.signal();
            return printerNumber;
        } finally {
            lock.unlock();
        }
    }

    public void release(int printerNumber) throws InterruptedException {
        lock.lock();
        try {
            while (printersTaken == 0)
                anyPrintersBusy.await();
            printersAvailable[printerNumber] = true;
            --printersTaken;
            System.out.println("Przestała drukować drukarka nr " + printerNumber);
            anyPrintersFree.signal();
        } finally {
            lock.unlock();
        }
    }
}
