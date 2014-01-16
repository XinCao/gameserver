package log;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author liang
 */
public class LogEntry {

    private String channel;
    private List<String> fields = new ArrayList<String>();

    public LogEntry(String channel) {
        this.channel = channel;
    }

    public LogEntry(String channel, Object... args) {
        this.channel = channel;
        this.addFields(args);
    }

    public final void addFields(Object... args) {
        for (Object f : args) {
            this.fields.add(f == null ? "(NULL)" : f.toString());
        }
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        int i = 0;

        buffer.append(channel);
        buffer.append('\t');

        for (String field : fields) {
            buffer.append(field);

            i++;
            if (i < fields.size()) {
                buffer.append('\t');
            }
        }

        return buffer.toString();
    }
}