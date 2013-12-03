package dao.sql;

/**
 *
 * @author caoxin
 */
public class SqlSupport <T extends Object>{
    
    private SqlCachedContainer sqlCachedContainer;
     
    public String getSqlInCachedPool(T o, String flag) {
        return sqlCachedContainer.getSql(o, flag);
    }
    
    public void putSqlInCachedPool(T o, String flag, String sql) {
        sqlCachedContainer.putSql(o, flag, sql);
    }

    public SqlCachedContainer getSqlCachedContainer() {
        return sqlCachedContainer;
    }

    public void setSqlCachedContainer(SqlCachedContainer sqlCachedContainer) {
        this.sqlCachedContainer = sqlCachedContainer;
    }
}
