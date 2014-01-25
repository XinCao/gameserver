package gameserver.action;

import eu.infomas.annotation.AnnotationDetector;
import eu.infomas.annotation.AnnotationDetector.TypeReporter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * @author Liang Zhenjing<liangzhenjing@gmail.com>
 */
public class ActionService {

    static Logger logger = LoggerFactory.getLogger(ActionService.class);
    private static Pattern pattern = Pattern.compile("^\\s*([a-zA-Z0-9_]+)\\(([0-9,:\\s-]*)\\)\\s*$");
    private static Map<String, Class> actionClassMap = new HashMap<String, Class>();
    @Autowired
    private ApplicationContext context;

    public ActionService() {
        this.scanForActions();
    }

    /**
     * 扫面所有的Action
     */
    private void scanForActions() {
        long begin = System.currentTimeMillis();
        logger.info("Scanning for actions which is used action annotation, this may take a while, please wait...");
        final Map<String, Class> actionMap = new HashMap<String, Class>();

        /**
         * 内部匿名类
         */
        TypeReporter reporter = new TypeReporter() {
            @Override
            @SuppressWarnings("unchecked")
            public Class<? extends java.lang.annotation.Annotation>[] annotations() {
                return new Class[]{Action.class};
            }

            @Override
            public void reportTypeAnnotation(Class<? extends Annotation> annotationClass, String className) {
                try {
                    Class actionClazz = Class.forName(className);
                    if (!IAction.class.isAssignableFrom(actionClazz)) {
                        throw new IllegalArgumentException("类 <" + className + "> 的没有实现接口 IAction");
                    }
                    Action actionAnnotation = (Action) actionClazz.getAnnotation(annotationClass);
                    String name = actionAnnotation.option();
                    if (name == null || name.isEmpty()) {
                        throw new IllegalArgumentException("类 <" + className + "> 的 Action Annotation 设置不正确，name 属性必须设置");
                    }
                    actionMap.put(name, actionClazz);
                    logger.debug("Found Action, name=" + name + ", action.class=" + className);
                } catch (ClassNotFoundException e) {
                    logger.error("没有找到类 <" + className + ">。error=" + e.getMessage());
                    System.exit(1);
                } catch (Exception e) {
                    logger.error("Action Annotation 使用不正确，请检查。error=" + e.getMessage());
                    System.exit(1);
                }
            }
        };
        final AnnotationDetector cf = new AnnotationDetector(reporter);
        try {
            cf.detect();
            actionClassMap = Collections.unmodifiableMap(actionMap);
        } catch (IOException ex) {
            logger.error("Faild to scan actions。error={}", ex.getMessage());
            System.exit(1);
        }
        long cost = System.currentTimeMillis() - begin;
        logger.info("Finished scan for action, found {} action(s), cost={}ms", actionClassMap.size(), cost);
    }

    /**
     * 获得actionName 对应的管理类
     *
     * @param actionName
     * @return
     * @throws Exception
     */
    public IAction getActionClass(String actionName) throws Exception {
        Class clazz = actionClassMap.get(actionName);
        AbstractAction abstractAction = (AbstractAction)clazz.newInstance();
        abstractAction.setApplicationContext(context);
        return abstractAction; 
    }
}