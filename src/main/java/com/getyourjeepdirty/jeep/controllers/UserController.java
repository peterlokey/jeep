package com.getyourjeepdirty.jeep.controllers;

import com.getyourjeepdirty.jeep.models.data.UserDao;
import com.getyourjeepdirty.jeep.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "Sign Up");
        return "user/sign-up";
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String signUp(Model model, @ModelAttribute User user, String verify, HttpServletRequest request) {
        boolean hasErrors = false;
        //verify first name length
        int len = user.getFirstName().length();
        if (len < 2 || len > 16) {
            model.addAttribute("firstNameError", "First name must be between 2 and 16 characters");
            hasErrors = true;
        }
        //verify last name length
        int lastLen = user.getLastName().length();
        if (lastLen < 2 || lastLen > 16) {
            model.addAttribute("lastNameError", "Last name must be between 2 and 16 characters");
            hasErrors = true;
        }

        //verify email is available
        for (User u : userDao.findAll()) {
            if (u.getEmail().equals(user.getEmail())) {
                model.addAttribute("emailError", "Email already exists");
                hasErrors = true;
            }
        }
        //verify email
        len = user.getEmail().length();
        if (len < 8 || len > 25) {
            model.addAttribute("emailError", "Email must be between 8 and 25 characters");
            hasErrors = true;
        }
        if (user.getEmail().indexOf('.') < 0) {
            model.addAttribute("emailError", "Please enter a valid email");
            hasErrors = true;
        }
        //verify passwords match
        if (!verify.equals(user.getPassword())) {
            model.addAttribute("passwordError", "Passwords do not match");
            hasErrors = true;
        }
        //verify password length
        len = user.getPassword().length();
        if (len < 6) {
            model.addAttribute("passwordError", "Password must be at least 6 characters");
            hasErrors = true;
        }

        if (hasErrors) {
            return "user/sign-up";
        }

        userDao.save(user);

        HttpSession session = request.getSession();
        session.setAttribute("name", user.getEmail());
        model.addAttribute("name", user.getEmail());
        return "index";
    }
}
