package jms;

import java.io.IOException;
import mx4j.tools.adaptor.http.HttpAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jms有关的管理逻辑
 *
 * @author CaoXin
 */
public class JmsManager {

    private final static Logger logger = LoggerFactory.getLogger(JmsManager.class);
    private HttpAdaptor httpAdaptor;

    public void startJmsService() {
        try {
            this.httpAdaptor.start();
        } catch (IOException e) {
            logger.error("启动了jms系统出错！" + e.getMessage());
        }
        logger.info("已经成功开启jms系统！");
    }

    public void stopJmsService() {
        this.httpAdaptor.stop();
        logger.info("已经成功关闭jms系统！");
    }

    public void setHttpAdaptor(HttpAdaptor httpAdaptor) {
        this.httpAdaptor = httpAdaptor;
    }
}