package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.NoticeCredentials;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticePage extends Page {
    @PostMapping("/notice/add")
    public String addNotice(
            @Valid @ModelAttribute("noticeForm") NoticeCredentials noticeForm,
            BindingResult bindingResult,
            HttpSession httpSession,
            HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            return "IndexPage";
        }

        noticeService.saveNotice(noticeForm);
        setMessage(httpSession, "Notice successfully added!");
        return "redirect:/";
    }
}
