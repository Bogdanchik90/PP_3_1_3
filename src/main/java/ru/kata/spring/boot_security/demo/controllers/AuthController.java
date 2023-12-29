package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Person;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.services.RegistrationService;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.util.PersonValidator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final RoleService roleService;

    @Autowired
    public AuthController(PersonValidator personValidator,
                          RegistrationService registrationService, RoleService roleService) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "/auth/login";
    }
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person, Model model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("person", person);
        return "/auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person,
                                      @RequestParam("roleId") int roleId,
                                      BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "/auth/registration";

        Role role = roleService.getRoleById(roleId);

        registrationService.register(person, role);
        return "redirect:/auth/login";
    }
}

//@GetMapping("/setRole/{id}")
//public String showSetRoleForm(Model model, @PathVariable("id") Long id) {
//    Person person = personService.getPersonById(id);
//    List<Role> roles = roleService.getAllRoles();
//    model.addAttribute("person", person);
//    model.addAttribute("roles", roles);
//    return "setRoleForm";
//}
//
//@PostMapping("/setRole/{id}")
//public String setRole(@ModelAttribute("person") Person person, @PathVariable("id") Long id, @RequestParam("roleId") Long roleId) {
//    Role role = roleService.getRoleById(roleId);
//    personService.setRole(person, role);
//    return "redirect:/persons";
//}

