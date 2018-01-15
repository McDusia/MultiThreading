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

    BoundedBuffer(int m)
    {
        this.m = m;
        items = new Object[2*m];

    }
    public void put(Object x, int a) throws InterruptedException {
        //long before = System.nanoTime();

        lock.lock();
        try {
            while (count == items.length || (items.length-count)<a)
                isSpace.await();
            for(int i = 0;i<a;i++) {
                items[putptr] = x;
                if (++putptr == items.length) putptr = 0;
                ++count;
            }
            areEnoughElem.signalAll();
        } finally {
            lock.unlock();
        }
        /*long after = System.nanoTime();
        long res = after - before;
        System.out.println(a + "  "+ res);
        */
    }

    public Object[] take(int a) throws InterruptedException {
        long before = System.nanoTime();
        lock.lock();
        try {
            while (count == 0 || count < a)
                areEnoughElem.await();
            Object[] tmp = new Object[a];
            for(int i = 0;i<a;i++) {
                Object x = items[takeptr];
                if (++takeptr == items.length) takeptr = 0;
                --count;
                tmp[i] = x;
            }
            isSpace.signalAll();
            long after = System.nanoTime();
            long res = after - before;
            System.out.println(a + "  "+ res);
            return tmp;
        } finally {
            lock.unlock();
        }

    }
}