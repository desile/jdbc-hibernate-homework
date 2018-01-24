package ru.hh.school.deposits;

import ru.hh.school.users.User;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "deposit")
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "open_timestamp")
    private Date openTimestamp;

    @Column(name = "expiration_timestamp")
    private Date expirationTimestamp;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "interest")
    private Double interest;

    @Column(name = "closed")
    private Boolean closed;


    public Deposit(User owner, Date openTimestamp, Date expirationTimestamp, Double amount, Double interest){
        this.owner = owner;
        this.openTimestamp = openTimestamp;
        this.expirationTimestamp = expirationTimestamp;
        this.amount = amount;
        this.interest = interest;
    }

    private Deposit(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Date getOpenTimestamp() {
        return openTimestamp;
    }

    public void setOpenTimestamp(Date openTimestamp) {
        this.openTimestamp = openTimestamp;
    }

    public Date getExpirationTimestamp() {
        return expirationTimestamp;
    }

    public void setExpirationTimestamp(Date expirationTimestamp) {
        this.expirationTimestamp = expirationTimestamp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Boolean isClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deposit deposit = (Deposit) o;

        return Objects.equals(id, deposit.id)
                && Objects.equals(owner, deposit.owner)
                && Objects.equals(openTimestamp, deposit.openTimestamp)
                && Objects.equals(expirationTimestamp, deposit.expirationTimestamp)
                && Objects.equals(interest, deposit.interest)
                && Objects.equals(amount, deposit.amount);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "\n\t id=" + id +
                "\n\t, owner=" + owner +
                "\n\t, openTimestamp=" + openTimestamp +
                "\n\t, expirationTimestamp=" + expirationTimestamp +
                "\n\t, amount=" + amount +
                "\n\t, interest=" + interest +
                "\n\t, closed=" + closed +
                '}';
    }
}
