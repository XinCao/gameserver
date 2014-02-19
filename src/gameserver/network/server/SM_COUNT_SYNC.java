package gameserver.network.server;

import gameserver.model.Int3;
import gameserver.model.player.Player;
import gameserver.service.CountId;
import gameserver.service.CountManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import gameserver.network.core.BaseServerPacket;
import gameserver.network.core.PacketKind;
import org.apache.mina.core.buffer.IoBuffer;

/**
 *
 * @author caoxin
 */
public class SM_COUNT_SYNC extends BaseServerPacket {

    private List<Int3> cds;

    public SM_COUNT_SYNC(Int3 int3) {
        super(PacketKind.SM_COUNT_SYNC);
        this.cds = Collections.singletonList(int3);
    }

    public SM_COUNT_SYNC(Player player) {
        super(PacketKind.SM_COUNT_SYNC);
        if (player == null) {
            return;
        }
        cds = new ArrayList<Int3>();
        Map<CountId, CountManager.CountInfo> countmap = player.getCountManager().getCountMap();
        Iterator<CountId> it = countmap.keySet().iterator();
        while (it.hasNext()) {
            CountId id = it.next();
            if (id.isSync()) {
                cds.add(new Int3(id.count(), countmap.get(id).cur, countmap.get(id).max));
            }
        }
    }

    @Override
    protected void writeImp(IoBuffer ioBuffer) {
        ioBuffer.putInt(cds.size());
        for (Int3 ip : cds) {
            ioBuffer.putInt(ip.param1());
            ioBuffer.putInt(ip.param2());
            ioBuffer.putInt(ip.param3());
        }
    }
}