package ru.hh.school.jdbc.example;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class Currency {

    private Integer id;
    private String fullName;
    private String shortName;
    private Date updateTimestamp;
    private Float rateToDollar;


    public static Currency create(String fullName, String shortName, Date updateTimestamp, Float rateToDollar) {
        return new Currency(null , fullName, shortName, updateTimestamp, rateToDollar);
    }

    public static Currency existing(int id, String fullName, String shortName, Date updateTimestamp, Float rateToDollar) {
        return new Currency(id , fullName, shortName, updateTimestamp, rateToDollar);
    }

    // private constructor, only factory methods can use it
    private Currency(Integer id, String fullName, String shortName, Date updateTimestamp, Float rateToDollar) {
        this.id = id;
        this.fullName = fullName;
        this.shortName = shortName;
        this.updateTimestamp = updateTimestamp;
        this.rateToDollar = rateToDollar;
    }

    public Integer id() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Date getUpdateTimestamp() {
        Timestamp updateTime = new Timestamp(updateTimestamp.getTime());
        updateTime.setNanos(0);
        return updateTime;
    }

    public void setUpdateTimestamp(Date updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public Float getRateToDollar() {
        return rateToDollar;
    }

    public void setRateToDollar(Float rateToDollar) {
        this.rateToDollar = rateToDollar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Currency currency = (Currency) o;

        return Objects.equals(id, currency.id)
                && Objects.equals(fullName, currency.fullName)
                && Objects.equals(shortName, currency.shortName)
                && Objects.equals(getUpdateTimestamp(), currency.getUpdateTimestamp())
                && Objects.equals(rateToDollar, currency.rateToDollar);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}



