package org.haulmont.service;

import org.haulmont.dao.*;
import org.haulmont.repo.BankRepo;
import org.haulmont.repo.CreditOfferRepo;
import org.haulmont.repo.CreditRepo;
import org.haulmont.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CreditOfferService {
    private CreditOfferRepo creditOfferRepo;
    private CreditRepo creditRepo;
    private BankRepo bankRepo;
    private UserRepo userRepo;
    private PaymentScheduleService paymentScheduleService;

    public CreditOfferService(CreditOfferRepo creditOfferRepo, CreditRepo creditRepo, BankRepo bankRepo, PaymentScheduleService paymentScheduleService, UserRepo userRepo) {
        this.creditOfferRepo = creditOfferRepo;
        this.creditRepo = creditRepo;
        this.bankRepo = bankRepo;
        this.paymentScheduleService = paymentScheduleService;
        this.userRepo = userRepo;
    }

    public CreditOffer createCreditOffer(User user, Credit credit, int sum, int term) {

        String condition = "Банк: " + credit.getBank().getName() + "; Кредит: " + credit.getName() +
                "; Лимит: " + credit.getLimit() + "; Процентная ставка: " + credit.getInterestRate();

        CreditOffer offer = new CreditOffer(user, credit, sum, term, condition);
        List<PaymentSchedule> list = paymentScheduleService.calculatePaymentSchedule(credit.getInterestRate(), offer, sum, term);
        offer.setPaymentSchedule(list);

        Bank bank = bankRepo.findById(credit.getBank().getId()).orElse(null);
        bank.getClientList().add(user);
        bankRepo.save(bank);

        offer = creditOfferRepo.save(offer);
        user.getBanksList().add(bank);
        user.getCreditOffers().add(offer);
        userRepo.save(user);
        credit.getCreditOffers().add(offer);
        creditRepo.save(credit);

        return offer;
    }

    public void deleteCreditOffer(UUID id) {
        CreditOffer offer = creditOfferRepo.findById(id).orElse(null);
        creditOfferRepo.deleteById(id);
        Bank offerBank = offer.getCredit().getBank();
        User user = offer.getUser();
        user.getCreditOffers().remove(offer);
        for (CreditOffer o : user.getCreditOffers()) {
            if (o.getCredit().getBank().equals(offerBank)) {
                return;
            }
        }
        offerBank.getClientList().remove(user);
        bankRepo.save(offerBank);
        userRepo.save(user);
    }

}
