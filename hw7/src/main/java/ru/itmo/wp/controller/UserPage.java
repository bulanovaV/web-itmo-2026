package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;


@Controller
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public String getUser(Model model, @PathVariable String id, HttpSession session) {
        Long correctId;
        try {
            correctId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            correctId = null;
        }
        User user = userService.findById(correctId);

        if (user != null) {
            model.addAttribute("curUser", user);
        }
        return "UserPage";
    }
}
