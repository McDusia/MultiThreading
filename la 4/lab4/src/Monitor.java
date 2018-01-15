import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class Monitor {

    //do bufora są wpisywane po kolei nr procesów,
    // które ostatnio pracowały na danej działce
    public int plots[] = new int[10];
    private final Lock lock = new ReentrantLock();
    private boolean plotsOccupied = false;
    //private int currentNumber = -1;

    private final Condition accessToBuffor = lock.newCondition();
    //private final Condition secondPerson = lock.newCondition();

    Monitor() {
        for(int i = 0; i<10;i++)
            plots[i] = -1;
    }

    //gwarantowana jest kolejność przychodzenia
    //można przesyłać parametry do sekcji krytycznej

    public void lockPlots(int processNr) throws InterruptedException {
        lock.lock();

        try {
            //czekaj na dokończenie pracy poprzednika...
            //poszukaj w tablicy działki, gdzie należy wykonać pracę
            while (plotsOccupied)
              accessToBuffor.await();
            plotsOccupied = true;
            System.out.println("Dostałem dostęp do działek");

        } finally {
            lock.unlock();
        }
    }

    public void unlockPlots() throws InterruptedException {
        lock.lock();
        try {
            plotsOccupied = false;
            accessToBuffor.signalAll();
            System.out.println("Lock zwolniony \n");

        } finally {
            lock.unlock();
        }
    }

    public void work(int n, int plotNr) {

        System.out.println("I am working on plot nr"+plotNr+"- process nr"+ n);
        plots[plotNr] ++;
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Obecny stan tablicy działek: [");
        for(int i = 0; i< 10; i++)
        {
            System.out.print(plots[i]+", ");
        }
        System.out.print("]");
    }

    public int takenumber(int myNumber){
        lock.lock();
        int n = -1;
        try {
            for(int i = 0; i<plots.length; i++)
            {
                if(this.plots[i] == (myNumber -1))
                {
                    n = i;

                }
            }

        } finally {
            lock.unlock();
        }
        return n;
    }
}