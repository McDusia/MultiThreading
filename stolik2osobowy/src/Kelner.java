import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Kelner {

    private final Lock lock = new ReentrantLock();
    private boolean tableOccupied = false;
    private int currentPairNumber = -1;

    private final Condition accessToTable = lock.newCondition();
    private final Condition secondPerson = lock.newCondition();

    //gwarantowana jest kolejność przychodzenia
    //można przesyłać parametry do sekcji krytycznej

    public void lockTable(int pairNumber) throws InterruptedException {
        lock.lock();

        try {
            //czekaj na zwolnienie stolika dopóki...
            while (tableOccupied ||
                    (currentPairNumber != pairNumber && currentPairNumber != -1))
                accessToTable.await();

            if (currentPairNumber == -1) {
                currentPairNumber = pairNumber;
                System.out.println("Jedna osoba z pary " + pairNumber + " czeka...");
                while (!tableOccupied) {
                    secondPerson.await();
                }
            } else {
                tableOccupied = true;
                secondPerson.signal();
                System.out.println("... osoba 2 z pary nr " + pairNumber + " dotarła!");
            }
        } finally {
            lock.unlock();
        }
    }

    public void unlockTable() throws InterruptedException {
        lock.lock();
        try {
            if (currentPairNumber != -1) {
                System.out.println("Osoba 1 z pary nr " + currentPairNumber + " zjadła...");
                currentPairNumber = -1;
            } else {
                tableOccupied = false;
                accessToTable.signalAll();
                System.out.println("... osoba 2 z tej pary tez zjadła!\n");
            }
        } finally {
            lock.unlock();
        }
    }

}