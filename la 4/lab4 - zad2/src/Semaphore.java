/**
 * Created by Madzia on 17.10.2017.
**/


public class Semaphore {
    private boolean busy;

    public Semaphore() {
        busy = false;
    }

    synchronized public void opusc() {
        while(busy) {
            try {
                wait();
            } catch(InterruptedException e) {}
        }
        busy = true;
        notify();
    }

    synchronized  public void podnies() {
        busy = false;
        notify();
    }

}
