package org.haulmont.service;

import org.haulmont.dao.Bank;
import org.haulmont.dao.Credit;
import org.haulmont.repo.BankRepo;
import org.haulmont.repo.CreditRepo;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreditService {

    private CreditRepo creditRepo;
    private BankRepo bankRepo;

    public CreditService(CreditRepo creditRepo, BankRepo bankRepo) {
        this.creditRepo = creditRepo;
        this.bankRepo = bankRepo;
    }

    public void createCredit(String name, String limit, String interestRate, UUID bank) throws NumberFormatException {
        Credit credit = new Credit(name, Long.parseLong(limit), Double.parseDouble(interestRate), bankRepo.findById(bank).orElse(null));
        creditRepo.save(credit);
        Bank thisBank = bankRepo.findById(bank).orElse(null);
        thisBank.getCreditList().add(credit);
        bankRepo.save(thisBank);
    }

    public void editCredit(Credit credit, String name, String limit, String interestRate) {
        credit.setName(name);
        credit.setLimit(Long.parseLong(limit));
        credit.setInterestRate(Double.parseDouble(interestRate));
        creditRepo.save(credit);
    }

    public void deleteCredit(Credit credit) {
        credit.setDeleted(true);
        creditRepo.save(credit);
    }
}
