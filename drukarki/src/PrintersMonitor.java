import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Madzia on 24.10.2017.
 */

class PrintersMonitor {
    //gwarantowana jest kolejność przychodzenia
    //można przesyłać parametry do sekcji krytycznej
    private final Lock lock = new ReentrantLock();
    //czy jakakolwiek drukarka gotowa do pracy?
    private final Condition notFull  = lock.newCondition();
    //czy jakakolwiek drukarka zajęta?
    private final Condition notEmpty = lock.newCondition();

    private int printersQnt;
    private final boolean[] AccesiblePrinters;// = new int[200];
    //private int putptr, takeptr, count;
    private int printersTaken;

    public PrintersMonitor(int n) {
        this.printersQnt = n;
        this.AccesiblePrinters = new boolean[n];
        for (int i = 0; i < n; i++) {
            this.AccesiblePrinters[i] = true;
        }
    }

    private int getFreePrinter() {
        for (int i = 0; i < printersQnt; i++) {
            if (AccesiblePrinters[i]) return i;
        }
        return -1;
    }

    public int taking() throws InterruptedException {
        lock.lock();
        try {
            while (printersTaken == AccesiblePrinters.length)
                notFull.await();
            int printerNumber = getFreePrinter();
            AccesiblePrinters[printerNumber] = false;
            ++printersTaken;
            notEmpty.signal();
            return printerNumber;
        } finally {
            lock.unlock();
        }
    }

    public void unlocking(int printerNumber) throws InterruptedException {
        lock.lock();
        try {
            while (printersTaken == 0)
                notEmpty.await();
            AccesiblePrinters[printerNumber] = true;
            --printersTaken;
            System.out.println("Przestała drukować drukarka nr " + printerNumber);
            notFull.signal();
        } finally {
            lock.unlock();
        }
    }


}