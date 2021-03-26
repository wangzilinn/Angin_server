package beans;

import beans.reload.ReloadClassLoader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 3/23/2021 10:39 AM
 */
public class BeanFactory {
    public static final Map<String, BeanInfo> beanInfoMap = new HashMap<>();

    public static final String BASE_PATH = "D:\\Case\\210326_angin_server\\target\\classes\\";

    public static Bean getBean(String className) {
        String classPath = BASE_PATH + className.replace(".", "\\");
        File loadFile = new File(classPath + ".class");
        long lastModified = loadFile.lastModified();
        System.out.println("当前类 lastModified = " + lastModified);
        if (beanInfoMap.get(className) == null) {
            // 如果该类没有被加载过, 直接加载
            System.out.println("第一次加载");
            load(className, lastModified);
        } else if (beanInfoMap.get(className).getLoadTime() != lastModified) {
            // 如果时间戳变化,则重新加载
            System.out.println("重新加载");
            load(className, lastModified);
        }
        return beanInfoMap.get(className).getBean();
    }

    public static void load(String className, long lastModified) {
        ReloadClassLoader reloadClassLoader = new ReloadClassLoader(BASE_PATH);
        Class<?> loadClass = reloadClassLoader.findClass(className);
        if (loadClass == null) {
            return;
        }
        Bean bean = newInstance(loadClass);
        BeanInfo beanInfo = new BeanInfo();
        beanInfo.setBean(bean);
        beanInfo.setLoadTime(lastModified);
        beanInfoMap.put(className, beanInfo);
    }

    private static Bean newInstance(Class<?> loadClass) {
        try {
            return (Bean)loadClass.getConstructor().newInstance(new Object[]{});
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


}
