package event.processer;

import event.listener.IGameEventListener;
import java.util.List;
import event.core.EventDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 事件监听处理（核心）
 *
 * @author CaoXin
 */
public class EventListenerProcessor implements BeanPostProcessor {

    private static Logger logger = LoggerFactory.getLogger(EventListenerProcessor.class);
    private EventDispatcher internalDispatcherDispatcher;

    public EventListenerProcessor(EventDispatcher dispatcher) {
        this.internalDispatcherDispatcher = dispatcher;
    }

    /**
     * Spring Bean 构造前的初始化
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
    

    /**
     * Spring Bean 构造后的初始化
     *
     * @param bean 过滤Bean，查找事件监听Bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (IGameEventListener.class.isAssignableFrom(bean.getClass())) {
            IGameEventListener listener = (IGameEventListener) bean;
            List<String> eventNames = listener.getEvents();
            if (eventNames == null) {
                return bean;
            }
            for (String eventName : eventNames) {
                try {
                    this.internalDispatcherDispatcher.addListener(eventName, listener);
                } catch (Exception ex) {
                    String errorInfo = "Failed to registered event listener, event.name=" + eventName + ", listener.class=" + bean.getClass().getName();
                    logger.error(errorInfo, ex);
                }
                logger.debug("Registered event listener, event.name=" + eventName + ", listener.class=" + bean.getClass().getName());
            }
        }
        return bean;
    }
}