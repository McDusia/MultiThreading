import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Madzia on 24.10.2017.
 */

class BoundedBuffer {
    private int m = 1;
    private final Lock lock = new ReentrantLock();
    private final Condition isSpace = lock.newCondition();
    private final Condition areEnoughElem = lock.newCondition();

    private Object[] items;
    private int putptr = 0, takeptr = 0, count = 0;
    private int currentNrPUT = 0;
    private int currentNrTAKE = 0;
    private int producentsConsumentsNr;

    BoundedBuffer(int m, int prNr )
    {
        this.m = m;
        items = new Object[2*m];
        producentsConsumentsNr = prNr;

    }

    public void put(Object x, int a, int nr) throws InterruptedException {
        //long before = System.nanoTime();
        lock.lock();
        try {
            //System.out.println("Prod Waiting to put "+a+" "+ nr);

            while (count == items.length || (items.length-count)<a || currentNrPUT!= nr)
                isSpace.await();
            //System.out.println("Prod "+ nr+ " is putting "+ a + " elem");
            for(int i = 0;i<a;i++) {
                items[putptr] = x;
                if (++putptr == items.length) putptr = 0;
                ++count;
            }
            if (++currentNrPUT == producentsConsumentsNr) currentNrPUT = 0;
            //System.out.println("PUT    buffor"+ count+ " now "+ currentNrPUT);
            isSpace.signalAll();
            areEnoughElem.signalAll();

        } finally {
            lock.unlock();
        }
        /*long after = System.nanoTime();
        long res = after-before;
        System.out.println(a + " "+res);*/
    }

    public Object[] take(int a, int nr) throws InterruptedException {
        long before = System.nanoTime();
        lock.lock();
        try {
            //System.out.println("Cons Waiting to take "+ a+" "+ nr);
            while (count == 0 || count < a || currentNrTAKE!= nr)
                areEnoughElem.await();
            //System.out.println("Cons "+ nr+ " is taking "+ a);
            Object[] tmp = new Object[m];
            for(int i = 0;i<a;i++) {
                Object x = items[takeptr];
                if (++takeptr == items.length) takeptr = 0;
                --count;
                tmp[i] = x;
            }
            if (++currentNrTAKE == producentsConsumentsNr) currentNrTAKE = 0;
            //System.out.println("TAKE buffor"+ count+ " now "+ currentNrTAKE);
            isSpace.signalAll();
            areEnoughElem.signalAll();
            long after = System.nanoTime();
            long res = after-before;
            System.out.println(a + " "+res);
            return tmp;
        } finally {
            lock.unlock();
        }
    }
}