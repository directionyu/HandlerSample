import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueue {

    // 互斥锁
    Lock lock;

    Condition mEmptyQueue;

    Condition mFullQueue;
    // 消息集合
    Message[] messages;

    // 消息进队列下标
    int putIndex = 0;
    // 队列取消息下标
    int getIndex = 0;
    // 消息记录数，用于判断是否继续生产消息和消费消息
    int count = 0;

    public MessageQueue() {
        lock = new ReentrantLock();
        messages=new Message[50];
        // ReentrantLock.newCondition() 返回的是Condition的一个实现，该类在AbstractQueuedSynchronizer中被实现，叫做newCondition（）
        mEmptyQueue = lock.newCondition();
        mFullQueue = lock.newCondition();
    }


    /**
     * 生产消息（消息进队列）
     *
     * @param message
     */
    final void enqueueMessage(Message message) {

        try {
            lock.lock();
            // 如果消息队列满了，那么让当前线程进入await等待队列，并释放当前锁，当其他线程调用singal()会重新请求锁，与Object.wait类似。
            while (count == messages.length) {
                try {
                    mFullQueue.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            messages[putIndex] = message;
            if (messages.length == 0) {
                putIndex = 0;
            } else {
                putIndex++;
                count++;
            }
            mEmptyQueue.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 消费消息
     *
     * @return
     */
    final Message consumeMessage() {
        Message message = null;
        try {
            lock.lock();
            while (count == 0) {
                try {
                    mEmptyQueue.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 遍历取消息，每取一个将之置为null
                message = messages[getIndex];
                messages[getIndex] = null;
                if (messages.length == 0) {
                    getIndex = 0;
                } else {
                    getIndex++;
                    count--;
                }
                mFullQueue.signalAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return message;
    }

}
