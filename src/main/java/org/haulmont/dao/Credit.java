package org.haulmont.dao;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "credit")
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private long limit;
    private double interestRate;
    private boolean deleted;
    @ManyToOne
    private Bank bank;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "credit")
    private List<CreditOffer> creditOffers;
    public Credit(String name, long limit, double interestRate, Bank bank) {
        this.name = name;
        this.limit = limit;
        this.interestRate = interestRate;
        this.bank = bank;
        this.deleted = false;
    }

    public Credit() {
        this.deleted = false;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<CreditOffer> getCreditOffers() {
        return creditOffers;
    }

    public void setCreditOffers(List<CreditOffer> creditOffers) {
        this.creditOffers = creditOffers;
    }
}
