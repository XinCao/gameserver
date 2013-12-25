package aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @version 1.0
 * @author caoxin
 */
public class AopTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ApplicationContext ac = new FileSystemXmlApplicationContext("./config/aop.xml");
        SongSelector songSelector = ac.getBean(SongSelector.class);
        songSelector.song("caoxin");
    }
}