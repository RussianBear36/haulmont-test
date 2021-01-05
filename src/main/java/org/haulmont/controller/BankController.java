package org.haulmont.controller;

import org.haulmont.dao.Bank;
import org.haulmont.dao.User;
import org.haulmont.repo.BankRepo;
import org.haulmont.service.BankService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/bank")
public class BankController {
    private BankRepo bankRepo;
    private BankService bankService;

    public BankController(BankRepo bankRepo, BankService bankService) {
        this.bankRepo = bankRepo;
        this.bankService = bankService;
    }

    @GetMapping("/createBank")
    public String createBank() {
        return "createBank";
    }

    @PostMapping("/createBank")
    public String createBank(
            @RequestParam String name,
            RedirectAttributes attributes
    ) {
        Bank bank = new Bank(name);
        bankRepo.save(bank);
        attributes.addFlashAttribute("message", "Банк успешно создан!");
        return "redirect:/";
    }

    @GetMapping("/listOfBanks")
    public String getUsersList(Model model) {
        List<Bank> allBanks = bankRepo.findAll();
        model.addAttribute("banksList", allBanks);
        return "listOfBanks";
    }

    @GetMapping("/editBank/{id}")
    public String editBank(Model model, @PathVariable UUID id, RedirectAttributes attributes) {
        try {
            Bank bank = bankRepo.findById(id).orElse(null);
            model.addAttribute("bank", bank);
            return "editBank";
        } catch (NullPointerException e) {
            attributes.addFlashAttribute("message", "Произошла ошибка! Банк не найден!");
            return "redirect:/bank/listOfBanks";
        }
    }

    @PostMapping("/editBank/{id}")
    public String editBank(
            @PathVariable UUID id,
            @RequestParam String name,
            RedirectAttributes attributes
    ) {
        Bank bank = bankRepo.findById(id).orElse(null);
        bankService.saveEditBank(name, bank);
        attributes.addFlashAttribute("message", "Банк успешно изменен!");
        return "redirect:/";
    }

    @GetMapping("/listOfCreditSpecificBank/{id}")
    public String listOfCreditForThisBank(Model model, @PathVariable UUID id, RedirectAttributes attributes) {
        try {
            Bank bank = bankRepo.findById(id).orElse(null);
            model.addAttribute("bankName", bank.getName());
            model.addAttribute("creditsList", bank.getCreditList());
            return "listOfCreditsForSpecificBank";
        } catch (NullPointerException e) {
            attributes.addFlashAttribute("message", "Произошла ошибка! Банк не найден!");
            return "redirect:/bank/listOfBanks";
        }
    }

    @PostMapping("/deleteBank/{id}")
    public String deleteBank(
            @PathVariable UUID id,
            RedirectAttributes attributes
    ) {
        bankService.deleteBank(id);
        attributes.addFlashAttribute("message", "Банк успешно удален!");
        return "redirect:/";
    }

    @GetMapping("/getUsersListForBank/{id}")
    public String getUsersListForBank(
            @PathVariable UUID id,
            Model model,
            RedirectAttributes attributes
    ) {
        try {
            Bank bank = bankRepo.findById(id).orElse(null);
            Set<User> users = bank.getClientList();
            model.addAttribute("clientList", users);
            model.addAttribute("bank", bank);
            return "listOfUsersForBank";
        } catch (NullPointerException e) {
            attributes.addFlashAttribute("message", "Произошла ошибка! Банк не найден!");
            return "redirect:/bank/listOfBanks";
        }
    }

}
