/**
 * Created by Madzia on 17.10.2017.
 **/


public class Semaphore {

    public int bucketsQuantity;
    public boolean busy;

    public Semaphore(int i) {
        bucketsQuantity = i;
        busy = false;
    }

    synchronized public void opusc() {
        while(bucketsQuantity == 0) {
            try {
                wait();
            } catch(InterruptedException e) {}
        }
        bucketsQuantity --;
        notifyAll();
    }

    synchronized  public void podnies() {
        bucketsQuantity ++;
        notifyAll();
    }


    synchronized public void semKolejkaOpusc() {
        while(busy) {
            try {
                wait();
            } catch(InterruptedException e) {}
        }
        busy = true;
        notifyAll();
    }

    synchronized  public void semKolejkaPodnies() {
        busy = false;
        notifyAll();
    }

}
