/**
 * 消息泵，负责取出消息交给Handler来处理。
 */
public class Looper {

    ThreadLocal<Message> messageThreadLocal = new ThreadLocal<>();
    MessageQueue messageQueue;

    private Looper(){
        messageQueue = new MessageQueue();
    }


}
