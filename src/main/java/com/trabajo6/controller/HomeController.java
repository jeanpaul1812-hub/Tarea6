package com.trabajo6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"/", "/inventario-diecast"})
    public String inicio() {
        return "redirect:/login";
    }
}
