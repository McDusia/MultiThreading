/**
 * Created by Madzia on 07.11.2017.
 */
class ConstructionTeam implements Runnable
{
    private int myNumber;
    private Monitor monitor;

    public ConstructionTeam(int n, Monitor m){
        myNumber = n;
        monitor = m;
        System.out.print("thread created");
    }

    public void run()
    {
        try {
            for(int i = 0; i<10; i++)
            {
                int num;
                while((num = monitor.takenumber(myNumber))==-1);
                monitor.lockPlots(myNumber);
                monitor.work(myNumber, num);
                monitor.unlockPlots();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
