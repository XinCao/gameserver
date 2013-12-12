package script;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author caoxin
 */
public class ScriptTest {
    public static void main(String...args) {
        ApplicationContext ac = new FileSystemXmlApplicationContext("./config/groovy_script.xml");
        Fighting fighting = (Fighting)ac.getBean(Fighting.class);
        fighting.test();
    }
}