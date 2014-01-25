package gameserver.action;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author caoxin
 */
public class ActionTest {
    
    public static void main(String ...args) throws Exception {
        
        ApplicationContext ac = new FileSystemXmlApplicationContext("./config/action.xml");
        IAction a = ac.getBean(ActionService.class).getActionClass("sample");
        a.setArguments(new Integer(1), "caoxin", new Integer(22));
        a.perform();
    }
}
