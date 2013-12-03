package dao.core;

import dao.sql.SqlSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * @author caoxin
 */
public class BaseDaoSupport {
    
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    protected SqlSupport sqlSupport;
    
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    public void setSqlSupport(SqlSupport sqlSupport) {
        this.sqlSupport = sqlSupport;
    }
}
