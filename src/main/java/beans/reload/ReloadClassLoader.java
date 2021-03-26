package beans.reload;

import java.io.*;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 3/23/2021 10:10 AM
 */
public class ReloadClassLoader extends ClassLoader {
    private final byte[] classData;

    public ReloadClassLoader(byte[] classData) {
        //指定父类加载器
        super(ClassLoader.getSystemClassLoader());
        this.classData = classData;
    }

    @Override
    public Class<?> findClass(String name) {
        return this.defineClass(name, classData, 0, classData.length);
    }

}
