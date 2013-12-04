package dao.sql;

/**
 *
 * @author caoxin
 */
public class SqlSupport<T> {

    private SqlCachedContainer sqlCachedContainer;

    public String getSqlInCachedPool(T o, String flag) {
        return sqlCachedContainer.getSql(o, flag);
    }

    public void putSqlInCachedPool(T o, String flag, String sql) {
        sqlCachedContainer.putSql(o, flag, sql);
    }

    public void setSqlCachedContainer(SqlCachedContainer sqlCachedContainer) {
        this.sqlCachedContainer = sqlCachedContainer;
    }
}