package beans;

import beans.reload.ReloadWatcher;

import java.util.List;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 3/26/2021 9:23 PM
 */
public class Container {
    private final BeanFactory beanFactory;
    private final ReloadWatcher reloadWatcher;

    public static Container from(Class<?> mainClass) {
        return new Container(mainClass);
    }

    private Container(Class<?> mainClass) {
        this.beanFactory = new BeanFactory(mainClass.getResource("").getPath());
        this.reloadWatcher = new ReloadWatcher(beanFactory);
    }

    public void putBeans(List<String> beanNameList) {
        // 加载所有bean
        beanNameList.forEach(beanFactory::setBean);
        // 启动监控线程, 进行动态类重载
        new Thread(reloadWatcher).start();
    }

    public Bean getBean(String beanName) {
        return beanFactory.getBean(beanName);
    }
}
