package org.haulmont.dao;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "creditOffer")
public class CreditOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Credit credit;
    private long creditSum;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "creditOffer")
    private List<PaymentSchedule> paymentSchedule;
    private int creditTerm;
    private double totalPayments;
    private String creditConditionOnRegistrationMoment;

    public CreditOffer(User user, Credit credit, long creditSum, int creditTerm, String creditConditionOnRegistrationMoment) {
        this.user = user;
        this.credit = credit;
        this.creditSum = creditSum;
        this.creditTerm = creditTerm;
        this.creditConditionOnRegistrationMoment = creditConditionOnRegistrationMoment;
    }

    public CreditOffer() {
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public long getCreditSum() {
        return creditSum;
    }

    public void setCreditSum(long creditSum) {
        this.creditSum = creditSum;
    }

    public List<PaymentSchedule> getPaymentSchedule() {
        return paymentSchedule;
    }

    public void setPaymentSchedule(List<PaymentSchedule> paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }

    public int getCreditTerm() {
        return creditTerm;
    }

    public void setCreditTerm(int creditTerm) {
        this.creditTerm = creditTerm;
    }

    public double getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(double totalPayments) {
        this.totalPayments = totalPayments;
    }

    public String getCreditConditionOnRegistrationMoment() {
        return creditConditionOnRegistrationMoment;
    }

    public void setCreditConditionOnRegistrationMoment(String creditConditionOnRegistrationMoment) {
        this.creditConditionOnRegistrationMoment = creditConditionOnRegistrationMoment;
    }
}
