package org.haulmont.controller;

import org.haulmont.dao.User;
import org.haulmont.repo.UserRepo;
import org.haulmont.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserRepo userRepo;
    private UserService userService;

    public UserController(UserRepo userRepo, UserService userService) {
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @GetMapping("/createUser")
    public String createUser() {
        return "createUser";
    }

    @PostMapping("/createUser")
    public String addUser(
            @RequestParam String firstname,
            @RequestParam String family,
            @RequestParam String lastname,
            @RequestParam String email,
            @RequestParam String phonenumber,
            @RequestParam String passport,
            RedirectAttributes attributes
    ) {
        User user = new User(firstname, family, lastname, email, phonenumber, passport);
        userRepo.save(user);
        attributes.addFlashAttribute("message", "Пользователь успешно создан!");
        return "redirect:/";
    }

    @GetMapping("/listOfUsers")
    public String getUsersList(Model model) {
        List<User> allUsers = userRepo.findAll();
        model.addAttribute("usersList", allUsers);
        return "listOfUsers";
    }

    @GetMapping("/editUser/{id}")
    public String editUser(Model model, @PathVariable UUID id, RedirectAttributes attributes) {
        try {
            User user = userRepo.findById(id).orElse(null);
            model.addAttribute("usertoedit", user);
            return "editUser";
        } catch (NullPointerException e) {
            attributes.addFlashAttribute("message", "Произошла ошибка! Пользователь не надйен!");
            return "redirect:/user/listOfUsers";
        }
    }

    @PostMapping("/editUser/{id}")
    public String editUser(
            @PathVariable UUID id,
            @RequestParam String firstname,
            @RequestParam String family,
            @RequestParam String lastname,
            @RequestParam String email,
            @RequestParam String phonenumber,
            @RequestParam String passport,
            RedirectAttributes attributes
    ) {
        User user = userRepo.findById(id).orElse(null);
        userService.saveEditUser(firstname, family, lastname, email, phonenumber, passport, user);
        attributes.addFlashAttribute("message", "Изменения успешно сохранены!");
        return "redirect:/";
    }

    @PostMapping("/deleteUser/{id}")
    public String deleteUser(
            @PathVariable UUID id,
            RedirectAttributes attributes
    ) {
        userRepo.deleteById(id);
        attributes.addFlashAttribute("message", "Пользователь успешно удален!");
        return "redirect:/";
    }

    @GetMapping("/viewUserData/{id}")
    public String viewUserData(
            @PathVariable UUID id,
            Model model,
            RedirectAttributes attributes
    ) {
        try {
            User user = userRepo.findById(id).orElse(null);
            model.addAttribute("user", user);
            return "viewUserData";
        } catch (NullPointerException e) {
            attributes.addFlashAttribute("message", "Произошла ошибка! Кредитное предложение не найдено!");
            return "redirect:/";
        }
    }
}
