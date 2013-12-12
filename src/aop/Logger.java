package aop;

/**
 *
 * @author caoxin
 */
public class Logger {

    public void start(String name) {
        System.out.println(name + " = " + "start select song!");
    }

    public void stop(String name) {
        System.out.println(name + " = " + "stop select song.");
    }

    public void success(String name) {
        System.out.println(name + " = " + "this selecting song successed");
    }

    public void filure(String name) {
        System.out.println(name + " = " + "this selecting song filure!");
    }
}