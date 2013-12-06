package memcached;

/**
 *
 * @author caoxin
 */
public class AccountUseMemcached {

    private UseMemcached<AccountUseMemcached, Account> useMemcached;

    public void putAccount(Account account) {
        useMemcached.put(this, account.getName(), account);
    }

    public Account getAccount(String name) {
        return useMemcached.get(this, name);
    }

    public UseMemcached getUseMemcached() {
        return useMemcached;
    }

    public void setUseMemcached(UseMemcached useMemcached) {
        this.useMemcached = useMemcached;
    }
}