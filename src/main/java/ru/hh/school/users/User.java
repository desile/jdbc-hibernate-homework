package ru.hh.school.users;

import ru.hh.school.deposits.Deposit;

import javax.persistence.*;
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

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "creation_timestamp", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Deposit> deposits;

    @Column(name = "deleted")
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

    // no setId, Hibernate uses reflection to set field

    public String firstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String lastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date creationDate() {
        return creationDate;
    }

    public Set<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(Set<Deposit> deposits) {
        this.deposits = deposits;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    // no setCreationDate

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;

        User thatUser = (User) that;
        return Objects.equals(id, thatUser.id)
                && Objects.equals(creationDate, thatUser.creationDate)
                && Objects.equals(firstName, thatUser.firstName)
                && Objects.equals(lastName, thatUser.lastName);
                //&& Objects.equals(deposits, thatUser.deposits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate);
    }

    @Override
    public String toString() {
        return String.format("%s{\n\tid=%d,\n\t firstName='%s',\n\t lastName='%s',\n\t creationDate='%s', \n\t deposits='%s'\n}",
                getClass().getSimpleName(), id, firstName, lastName, creationDate, deposits);
    }
}
