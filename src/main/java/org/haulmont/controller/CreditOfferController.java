package org.haulmont.controller;

import org.haulmont.dao.Credit;
import org.haulmont.dao.CreditOffer;
import org.haulmont.dao.User;
import org.haulmont.repo.CreditOfferRepo;
import org.haulmont.repo.CreditRepo;
import org.haulmont.repo.UserRepo;
import org.haulmont.service.CreditOfferService;
import org.haulmont.util.CreditsComparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/creditOffer")
public class CreditOfferController {
    private CreditOfferRepo creditOfferRepo;
    private CreditRepo creditRepo;
    private UserRepo userRepo;
    private CreditOfferService creditOfferService;
    private CreditsComparator creditsComparator = new CreditsComparator();

    public CreditOfferController(CreditOfferRepo creditOfferRepo, CreditRepo creditRepo, UserRepo userRepo, CreditOfferService creditOfferService) {
        this.creditOfferRepo = creditOfferRepo;
        this.creditRepo = creditRepo;
        this.userRepo = userRepo;
        this.creditOfferService = creditOfferService;
    }

    @GetMapping("/createCreditOfferForUser/{id}")
    public String createCreditOffer(
            Model model,
            @PathVariable UUID id,
            RedirectAttributes attributes
    ) {
        try {
            User user = userRepo.findById(id).orElse(null);
            model.addAttribute("user", user);
            List<Credit> credits = creditRepo.findCreditsByDeletedIsFalse();
            //Сортировка кредитов по банкам (по алфавиту)
            Collections.sort(credits, creditsComparator);
            model.addAttribute("credits", credits);
            return "createCreditOffer";
        } catch (NullPointerException e) {
            attributes.addFlashAttribute("message", "Произошла ошибка! Пользователь не найден!");
            return "redirect:/user/listOfUsers";
        }
    }

    @PostMapping("/createCreditOfferForUser/{id}")
    public String calculateCreditOffer(
            @PathVariable UUID id,
            @RequestParam UUID creditId,
            @RequestParam String sum,
            @RequestParam String term,
            Model model,
            RedirectAttributes attributes
    ) {
        try {
            User user = userRepo.findById(id).orElse(null);
            Credit credit = creditRepo.findById(creditId).orElse(null);
            int creditSum = Integer.parseInt(sum);
            int creditTerm = Integer.parseInt(term);
            if (creditSum > credit.getLimit()) {
                attributes.addFlashAttribute("message", "Введенная сумма больше лимита по кредиту!!!");
                return "redirect:/creditOffer/createCreditOfferForUser/" + id;
            }
            CreditOffer offer = creditOfferService.createCreditOffer(user, credit, creditSum, creditTerm);
            model.addAttribute("offer", offer);
            model.addAttribute("user", user);
        } catch (NumberFormatException e) {
            attributes.addFlashAttribute("message", "Вы ввели некорректные данные!");
            return "redirect:/creditOffer/createCreditOfferForUser/" + id;
        }
        return "confirmCreditOffer";
    }

    @PostMapping("/confirmCreditOffer/{id}")
    public String confirmCreditOffer(
            @PathVariable UUID id,
            RedirectAttributes attributes
    ) {
        attributes.addFlashAttribute("message", "Кредит успешно оформлен!");
        return "redirect:/";
    }

    @PostMapping("/cancelCreationCreditOffer/{id}")
    public String confirmCreditOffer(
            @PathVariable UUID id,
            @RequestParam UUID offerId
    ) {
        creditOfferRepo.deleteById(offerId);
        return "redirect:/creditOffer/createCreditOfferForUser/" + id;
    }

    @GetMapping("/listOfCreditOffersForUser/{id}")
    public String listOfCreditOffersForUser(
            @PathVariable UUID id,
            Model model,
            RedirectAttributes attributes
    ) {
        try {
            User user = userRepo.findById(id).orElse(null);
            List<CreditOffer> allCreditOfferForUser = creditOfferRepo.findAllByUser(user);
            model.addAttribute("creditOffers", allCreditOfferForUser);
            model.addAttribute("user", user);
            return "listOfCreditOffersForUser";
        } catch (NullPointerException e) {
            attributes.addFlashAttribute("message", "Произошла ошибка! Пользователь не найден!");
            return "redirect:/user/listOfUsers";
        }
    }

    @GetMapping("/listOfCreditOffersForUserAndBank/{bankId}/{id}")
    public String listOfCreditOffersForUserAndBank(
            @PathVariable UUID bankId,
            @PathVariable UUID id,
            Model model,
            RedirectAttributes attributes
    ) {
        try {
            User user = userRepo.findById(id).orElse(null);
            List<CreditOffer> allCreditOfferForUserAndBank = creditOfferRepo.findAllByUser(user)
                    .stream().filter(offer -> offer.getCredit().getBank().getId().equals(bankId))
                    .collect(Collectors.toList());
            model.addAttribute("creditOffers", allCreditOfferForUserAndBank);
            model.addAttribute("user", user);
            return "listOfCreditOffersForUser";
        } catch (NullPointerException e) {
            attributes.addFlashAttribute("message", "Произошла ошибка! Пользователь не найден!");
            return "redirect:/bank/getUsersListForBank/" + bankId;
        }
    }

    @GetMapping("/viewCreditOffer/{id}")
    public String viewCreditOffer(
            @PathVariable UUID id,
            Model model,
            RedirectAttributes attributes
    ) {
        try {
            CreditOffer offer = creditOfferRepo.findById(id).orElse(null);
            model.addAttribute("offer", offer);
            return "viewCreditOffer";
        } catch (NullPointerException e) {
            attributes.addFlashAttribute("message", "Произошла ошибка! Кредитное предложение не найдено!");
            return "redirect:/user/listOfUsers";
        }
    }

    @PostMapping("/deleteCreditOffer/{id}")
    public String deleteCreditOffer(
            @PathVariable UUID id,
            RedirectAttributes attributes
    ) {
        creditOfferService.deleteCreditOffer(id);
        attributes.addFlashAttribute("message", "Кредитное предложение успешно удалено!");
        return "redirect:/";
    }

}
