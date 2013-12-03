package action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Liang Zhenjing<liangzhenjing@gmail.com>
 */
abstract class AbstractAction implements IAction, ApplicationContextAware {

    static Logger logger = LoggerFactory.getLogger(IAction.class);
    protected ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    protected void logAction(Object... args) {
        String paramsStr = "";
        for (Object object : args) {
            if (object != null) {
                paramsStr += ", " + object.toString();
            }
        }
        logger.debug("Action executed: action=" + this.getClass().getSimpleName() + ", params=" + paramsStr);
    }
    
}