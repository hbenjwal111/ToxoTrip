package listeners;

/**
 * Created by nalin on 1/2/16.
 */
public interface IResponseReceivedNotifyInterface {

    void responseReceived(ResponseArgs responseArgs);

    void stringResponseReceived(String response);
}
