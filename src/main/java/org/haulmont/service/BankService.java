package org.haulmont.service;

import org.haulmont.dao.Bank;
import org.haulmont.repo.BankRepo;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BankService {
    private BankRepo bankRepo;

    public BankService(BankRepo bankRepo) {
        this.bankRepo = bankRepo;
    }

    public void saveEditBank(
            String name,
            Bank bank
    ) {
        bank.setName(name);
        bankRepo.save(bank);
    }

    public void deleteBank(UUID bankId) {
        Bank bank = bankRepo.findById(bankId).orElse(null);
        bankRepo.delete(bank);
    }
}
