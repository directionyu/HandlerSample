/**
 * 消息泵，负责取出消息交给Handler来处理。
 */
public class Looper {

    static ThreadLocal<Looper> messageThreadLocal = new ThreadLocal<>();

    MessageQueue messageQueue;

    private Looper() {
        messageQueue = new MessageQueue();
    }

    /**
     * 获取Looper的线程副本
     *
     * @return 返回Looper线程副本
     */
    public static Looper getLocalLooper() {
        // 返回此线程局部变量的当前线程副本中的值，如果这是线程第一次调用该方法，则创建并初始化此副本。
        return messageThreadLocal.get();
    }

    public static void prepare() {
        if (messageThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        messageThreadLocal.set(new Looper());
    }

    public static void loop() {
        Looper looper = getLocalLooper();
        if (looper == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }

        MessageQueue messageQueue = looper.messageQueue;
        // 无限循环
        for (; ; ) {
            Message message = messageQueue.consumeMessage();
            if (message == null || message.handler == null) {
                continue;
            }

            message.handler.dispatchMessage(message);
        }
    }


}
