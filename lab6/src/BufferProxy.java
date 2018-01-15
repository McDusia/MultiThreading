/**
 * Created by Madzia on 21.11.2017.
 */

class BufferProxy {
    private Scheduler scheduler;
    private Buffer aktywnyObiekt;

    BufferProxy(Scheduler s, Buffer b)
    {
        scheduler = s;

        aktywnyObiekt = b;
        Thread schedulerThread= new Thread(scheduler);
        schedulerThread.start();
    }

    public void put(int n) {
        PutRequest r = new PutRequest(n, aktywnyObiekt);
        scheduler.enqueue(r);
    }

    public Future take() {
        Future future = new Future();
        TakeRequest t = new TakeRequest( future, aktywnyObiekt);
        scheduler.enqueue(t);
        return future;
    }
}

