/**
 * Created by Madzia on 21.11.2017.
 */
public class TakeRequest<T> implements MethodRequest{

    private Future future;
    private Buffer aktywnyObiekt;
    private int n;

    TakeRequest( Future f, Buffer b) {
        //this.n = n;
        aktywnyObiekt = b;
        future = f;
    }


    public boolean guard() {
        return(!aktywnyObiekt.isEmpty());
    }

    public void execute() {

        int result= aktywnyObiekt.take();
        future.setResult(result);
        future.setAvailability();
    }

    public Future getFuture() {
        return future;
    }
}
