package memcached;

/**
 * 使用memcached范例
 * @author caoxin
 */
public class UseMemcached {
    
    private MemcachedClientService<String> memcachedClientService;
    private String name;
    private int age;
    
    public void init() {
        setNameUseMC();
        setAgeUseMC();
    }
    
    public void setNameUseMC() {
        name = memcachedClientService.get("name");
    }
    
    public void setAgeUseMC() {
        age = Integer.getInteger(memcachedClientService.get("age"));
    }

    @Override
    public String toString() {
        return "UseMemcached{" + "memcachedClientService=" + memcachedClientService + ", name=" + name + ", age=" + age + '}';
    }
    
    public void setMemcachedClinetService(MemcachedClientService memcachedClientService) {
        this.memcachedClientService = memcachedClientService;
    }
}
