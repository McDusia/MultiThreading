import static java.lang.Thread.sleep;

/**
 * Created by Madzia on 24.10.2017.
 */

class Consumer implements Runnable
{
    //private Buffer buffer;
    private PrintersMonitor monitor;
    private int myNr;

    public Consumer(PrintersMonitor monitor, int n)
    {
        this.monitor = monitor;
        myNr = n;
    }
    public void run()
    {
        while(true)
        //for(int j = 0; j<20; j++)
        {
            createTask();
            int printerNr = 0;
            try {
                printerNr = monitor.taking();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            print(printerNr);
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Consumer " + myNr +  "printed in : " + printerNr);
            try {
                monitor.unlocking(printerNr);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void createTask() {

        System.out.println("Utworzono zadanie"+myNr);
    }

    private void print(int printerNumber) {
        System.out.println("Nowe zadanie, drukuje drukarka nr " + printerNumber);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}


