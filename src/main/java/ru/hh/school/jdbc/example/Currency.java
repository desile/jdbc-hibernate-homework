package ru.hh.school.jdbc.example;

import java.util.Date;

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
        return updateTimestamp;
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

        if (id != null ? !id.equals(currency.id) : currency.id != null) return false;
        if (fullName != null ? !fullName.equals(currency.fullName) : currency.fullName != null) return false;
        if (shortName != null ? !shortName.equals(currency.shortName) : currency.shortName != null) return false;
        if (updateTimestamp != null ? !updateTimestamp.equals(currency.updateTimestamp) : currency.updateTimestamp != null)
            return false;
        return rateToDollar != null ? rateToDollar.equals(currency.rateToDollar) : currency.rateToDollar == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}



