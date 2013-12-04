package dao;

import dao.sql.SqlSupport;
import dao.sql.SqlType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

/**
 *
 * @author caoxin
 */
public class SimpleDao extends NamedParameterJdbcDaoSupport {

    private SqlSupport<SimpleDao> sqlSupport;
    private String insertSql  = sqlSupport.getSqlInCachedPool(this, SqlType.insertSql);

    public void insert() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("account", "jingxin191314@foxmail.com");
        paramsMap.put("show_name", "caoxin");
        paramsMap.put("role_name", "admin");
        paramsMap.put("create_date", df.format(new Date()));
        paramsMap.put("is_valied", "1");
        getNamedParameterJdbcTemplate().update(insertSql, paramsMap);
    }

    public static void main(String... args) {
        ApplicationContext ac = new FileSystemXmlApplicationContext("./config/app.xml");
        SimpleDao simpleDao = ac.getBean(SimpleDao.class);
        simpleDao.insert();
    }
}