package log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

/**
 *
 * @author caoxin
 */
public class LogServiceImplementWithLogback implements LogService {

    private static final Logger logger = LoggerFactory.getLogger(LogService.class);

    @Override
    public void save_item_log(int playerId, String action, int itemTplId, long amount, int channel, String extraData) {
        LogEntry entry = new LogEntry(
                "ITEM",
                playerId,
                action,
                itemTplId,
                amount,
                channel,
                extraData);

        logger.info(MarkerFactory.getMarker("GAME_EVENT.ITEM"), entry.toString());
    }
}