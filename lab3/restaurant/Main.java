package restaurant;

import java.util.ArrayList;

/**
 * Created by joanna on 05.11.17.
 */
public class Main {


    private static Waiter waiter = new Waiter();
    private static ArrayList<Thread> clientsThreads = new ArrayList<>();

    public static void main(String[] args) {

        int pairsAmount = 10;

        for(int i = 0; i < pairsAmount; i++) {
            Client client1 = new Client(i, waiter);
            Client client2 = new Client(pairsAmount - i - 1, waiter);
            Thread client1Thread = new Thread(client1);
            Thread client2Thread = new Thread(client2);
            clientsThreads.add(client1Thread);
            clientsThreads.add(client2Thread);
        }

        clientsThreads.forEach(Thread::start);

        clientsThreads.forEach((thread) -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
