package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/users")
@Validated
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String printUsers(Model model) {

        model.addAttribute("users", userService.listUsers());
        model.addAttribute("user", new User());
        model.addAttribute("rolesList", roleRepository.findAll());

        return "users";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user) {
        userService.add(user);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/edit")
    public String edit(Model model, @RequestParam Long id) {

        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roleRepository.findAll());

        return "edit";
    }

    @PatchMapping(value = "/edit")
    public String update(@ModelAttribute("user") @Valid User user) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/delete")
    @DeleteMapping()
    public String delete(@RequestParam("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }

}
