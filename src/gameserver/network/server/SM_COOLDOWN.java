package gameserver.network.server;

import gameserver.model.Int3;
import gameserver.model.player.Player;
import gameserver.service.CoolDownId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import gameserver.network.core.BaseServerPacket;
import gameserver.network.core.PacketKind;
import gameserver.service.CoolDownManager.CoolDownInfo;
import java.util.Map;
import org.apache.mina.core.buffer.IoBuffer;

/**
 *
 * @author caoxin
 */
public class SM_COOLDOWN extends BaseServerPacket {

    private List<Int3> cds;

    public SM_COOLDOWN(Player player) {
        super(PacketKind.SM_COOLDOWN);
        if (player == null) {
            return;
        }
        cds = new ArrayList<Int3>();
        Map<CoolDownId, CoolDownInfo> coolmap = player.getCoolManager().getCoolMap();
        Iterator<CoolDownId> it = coolmap.keySet().iterator();
        while (it.hasNext()) {
            CoolDownId id = it.next();
            if (id.isSync()) {
                CoolDownInfo cdi = coolmap.get(id);
                cds.add(new Int3(id.count(), cdi.cur, cdi.interval));
            }
        }
    }

    public SM_COOLDOWN(Player player, Int3 int3) {
        super(PacketKind.SM_COOLDOWN);
        this.cds = Collections.singletonList(int3);
    }

    @Override
    protected void writeImp(IoBuffer ioBuffer) {
        ioBuffer.putInt(cds.size());
        for (Int3 int3 : cds) {
            ioBuffer.putInt(int3.param1());
            ioBuffer.putInt(int3.param2());
            ioBuffer.putInt(int3.param3());
        }
    }
}