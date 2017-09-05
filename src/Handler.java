/**
 * 负责发送和处理消息
 */
public class Handler {
    // 消息队列
    private MessageQueue messageQueue;

    // 消息泵
    Looper looper;

    public Handler() {
        looper = Looper.getLocalLooper();
        if (looper == null) {
            throw new RuntimeException("Can't create handler inside thread that has not called Looper.prepare()");
        }
        messageQueue = looper.messageQueue;
    }

    public final void sendMessage(Message msg) {
        MessageQueue queue = messageQueue;
        if (queue != null) {
            msg.handler = this;
            queue.enqueueMessage(msg);
        } else {
            throw new RuntimeException(this + " sendMessage() called with no mQueue");
        }

    }

    /**
     * 子类必须实现此方法接收处理消息
     * Subclasses must implement this to receive messages.
     */
    public void handleMessage(Message msg) {
    }

    /**
     * 在此处理系统消息
     * Handle system messages here.
     */
    public void dispatchMessage(Message msg) {
        handleMessage(msg);
    }

}
