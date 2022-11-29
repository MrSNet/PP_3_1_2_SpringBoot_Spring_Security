package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/admin/users")
public class AdminController {

    private final UserService userService;
    private final RoleDao roleDao;

    public AdminController(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping
    public String printUsers(Model model) {

        model.addAttribute("users", userService.listUsers());
        model.addAttribute("user", new User());
        model.addAttribute("rolesList", roleDao.listRoles());

        return "users";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user, @RequestParam(value = "rolesId") Long[] rolesId) {
        userService.add(user, rolesId);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/edit")
    public String edit(Model model, @RequestParam Long id) {

        model.addAttribute("user", userService.findById(id));
        model.addAttribute("rolesList", roleDao.listRoles());

        return "edit";
    }

    @PatchMapping(value = "/edit")
    public String update(@ModelAttribute("user") User user, @RequestParam(value = "rolesId", required = false) Long[] rolesId) {
        userService.updateUser(user, rolesId);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/delete")
    @DeleteMapping()
    public String delete(@RequestParam("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }

}
