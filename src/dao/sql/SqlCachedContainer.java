package dao.sql;

import java.util.Map;
import javolution.util.FastMap;

/**
 *
 * Sql 缓存容器
 *
 * @author caoxin
 */
public class SqlCachedContainer<C> {

    private final Map<String, Map<String, String>> cachedSql = new FastMap<String, Map<String, String>>().shared();

    /**
     * 读取sql
     *
     * @param c
     * @param flag
     * @return
     */
    public String getSql(C c, String flag) {
        Map<String, String> classSqlMap = getClassSqlMap(c);
        String sql = null;
        if (classSqlMap.containsKey(flag)) {
            sql = classSqlMap.get(flag);
        }
        return sql;
    }

    /**
     * 存放sql
     *
     * @param c
     * @param flag
     * @param sql
     */
    public void putSql(C c, String flag, String sql) {
        Map<String, String> classSqlMap = getClassSqlMap(c);
        classSqlMap.put(flag, sql);
    }

    private Map<String, String> getClassSqlMap(C c) {
        String className = c.getClass().getName();
        if (!this.cachedSql.containsKey(className)) {
            Map<String, String> classSqlMap = new FastMap<String, String>();
            cachedSql.put(className, classSqlMap);
        }
        return cachedSql.get(className);
    }
}