import sun.plugin2.message.Message;

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

    int putIndex;

    int getIndex;

    int count;

    public MessageQueue(Message[] messages) {
        lock = new ReentrantLock();
        // ReentrantLock.newCondition() 返回的是Condition的一个实现，该类在AbstractQueuedSynchronizer中被实现，叫做newCondition（）
        mEmptyQueue = lock.newCondition();
        mFullQueue = lock.newCondition();
    }

    final void enqueueMessage(Message message){
        lock.lock();
        // 如果消息队列满了，那么让当前线程进入await等待队列，并释放当前锁，当其他线程调用singal()会重新请求锁，与Object.wait类似。
        while (count == messages.length){
            try {
                mFullQueue.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        messages[putIndex] = message;
    }
}
