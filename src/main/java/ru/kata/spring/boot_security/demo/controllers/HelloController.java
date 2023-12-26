package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.security.PersonDetails;
import ru.kata.spring.boot_security.demo.services.PersonDetailsService;

@Controller
public class HelloController {

    private final PersonDetailsService personDetailsService;

    public HelloController(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam("id") int id, Model model) {
        model.addAttribute("person", personDetailsService.getPersonById(id));
        return "/hello/index";
    }

    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails.getPerson());
        return "/hello/index";
    }
    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("person",personDetailsService.getAllPeople());
        return "/hello/admin";
    }
}
