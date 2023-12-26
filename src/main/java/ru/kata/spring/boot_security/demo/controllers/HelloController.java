package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.Person;
import ru.kata.spring.boot_security.demo.security.PersonDetails;
import ru.kata.spring.boot_security.demo.services.PersonDetailsService;

import java.security.Principal;
import java.util.Optional;

@Controller
public class HelloController {

    private final PersonDetailsService personDetailsService;

    public HelloController(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @GetMapping("/hello")
    public String sayHello(Model model, Principal principal) {
        Person person = personDetailsService.getPersonByName(principal.getName()).orElse(null);

        model.addAttribute("person", person);
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
