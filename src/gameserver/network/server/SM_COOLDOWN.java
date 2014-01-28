package gameserver.network.server;

import gameserver.model.IntPair;
import gameserver.model.player.Player;
import gameserver.service.CoolDownId;
import gameserver.service.GameTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import gameserver.network.core.BaseServerPacket;
import java.util.Map;
import org.apache.mina.core.buffer.IoBuffer;

/**
 *
 * @author caoxin
 */
public class SM_COOLDOWN extends BaseServerPacket {

    private List<IntPair> cds;

    @Override
    protected void writeImp(IoBuffer ioBuffer) {
        ioBuffer.putInt(cds.size());
        int curTime = GameTime.getInstance().currentTimeSecond();
        for (IntPair ip : cds) {
            ioBuffer.putInt(ip.param1());
            int ntime = ip.param2() - curTime;
            ioBuffer.putInt(ntime > 0 ? ntime : 0);
        }
    }

    public void init(Player player) {
        if (player == null) {
            return;
        }
        cds = new ArrayList<IntPair>();
        Map<CoolDownId, Integer> coolmap = player.getCoolManager().getCoolMap();
        Iterator<CoolDownId> it = coolmap.keySet().iterator();
        while (it.hasNext()) {
            CoolDownId id = it.next();
            if (id.isSync()) {
                int ctime = coolmap.get(id);
                cds.add(new IntPair(id.value(), ctime));
            }
        }
    }

    public void init(Player player, IntPair intPair) {
        this.cds = Collections.singletonList(intPair);
    }
}