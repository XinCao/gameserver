package aop;

/**
 *
 * @author caoxin
 */
public class SongSelector {


    public String song(String name) {
        String[] songSet = {"a", "b", "c", "d", "e"};
        int max = songSet.length;
        int randomInt = (int) (Math.random() * max);
        System.out.println(songSet[randomInt]);
        return songSet[randomInt];
    }
}