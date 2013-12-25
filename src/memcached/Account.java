package memcached;

import java.io.Serializable;

/**
 *
 * @author caoxin
 */
public class Account implements Serializable {

    private int id;
    private String name;
    private String showName;
    private String birthdy;
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getBirthdy() {
        return birthdy;
    }

    public void setBirthdy(String birthdy) {
        this.birthdy = birthdy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Account{" + "id=" + id + ", name=" + name + ", showName=" + showName + ", birthdy=" + birthdy + ", address=" + address + '}';
    }
}