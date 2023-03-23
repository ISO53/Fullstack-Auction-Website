package com.iso.bidding.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SpringSessionController {

    private final String S = "SESSION_MESSAGES";

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute(S);

        if (messages == null) {
            messages = new ArrayList<>();
        }

        model.addAttribute("sessionMessages", messages);
        model.addAttribute("sessionId", session.getId());

        return "index";
    }

    @PostMapping("/persistMessage")
    public String persistMessage(@RequestParam("msg") String msg, HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        List<String> messages = (List<String>) request.getSession().getAttribute(S);

        if (messages == null) {
            messages = new ArrayList<>();
            request.getSession().setAttribute(S, msg);
        }

        messages.add(msg);
        request.getSession().setAttribute(S, msg);
        return "redirect:/";
    }

    @PostMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }
}
