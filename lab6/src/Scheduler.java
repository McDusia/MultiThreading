import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Madzia on 21.11.2017.
 */
class Scheduler implements Runnable{

    //thread-safe (kolejka- Monitor)
    private Queue<MethodRequest> q = new ConcurrentLinkedQueue();

    public void enqueue(MethodRequest request)
    {
        q.add(request);
    }

    public void run(){
        while(true)
        {
            MethodRequest request = q.poll();
            if(request != null) {
                if(request.guard())
                    request.execute();
                else
                    q.add(request);
            }

        }
    }

}
