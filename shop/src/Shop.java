/**
 * Created by Madzia on 17.10.2017.
 */
import java.util.ArrayList;
import java.util.List;

public class Shop {

    private int bucketsQuantity;
    public final List<Integer> queue = new ArrayList<>();


    Shop(int i){

        this.bucketsQuantity = i;
    }

    public static void main(String[] args) throws Exception {

        Shop shop = new Shop(10);
        Semaphore sem = new Semaphore(shop.bucketsQuantity);
        Client[] clients = new Client[15];

        for(int i = 0;i<15;i++) {

            clients[i] = new Client(shop,sem,i);
        }

        for(int i = 0;i<15;i++) {
            clients[i].start();
        }
        try{
            for(int i = 0;i<15;i++) {
                clients[i].join();
            }
        } catch(Exception e){System.out.println(e);}

    }
}
