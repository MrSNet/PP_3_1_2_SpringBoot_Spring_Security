package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping
public class AdminController {

    private final UserService userService;
    private final RoleDao roleDao;

    public AdminController(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping("/adminPanel")
    public String printUsers(@AuthenticationPrincipal User user, Model model) {

        model.addAttribute("users", userService.listUsers());
        model.addAttribute("user", user);
        model.addAttribute("rolesList", roleDao.listRoles());

        return "adminPanel";
    }

    @GetMapping("/adminUser")
    public String showUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "adminUser";
    }

    @GetMapping("/adminPanel/addUser")
    public String forAddUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("rolesList", roleDao.listRoles());
        return "addUser";
    }

    @PostMapping("/adminPanel/addUser")
    public String create(@ModelAttribute("user") User user, @RequestParam(value = "rolesId") Long[] rolesId) {
        userService.add(user, rolesId);
        return "redirect:/adminPanel";
    }

    @PatchMapping(value = "adminPanel/edit")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "rolesId", required = false) Long[] rolesId) {

        userService.updateUser(user, rolesId);
        return "redirect:/adminPanel";
    }

    @DeleteMapping("adminPanel/delete")
    public String delete(@RequestParam("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/adminPanel";
    }

}
