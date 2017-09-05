/**
 *
 */
public class Message {

    //处理该消息的Handler
    public Handler handler;
    // 哪个消息
    public int what;

    public Object object;

    @Override
    public String toString() {
        return object.toString();
    }
}
