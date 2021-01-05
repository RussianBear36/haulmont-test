package org.haulmont.controller;

import org.haulmont.dao.User;
import org.haulmont.repo.CreditRepo;
import org.haulmont.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.sql.DataSource;
import java.sql.SQLException;

@Controller
public class MainController {

    @GetMapping("/")
    public String getMainPage(){
        return "index";
    }
}
