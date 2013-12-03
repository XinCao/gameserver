package event.dispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.Phased;

/**
 *
 * @author CaoXin
 */
public class BaseService implements IService, Phased {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init() {
    }

    @Override
    public int getPhase() {
        return 100;
    }
}