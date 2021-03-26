import beans.Bean;
import beans.Container;

import java.util.Collections;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 3/23/2021 11:05 AM
 */
public class Main {
    public static void main(String[] args) {
        //start context
        Container container = Container.from(Main.class);
        container.putBeans(Collections.singletonList("server.myBean"));
        Bean bean = container.getBean("server.myBean");
        bean.logic();
    }
}
