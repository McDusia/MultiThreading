import java.util.ArrayList;

/**
 * Created by Madzia on 04.11.2017.
 */
public class Restauracja {

    private static ArrayList<Thread> clientsThreads = new ArrayList<>();

    private static Kelner monitor = new Kelner();

    public static void main(String[] args) throws Exception
    {
        int qnt = 10; //10 par

        for(int i = 0; i< qnt; i++)
        {
            Thread client1Thread = new Thread(new Klient(1,i, monitor));
            Thread client2Thread = new Thread(new Klient(2,i, monitor));
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
