package org.haulmont.controller;

import org.haulmont.dao.Bank;
import org.haulmont.dao.Credit;
import org.haulmont.repo.BankRepo;
import org.haulmont.repo.CreditRepo;
import org.haulmont.service.CreditService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/credit")
public class CreditController {

    private CreditRepo creditRepo;
    private BankRepo bankRepo;
    private CreditService creditService;

    public CreditController(CreditRepo creditRepo, BankRepo bankRepo, CreditService creditService) {
        this.creditRepo = creditRepo;
        this.bankRepo = bankRepo;
        this.creditService = creditService;
    }

    @GetMapping("/createCredit")
    public String createCredit(Model model) {
        List<Bank> allBank = bankRepo.findAll();
        model.addAttribute("bankList", allBank);
        return "createCredit";
    }

    @PostMapping("/createCredit")
    public String createCredit(
            @RequestParam String name,
            @RequestParam String limit,
            @RequestParam String interestRate,
            @RequestParam UUID bank,
            RedirectAttributes attributes
    ) {
        interestRate = interestRate.replace(',', '.');
        try {
            creditService.createCredit(name, limit, interestRate, bank);
        } catch (NumberFormatException e) {
            attributes.addFlashAttribute("message", "Вы ввели некорректные данные! Попробуйте еще раз!");
            return "redirect:/credit/createCredit";
        }
        attributes.addFlashAttribute("message", "Кредит успешно создан!");
        return "redirect:/";
    }

    @GetMapping("/editCredit/{id}")
    public String editCredit(
            @PathVariable UUID id,
            Model model,
            RedirectAttributes attributes
    ) {
        try {
            Credit credit = creditRepo.findById(id).orElse(null);
            model.addAttribute("credit", credit);
            return "editCredit";
        } catch (NullPointerException e) {
            attributes.addFlashAttribute("message", "Произошла ошибка! Кредит не найден!");
            return "redirect:/credit/listOfCredits";
        }
    }

    @PostMapping("/editCredit/{id}")
    public String editCredit(
            @PathVariable UUID id,
            @RequestParam String name,
            @RequestParam String limit,
            @RequestParam String interestRate,
            RedirectAttributes attributes
    ) {
        Credit credit = creditRepo.findById(id).orElse(null);
        creditService.editCredit(credit, name, limit, interestRate);
        attributes.addFlashAttribute("message", "Кредит успешно изменен!");
        return "redirect:/";
    }

    @GetMapping("/listOfCredits")
    public String getUsersList(Model model) {
        List<Credit> allCredits = creditRepo.findCreditsByDeletedIsFalse();
        model.addAttribute("creditsList", allCredits);
        return "listOfCredits";
    }

    @PostMapping("/deleteCredit/{id}")
    public String deleteCredit(
            @PathVariable UUID id,
            RedirectAttributes attributes
    ) {
        try {
            Credit credit = creditRepo.findById(id).orElse(null);
            creditService.deleteCredit(credit);
            attributes.addFlashAttribute("message", "Кредит успешно удален!");
            return "redirect:/";
        } catch (NullPointerException e) {
            attributes.addFlashAttribute("message", "Произошла ошибка! Кредит не найден!");
            return "redirect:/credit/listOfCredits";
        }
    }

}
