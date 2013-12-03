package memcached;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 使用memcached范例（使用memcached保存临时数据）
 *
 * @author caoxin
 */
public class UseMemcached<T extends Object> {

    private MemcachedClientService<String> memcachedClientService;

    public void put(T o, String key, String value, Date expireDate) {
        String clazzName = o.getClass().getSimpleName();
        memcachedClientService.put(clazzName + key, value, expireDate);
    }

    public void put(T o, String key, String value) {
        put(o, key, value, null);
    }

    public String get(T o, String key) {
        String clazzName = o.getClass().getSimpleName();
        return memcachedClientService.get(clazzName + key);
    }

    public Map<String, String> get(T o, List<String> keys) {
        return memcachedClientService.gets(keys);
    }

    public void setMemcachedClinetService(MemcachedClientService memcachedClientService) {
        this.memcachedClientService = memcachedClientService;
    }
}