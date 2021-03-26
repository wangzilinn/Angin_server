package beans;

import beans.Bean;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 3/23/2021 10:34 AM
 */
public class BeanInfo {
    private long loadTime;
    /**
     * 要加载的类
     */
    private Bean bean;

    public long getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(long loadTime) {
        this.loadTime = loadTime;
    }

    public Bean getBean() {
        return bean;
    }

    public void setBean(Bean bean) {
        this.bean = bean;
    }
}

