package gameserver.model;

/**
 *
 * @author caoxin
 */
public class IntPair {

    private int param1;
    private int param2;

    public IntPair(int param1, int param2) {
        this.param1 = param1;
        this.param2 = param2;
    }

    public int param1() {
        return param1;
    }

    public int param2() {
        return param2;
    }
}