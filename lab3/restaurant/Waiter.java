package restaurant;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by joanna on 05.11.17.
 */
public class Waiter {

    private final Lock lock = new ReentrantLock();
    private boolean tableOccupied = false;
    private int currentPairNumber = -1;

    private final Condition freeTable = lock.newCondition();
    private final Condition iAmHere = lock.newCondition();

    public void giveMeTable(int pairNumber) throws InterruptedException {
        lock.lock();

        try {
            while (tableOccupied || (currentPairNumber != pairNumber && currentPairNumber != -1))
                freeTable.await();

            if (currentPairNumber == -1) {
                currentPairNumber = pairNumber;
                System.out.println("Osoba 1 z pary nr " + pairNumber + " czeka...");
                while (!tableOccupied) {
                    iAmHere.await();
                }
            } else {
                tableOccupied = true;
                iAmHere.signal();
                System.out.println("... osoba 2 z pary nr " + pairNumber + " już jest!");
            }
        } finally {
            lock.unlock();
        }
    }

    public void releaseTable() throws InterruptedException {
        lock.lock();
        try {
            if (currentPairNumber != -1) {
                System.out.println("Osoba 1 z pary nr " + currentPairNumber + " zjadła...");
                currentPairNumber = -1;
            } else {
                tableOccupied = false;
                freeTable.signalAll();
                System.out.println("... osoba 2 z tej pary tez zjadła!\n");
            }
        } finally {
            lock.unlock();
        }

    }
}
