package restaurant;

/**
 * Created by joanna on 05.11.17.
 */
public class Client implements Runnable{

    public int pairNumber;
    private Waiter waiter;

    public Client(int pairNumber, Waiter waiter) {
        this.pairNumber = pairNumber;
        this.waiter = waiter;
    }

    public void run() {
        while(true) {
            try {
                mindMyBusiness();
                waiter.giveMeTable(pairNumber);
                eat();
                waiter.releaseTable();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void mindMyBusiness() throws InterruptedException {
        Thread.sleep(1000);
    }

    private void eat() throws InterruptedException {
        Thread.sleep(3000);
    }
}
