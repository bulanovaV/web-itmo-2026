package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @PostMapping("/users/status")
    public String changeStatus(
            @RequestParam long id,
            @RequestParam boolean disabled,
            HttpSession session
    ) {
        User currentUser = userService.findById((Long) session.getAttribute(USER_ID_SESSION_KEY));
        if (currentUser == null || !currentUser.isAdmin()) {
            return "redirect:/users/all";
        }
        userService.setDisabled(id, disabled);
        return "redirect:/users/all";
    }
}
