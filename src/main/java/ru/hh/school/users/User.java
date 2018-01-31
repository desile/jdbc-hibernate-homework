package ru.hh.school.users;

import ru.hh.school.deposits.Deposit;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "creation_timestamp", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Deposit> deposits;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.deposits = new HashSet<>();
        this.creationDate = new Date();
        this.deleted = false;
    }

    private User(){

    }

    public Integer id() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreationDate() {
        return new Timestamp(creationDate.getTime());
    }

    public Set<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(Set<Deposit> deposits) {
        this.deposits = deposits;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;

        User thatUser = (User) that;
        return Objects.equals(id, thatUser.id)
                && Objects.equals(getCreationDate(), thatUser.getCreationDate())
                && Objects.equals(firstName, thatUser.firstName)
                && Objects.equals(lastName, thatUser.lastName)
                && Objects.equals(deposits, thatUser.deposits);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return String.format("%s{\n\tid=%d,\n\t firstName='%s',\n\t lastName='%s',\n\t creationDate='%s', \n\t deposits='%s'\n}",
                getClass().getSimpleName(), id, firstName, lastName, creationDate, deposits);
    }
}
