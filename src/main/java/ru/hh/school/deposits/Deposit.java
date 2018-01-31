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

    @Column(name = "open_timestamp", nullable = false, updatable = false)
    private Date openTimestamp;

    @Column(name = "expiration_timestamp", nullable = false)
    private Date expirationTimestamp;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "interest", nullable = false)
    private double interest;

    @Column(name = "closed", nullable = false)
    private boolean closed;


    public Deposit(User owner, Date expirationTimestamp, double amount, double interest){
        this.owner = owner;
        this.openTimestamp = new Date();
        this.expirationTimestamp = expirationTimestamp;
        this.amount = amount;
        this.interest = interest;
    }

    private Deposit(){

    }

    public Integer id() {
        return id;
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

    public Date getExpirationTimestamp() {
        return expirationTimestamp;
    }

    public void setExpirationTimestamp(Date expirationTimestamp) {
        this.expirationTimestamp = expirationTimestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
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

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
