package org.haulmont.service;

import org.haulmont.dao.User;
import org.haulmont.repo.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void saveEditUser(
            String firstname,
            String family,
            String lastname,
            String email,
            String phonenumber,
            String passport,
            User user
    ) {
        user.setFirstname(firstname);
        user.setFamily(family);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPhonenumber(phonenumber);
        user.setPassport(passport);
        userRepo.save(user);
    }
}
