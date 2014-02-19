package gameserver.service;

import gameserver.dao.CountMapper;
import gameserver.model.Count;
import gameserver.service.CountManager.CountInfo;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author caoxin
 */
public class CountService {

    private CountMapper countMapper;
    
    public CountManager loadCountManager(CountManager countManager, Count count) {
        List<Count> countList = loadCountList(count);
        Map<CountId, CountInfo> countMap = countManager.getCountMap();
        for (Count c : countList) {
            countMap.put(CountId.fromInt(c.getCount()), new CountInfo(c.getCur(), c.getMax()));
        }
        return countManager;
    }

    public List<Count> loadCountList(Count count) {
        return countMapper.selectCountList(count);
    }

    public Count loadCount(Count count) {
        return countMapper.selectCount(count);
    }
    
    public Count loadCountByPlayerIdAndCountId(int playerId, CountId countId) {
        Count count = new Count();
        count.setPlayerId(playerId);
        count.setCount(countId.count());
        return loadCount(count);
    }
    
    public void saveCountManager(CountManager countManager, Count count) {
        int playerId = count.getPlayerId();
        Set<Entry<CountId, CountInfo>> entry = countManager.getCountMap().entrySet();
        for (Entry<CountId, CountInfo> e : entry) {
            if (!e.getKey().saveToDB) {
                continue;
            }
            Count c = new Count();
            c.setPlayerId(playerId);
            c.setCount(e.getKey().count());
            c.setCur(e.getValue().cur);
            c.setMax(e.getValue().max);
            countMapper.updateCount(count);
        }
    }
    
    public void saveCount(Count c) {
        countMapper.updateCount(c);
    }

    public void setCountMapper(CountMapper countMapper) {
        this.countMapper = countMapper;
    }
}