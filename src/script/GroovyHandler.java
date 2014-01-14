package script;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Groovy 脚本助手
 *
 * @author penn
 */
public class GroovyHandler {

    private static final Logger log = LoggerFactory.getLogger(GroovyHandler.class);
    ClassLoader cl;
    GroovyClassLoader gcl;
    GroovyShell gs;
    Class<?> gClass;
    GroovyObject gObject;
    String groovyPath;
    File file;

    public GroovyHandler(String fileName, String groovyPath) {
        this.groovyPath = groovyPath;
        this.initMethod(fileName);
    }

    private boolean initMethod(String fileName) {
        cl = GroovyHandler.class.getClassLoader();
        gcl = new GroovyClassLoader(cl);
        gs = new GroovyShell(cl);
        String url = this.groovyPath + fileName;
        file = new File(url);
        if (!file.exists()) {
            return false;
        }
        try {
            gClass = gcl.parseClass(file);
            gObject = (GroovyObject) gClass.newInstance();
            return true;
        } catch (Exception e) {
            log.error("set file " + fileName + " exception", e);
        }
        return false;
    }

    public Object evaluate() {
        try {
            return gs.evaluate(file);
        } catch (Exception ex) {
            log.error("evaluate file exception", ex);
        }
        return null;
    }

    public Object invokeMethod(String method, Object[] params) {
        return gObject.invokeMethod(method, params);
    }

    public void setProperty(String name, Object obj) {
        gObject.setProperty(name, obj);
    }

    public Object getProperty(String name) {
        return gObject.getProperty(name);
    }
}