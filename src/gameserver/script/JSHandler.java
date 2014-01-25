package gameserver.script;

import java.io.FileReader;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JS脚本助手 javascript
 *
 */
public class JSHandler {

    private static final Logger logger = LoggerFactory.getLogger(JSHandler.class);
    ScriptEngineManager scriptEngineManager;
    ScriptEngine scriptEngine;
    private String jsPath;

    public JSHandler() {
        scriptEngineManager = new ScriptEngineManager();
        scriptEngine = scriptEngineManager.getEngineByName("javascript");
    }
    
    public JSHandler(String name) {
        scriptEngineManager = new ScriptEngineManager();
        scriptEngine = scriptEngineManager.getEngineByName(name);
    }

    public JSHandler(String name, String jsPath) {
        scriptEngineManager = new ScriptEngineManager();
        scriptEngine = scriptEngineManager.getEngineByName(name);
        this.jsPath = jsPath;
    }

    /**
     * 执行js文件脚本
     *
     * @param filename
     * @return 脚本执行返回
     */
    public Object evalFile(String filename) {
        try {
            return scriptEngine.eval(new FileReader(jsPath + filename));
        } catch (Exception ex) {
            logger.error("eval file " + filename + " exception", ex);
        }
        return null;
    }

    /**
     * 执行js脚本中某个指定方法并传入参数
     *
     * @param filename 文件名
     * @param funcation 方法名
     * @param obj 参数组
     * @return 脚本执行返回
     */
    public Object evalFile(String filename, String funcation, Object... objs) {
        try {
            Object obj = scriptEngine.eval(new FileReader(jsPath + filename));
            logger.debug("this is : " + obj);
            Invocable inv = (Invocable) scriptEngine;
            return inv.invokeFunction(funcation, objs);
        } catch (Exception ex) {
            logger.error("eval file " + filename + ", funcation is " + funcation + " exception", ex);
        }
        return null;
    }

    /**
     * 直接执行js语句
     *
     * @param jsc
     * @return 脚本执行返回
     */
    public Object evalString(String jsc) {
        try {
            return scriptEngine.eval(jsc);
        } catch (Exception ex) {
            logger.error("eval js \"" + jsc + "\" exception", ex);
        }
        return null;
    }

    /**
     * 直接执行js语句中的指定方法并传入参数
     *
     * @param filename
     * @param funcation
     * @param obj
     * @return 脚本执行返回
     */
    public Object evalString(String jsc, String funcation, Object... obj) {
        try {
            Invocable inv = (Invocable) scriptEngine.eval(jsc);
            return inv.invokeFunction(funcation, obj);
        } catch (Exception ex) {
            logger.error("eval js \"" + jsc + "\", funcation is " + funcation + " exception", ex);
        }
        return null;
    }
}