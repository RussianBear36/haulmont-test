package org.haulmont.dao;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class PaymentSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String date;
    private double amountOfPayment;
    private double interestAmount;
    private double loanBodyAmount;
    @ManyToOne
    private CreditOffer creditOffer;

    public PaymentSchedule(String date, double amountOfPayment, double interestAmount, double loanBodyAmount, CreditOffer creditOffer) {
        this.date = date;
        this.amountOfPayment = amountOfPayment;
        this.interestAmount = interestAmount;
        this.loanBodyAmount = loanBodyAmount;
        this.creditOffer = creditOffer;
    }

    public PaymentSchedule() {
    }

    public UUID getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmountOfPayment() {
        return amountOfPayment;
    }

    public void setAmountOfPayment(double amountOfPayment) {
        this.amountOfPayment = amountOfPayment;
    }

    public double getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(double interestAmount) {
        this.interestAmount = interestAmount;
    }

    public double getLoanBodyAmount() {
        return loanBodyAmount;
    }

    public void setLoanBodyAmount(double loanBodyAmount) {
        this.loanBodyAmount = loanBodyAmount;
    }

    public CreditOffer getCreditOffer() {
        return creditOffer;
    }

    public void setCreditOffer(CreditOffer creditOffer) {
        this.creditOffer = creditOffer;
    }
}
