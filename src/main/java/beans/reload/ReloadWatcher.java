package beans.reload;

import beans.Bean;
import beans.BeanFactory;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 3/23/2021 11:01 AM
 */
public class ReloadWatcher implements Runnable {
    private final BeanFactory beanFactory;

    public ReloadWatcher(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void run() {
        while (true) {
            beanFactory.reloadBeanInfoMapIfNecessary();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
