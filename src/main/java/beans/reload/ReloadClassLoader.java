package beans.reload;

import java.io.*;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 3/23/2021 10:10 AM
 */
public class ReloadClassLoader extends ClassLoader {
    private final String basePath;

    public ReloadClassLoader(String basePath) {
        //指定父类加载器
        super(ClassLoader.getSystemClassLoader());
        this.basePath = basePath;
    }

    @Override
    public Class<?> findClass(String name) {
        byte[] data = this.loadClassData(name);
        if (data == null) {
            return null;
        }
        return this.defineClass(name, data, 0, data.length);
    }

    private byte[] loadClassData(String name) {
        try {
            System.out.println("重新读取字节码");
            name = name.replace(".", "\\");
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name + ".class"));
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
}
