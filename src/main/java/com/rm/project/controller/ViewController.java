package com.rm.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home() {
        return "index-rest";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/budget-details")
    public String budgets() {
        return "budget";
    }

    @GetMapping("/create-expense")
    public String expenses() {
        return "create-expense";
    }

    @GetMapping("/create-alert")
    public String alerts() {
        return "create-alert";
    }

    @GetMapping("/notifications")
    public String notifications() {
        return "notifications";
    }
}
