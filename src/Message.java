/**
 *
 */
public class Message {
    // 哪个消息
    public int what;

    public Object object;

    @Override
    public String toString() {
        return object.toString();
    }
}
