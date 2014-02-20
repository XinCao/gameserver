package gameserver.service;

import gameserver.dao.CoolDownMapper;
import gameserver.model.CoolDown;
import gameserver.service.CoolDownManager.CoolDownInfo;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author caoxin
 */
public class CoolDownService {

    private CoolDownMapper coolDownMapper;
    
    /**
     * 加载存盘冷却
     * 
     * @param coolDownManager
     * @param cooldown
     * @return 
     * 
     */
    public CoolDownManager loadCoolDownManager(CoolDownManager coolDownManager, CoolDown cooldown) {
        List<CoolDown> cooldownList = this.loadCoolDownList(cooldown);
        Map<CoolDownId, CoolDownInfo> coolMap = coolDownManager.getCoolMap();
        for (CoolDown c : cooldownList) {
            coolMap.put(CoolDownId.fromInt(c.getCount()), new CoolDownInfo(c.getCur(), c.getInterval()));
        }
        return coolDownManager;
    }
    
    /**
     * 保存存盘的冷却
     * 
     * @param coolDownManager
     * @param cooldown 
     */
    public void saveCoolDownManager(CoolDownManager coolDownManager, CoolDown cooldown) {
        int playerId = cooldown.getPlayerId();
        Set<Entry<CoolDownId, CoolDownInfo>> entry = coolDownManager.getCoolMap().entrySet();
        for (Entry<CoolDownId, CoolDownInfo> e : entry) {
            if (!e.getKey().isSave()) {
                continue;
            }
            CoolDown c = new CoolDown();
            c.setPlayerId(playerId);
            c.setCount(e.getKey().count());
            c.setCur(e.getValue().cur);
            c.setInterval(e.getValue().interval);
            coolDownMapper.updateCoolDown(c);
        }
    }
    
    public List<CoolDown> loadCoolDownList(CoolDown cooldown) {
        return coolDownMapper.selectCoolDownList(cooldown);
    }
    
    public void setCoolDownMapper(CoolDownMapper coolDownMapper) {
        this.coolDownMapper = coolDownMapper;
    }
}