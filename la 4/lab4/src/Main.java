import java.util.ArrayList;

/**
 * Created by Madzia on 07.11.2017.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        ArrayList<Thread> teams = new ArrayList<>();
        //ConstructionTeam teams[] = new ConstructionTeam[5];

        Monitor monitor = new Monitor();
        for(int i = 0; i< 5; i++)
        {
            Thread t = new Thread(new ConstructionTeam(i, monitor));
            teams.add(t);
        }
        teams.forEach(Thread::start);

        teams.forEach((thread) -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }
}
