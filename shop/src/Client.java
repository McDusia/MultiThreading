import java.awt.*;

/**
 * Created by Madzia on 17.10.2017.
 */
public class Client extends Thread{

    Shop shop;
    Semaphore s;
    int number;

    Client(Shop shop, Semaphore sem, int nr) {
        this.shop = shop;
        this.s = sem;
        this.number = nr;
        try {
            standInQueue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            //standInQueue();
            takeBucket(number);
            goOutOfTheQueue();

            startShopping();
            giveBucket(number);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void standInQueue() throws InterruptedException{
        System.out.println(number + "czekam na koszyk");
        Thread.sleep((1000));
        s.semKolejkaOpusc();
        shop.queue.add(number);
        s.semKolejkaPodnies();

    }

    private void goOutOfTheQueue() throws InterruptedException {
        s.semKolejkaOpusc();
        int index = shop.queue.indexOf(number);
        shop.queue.remove(index);
        System.out.println(number + "wychodzę z kolejki do koszyków");
        Thread.sleep((1000));
        s.semKolejkaPodnies();
    }

    private void takeBucket(int i) throws InterruptedException{
        while(shop.queue.indexOf(number) != 0) {
            try {
                sleep(1000);
            } catch(InterruptedException e) {}
        }
        s.opusc();
    }

    private void startShopping() throws InterruptedException {
        System.out.println(number + "kupuję");
        Thread.sleep((500));
        Thread.sleep((long)(Math.random() * 1000));
    }

    private void giveBucket(int i) throws InterruptedException{
        s.podnies();
        System.out.println(i + "skończyłem zakupy");
        Thread.sleep((1000));
    }
}
