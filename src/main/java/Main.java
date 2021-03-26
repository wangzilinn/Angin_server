import beans.reload.Watcher;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 3/23/2021 11:05 AM
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(Main.class.getResource("").getPath());
        new Thread(new Watcher()).start();
    }
}
