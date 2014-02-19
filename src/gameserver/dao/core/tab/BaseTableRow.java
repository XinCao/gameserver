package gameserver.dao.core.tab;

import java.lang.reflect.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author caoxin
 */
public abstract class BaseTableRow {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public abstract int getId();

    @Override
    public String toString() {
        String str = "{";
        Field[] fields = this.getClass().getFields();
        for (Field field : fields) {
            Object value = null;
            try {
                value = field.get(this);
            } catch (Exception ex) {
                logger.debug(ex.getMessage());
            }
            String valueStr = value == null ? "<NULL>" : value.toString();
            str += field.getName() + "=" + valueStr + ", ";
        }
        str += "}";
        return str;
    }
}