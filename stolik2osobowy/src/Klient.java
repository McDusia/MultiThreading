

class Klient implements Runnable
{
    //private Buffer buffer;
    private Kelner monitor;
    private int myNr;
    private int pairNr;



    public Klient(int myNr, int pairNr, Kelner monitor)
    {
        this.monitor = monitor;
        this.pairNr = pairNr;
        this.myNr = myNr;

    }
    public void run()
    {
        //for(int j = 0; j<50; j++)
        while(true)
        {
            try {
                monitor.lockTable(pairNr);
                eat();
                monitor.unlockTable();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void eat() throws InterruptedException{

        System.out.println("Jemy, mój nr: "+myNr+"nr pary: "+pairNr);
        Thread.sleep(1000);
    }

}


