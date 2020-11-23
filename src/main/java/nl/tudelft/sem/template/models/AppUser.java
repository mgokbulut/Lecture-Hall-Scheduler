package nl.tudelft.sem.template.models;


import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AppUser")
public class AppUser {
    @Id
    @Column(name = "netId")
    private String netID;
    @Column(name = "hashedPassword")
    private String password;

    public AppUser(String netID, String password) {
        this.netID = netID;
        this.password = password;
    }
    public AppUser() {

    }

    public String getNetID() {
        return netID;
    }

    public void setNetID(String netID) {
        this.netID = netID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser user = (AppUser) o;
        return Objects.equals(netID, user.netID) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(netID, password);
    }
    //    @ManyToOne
//    @JoinColumn(name = "role", referencedColumnName = "id")
//    private Role role;
}