package dao;

import dao.core.BaseDaoSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author caoxin
 */
public class SimpleDao extends BaseDaoSupport {

    private static String insertSql = "insert";
    private static String deleteSql = "delete";
    private static String updateSql = "update";
    private static String selectSql = "select";

    public void Simple() {
        insertSql = sqlSupport.getSqlInCachedPool(this, insertSql);
        deleteSql = sqlSupport.getSqlInCachedPool(this, deleteSql);
        updateSql = sqlSupport.getSqlInCachedPool(this, updateSql);
        selectSql = sqlSupport.getSqlInCachedPool(this, selectSql);
    }

    public void insert() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("account", "jingxin191314@foxmail.com");
        paramsMap.put("show_name", "caoxin");
        paramsMap.put("role_name", "admin");
        paramsMap.put("create_date", df.format(new Date()));
        paramsMap.put("is_valied", "1");
        this.namedParameterJdbcTemplate.update(insertSql, paramsMap);
    }

    public static void main(String... args) {
        ApplicationContext ac = new FileSystemXmlApplicationContext("./config/app.xml");
        SimpleDao simpleDao = ac.getBean(SimpleDao.class);
        simpleDao.insert();
    }
}