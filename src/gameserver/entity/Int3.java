package gameserver.entity;

/**
 *
 * @author caoxin
 */
public class Int3 {

    private int param1;
    private int param2;
    private int param3;

    public Int3(int param1, int param2, int param3) {
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
    }

    public int param1() {
        return this.param1;
    }

    public int param2() {
        return this.param2;
    }

    public int param3() {
        return this.param3;
    }
}