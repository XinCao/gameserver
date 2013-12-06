package memcached;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.List;
import net.spy.memcached.MemcachedClient;

/**
 * memcached 服务器读取底层
 * 
 * @author caoxin
 */
public class MemcachedClientService<V extends Object & Serializable> {

    private MemcachedClient memcachedClient;

    /**
     * 获得键值
     * 
     * @param key
     * @return
     */
    public V get(String key) {
        return (V) memcachedClient.get(key);
    }

    /**
     * 获得多个键值
     * 
     * @param keys
     * @return
     */
    public Map<String, V> gets(String[] keys) {
        Object rs = memcachedClient.getBulk(keys);
        if (rs == null) {
            return null;
        }
        return (Map<String, V>) rs;
    }

    /**
     * 获得多个键值
     * 
     * @param keys
     * @return
     */
    public Map<String, V> gets(List<String> keys) {
        return gets((String[]) (keys.toArray()));
    }

    /**
     * 将键值对保存指定时间 
     * 
     * @param key
     * @param o
     * @param expireTime
     */
    public void put(String key, V o, Date expireDate) {
        int expireTime = 24 * 60 * 60;
        if (expireDate != null) {
            expireTime = (int) ((expireDate.getTime() - System.currentTimeMillis()) / 1000.0);
        }
        memcachedClient.set(key, expireTime, (Object) o);
    }

    /**
     *  键值对保存一天
     * 
     * @param key
     * @param o
     */
    public void put(String key, V o) {
        put(key, o, null);
    }

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }
}