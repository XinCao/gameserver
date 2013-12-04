package jmx;

import java.io.IOException;
import mx4j.tools.adaptor.http.HttpAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jmx有关的管理逻辑
 *
 * @author CaoXin
 */
public class JmxManager {

    private final static Logger logger = LoggerFactory.getLogger(JmxManager.class);
    private HttpAdaptor httpAdaptor;

    public void startJmxService() {
        try {
            this.httpAdaptor.start();
        } catch (IOException e) {
            logger.error("启动了jmx系统出错！" + e.getMessage());
        }
        logger.info("已经成功开启jmx系统！");
    }

    public void stopJmxService() {
        this.httpAdaptor.stop();
        logger.info("已经成功关闭jmx系统！");
    }

    public void setHttpAdaptor(HttpAdaptor httpAdaptor) {
        this.httpAdaptor = httpAdaptor;
    }
}