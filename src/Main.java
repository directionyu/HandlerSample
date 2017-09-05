import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        // 初始化Looper
        Looper.prepare();

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println(Thread.currentThread().getName() + "--receiver--" + msg.toString());
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        Message msg = new Message();
                        msg.what = 0;
                        synchronized (UUID.class) {
                            msg.object = Thread.currentThread().getName()+"--send---"+UUID.randomUUID().toString();
                        }
                        System.out.println(msg);
                        handler.sendMessage(msg);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
        //开始消息循环
        Looper.loop();
    }
}
