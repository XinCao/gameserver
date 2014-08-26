package gameserver.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 屏蔽词
 * 
 * @author caoxin
 */
public class WordFilter {

    private Map<String, Pattern> blockedWords = new HashMap<String, Pattern>();
    private static final Logger logger = LoggerFactory.getLogger(WordFilter.class);
    private String[] badnum = new String[]{"64", "722", "89"};
    
    public WordFilter () {
        this("word_filter.txt");
    }

    public WordFilter(String fileName) {
        this.loadFromFile(fileName);
    }

    private void loadFromFile(String fileName) {
        ArrayList<String> list = this.readFile(fileName);
        if (list == null || list.isEmpty()) {
            return;
        }
        HashMap<String, Pattern> newMap = new HashMap<String, Pattern>();
        for (String line : list) {
            newMap.put(line, Pattern.compile(line, Pattern.CASE_INSENSITIVE));
        }
        this.blockedWords = newMap;
    }

    /**
     * 包含屏蔽词，将会被替换
     * 
     * @param str
     * @return 
     */
    public String filter(String str) {
        if (this.containWord(str)) {
            str = this.filterWord(str, "*");
        }
        return str;
    }

    public boolean containWord(String normalized) {
        if (blockedWords == null || blockedWords.isEmpty()) {
            return false;
        }
        Set<String> keys = blockedWords.keySet();
        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
            String key = iterator.next();
            Pattern pattern = blockedWords.get(key);
            if (pattern.matcher(normalized).find()) {
                return true;
            }
        }
        for (int i = 0; i < badnum.length; i++) { // check bad num
            if (normalized.contains(badnum[i])) {
                return true;
            }
        }
        return false;
    }

    private String filterWord(String replaced, String mask) {
        if (blockedWords == null || blockedWords.isEmpty()) {
            return replaced;
        }
        Set<String> keys = blockedWords.keySet();
        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
            String key = iterator.next();
            Pattern pattern = blockedWords.get(key);
            Matcher matcher = pattern.matcher(replaced);
            if (matcher.find()) {
                replaced = matcher.replaceAll(mask);
            }
        }
        for (int i = 0; i < badnum.length; i++) {
            replaced = replaced.replace(badnum[i], "*");
        }
        return replaced;
    }

    private ArrayList<String> readFile(String fileName) {
        ArrayList<String> list = new ArrayList<String>();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader br = null;
        try {
            inputStream = ClassLoader.getSystemResourceAsStream(fileName);
//            inputStream = new FileInputStream(fileName);
            inputStreamReader = new InputStreamReader(inputStream, "UTF8");
            br = new BufferedReader(inputStreamReader);
            String temp;
            while ((temp = br.readLine()) != null) {
                list.add(temp.trim());
            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                logger.debug(e.getMessage());
            }
        }
        return list;
    }
}