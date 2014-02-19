package gameserver.dao;

import gameserver.model.Count;
import java.util.List;

/**
 *
 * @author caoxin
 */
public interface CountMapper {

    public void insertCount(Count count);

    public void deleteCount(Count count);

    public void updateCount(Count count);

    public Count selectCount(Count count);
    
    public List<Count> selectCountList(Count count);
}