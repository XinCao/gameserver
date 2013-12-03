package memcached;

import java.util.Date;
import java.util.Map;
import java.util.List;
import net.spy.memcached.MemcachedClient;

/**
 *
 * @author caoxin
 */
public class MemcachedClientService<T extends Object> {

    private MemcachedClient memcachedClient;

    /**
     *
     * @param key
     * @return
     */
    public T get(String key) {
        return (T) memcachedClient.get(key);
    }

    /**
     *
     * @param keys
     * @return
     */
    public Map<String, T> gets(String[] keys) {
        Object rs = memcachedClient.getBulk(keys);
        if (rs == null) {
            return null;
        }
        return (Map<String, T>) rs;
    }

    /**
     *
     * @param keys
     * @return
     */
    public Map<String, T> gets(List<String> keys) {
        return gets((String[]) (keys.toArray()));
    }

    /**
     *
     * @param key
     * @param o
     * @param expireTime
     */
    public void put(String key, T o, Date expireDate) {
        int expireTime = 24 * 60 * 60;
        if (expireDate != null) {
            expireTime = (int) ((expireDate.getTime() - System.currentTimeMillis()) / 1000.0);
        }
        memcachedClient.set(key, expireTime, (Object) o);
    }

    /**
     *
     * @param key
     * @param o
     */
    public void put(String key, T o) {
        put(key, o, null);
    }

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }
}