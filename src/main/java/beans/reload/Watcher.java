package beans.reload;

import beans.Bean;
import beans.BeanFactory;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 3/23/2021 11:01 AM
 */
public class Watcher implements Runnable {

    @Override
    public void run() {
        while (true) {
            Bean manager = BeanFactory.getBean("server.myBean");
            manager.logic();
            System.gc();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
