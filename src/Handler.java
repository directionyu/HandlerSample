/**
 * 负责发送和处理消息
 */
public class Handler {
    // 消息队列
    MessageQueue messageQueue;

    // 消息泵
    Looper looper;

    public Handler() {
        looper = Looper.getLocalLooper();
        if (looper == null){
            throw new RuntimeException("Can't create handler inside thread that has not called Looper.prepare()");
        }
        messageQueue = looper.messageQueue;
    }
}
