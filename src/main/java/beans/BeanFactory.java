package beans;

import beans.reload.ReloadClassLoader;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 3/23/2021 10:39 AM
 */
@Slf4j
public class BeanFactory {
    /**
     * 储存所有的bean
     */
    public final Map<String, BeanInfo> beanInfoMap = new HashMap<>();
    /**
     * 所有bean都基于这个地址
     */
    public  String BASE_PATH;
    public BeanFactory(String basePath) {
        this.BASE_PATH = basePath;
    }

    public void reloadBeanInfoMapIfNecessary() {
        beanInfoMap.forEach((beanName, beanInfo) -> {
            File loadFile = new File(BASE_PATH + beanName + ".class");
            if (loadFile.lastModified() != beanInfo.getLoadTime()) {
                BeanInfo newBeanInfo = load(beanName, loadFile);
                beanInfoMap.put(beanName, newBeanInfo);
            }
        });
    }

    /**
     * 向容器中放入bean
     * @param beanName bean的名字
     * @return bean
     */
    public Bean setBean(String beanName) {
        File loadFile = new File(BASE_PATH + beanName + ".class");
        BeanInfo beanInfo = load(beanName, loadFile);
        beanInfoMap.put(beanName, beanInfo);
        return beanInfo.getBean();
    }

    /**
     * 从容器中取出bean
     * @param beanName name
     * @return bean
     */
    public Bean getBean(String beanName) {
        if (!beanInfoMap.containsKey(beanName)) {
            return null;
        }
        return beanInfoMap.get(beanName).getBean();
    }

    /**
     * @param className 类的限定名
     * @return beanInfo
     */
    public BeanInfo load(String className, File loadFile) {
        byte[] classData = loadClassData(loadFile);
        ReloadClassLoader reloadClassLoader = new ReloadClassLoader(classData);
        Class<?> loadClass = reloadClassLoader.findClass(className);
        if (loadClass == null) {
            return null;
        }
        Bean bean = newInstance(loadClass);
        BeanInfo beanInfo = new BeanInfo();
        beanInfo.setBean(bean);
        long lastModified = loadFile.lastModified();
        beanInfo.setLoadTime(lastModified);
        return beanInfo;
    }

    private byte[] loadClassData(File loadFile) {
        try {
            System.out.println("读取字节码");
            FileInputStream fileInputStream = new FileInputStream(loadFile);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int b = 0;
            while ((b = fileInputStream.read()) != -1) {
                byteArrayOutputStream.write(b);
            }
            fileInputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (FileNotFoundException e) {
            System.out.println("找不到文件, 可能文件尚未编译完");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Bean newInstance(Class<?> loadClass) {
        try {
            return (Bean)loadClass.getConstructor().newInstance(new Object[]{});
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


}
