package gameserver.dao;

import gameserver.model.CoolDown;
import java.util.List;

/**
 *
 * @author caoxin
 */
public interface CoolDownMapper {

    public void insertCoolDown(CoolDown coolDown);

    public void deleteCoolDown(CoolDown coolDown);

    public void updateCoolDown(CoolDown coolDown);

    public CoolDown selectCoolDown(CoolDown coolDown);

    public List<CoolDown> selectCoolDownList(CoolDown coolDown);
}