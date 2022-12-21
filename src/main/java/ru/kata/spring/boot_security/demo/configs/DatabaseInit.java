package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class DatabaseInit {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public DatabaseInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void initDbUsers() {

        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        if (roleService.getRoles().isEmpty()) {
            roleService.addRole(roleAdmin);
            roleService.addRole(roleUser);
        }

        if (userService.getUsersList().isEmpty()) {
            User admin = new User();
            Set<Role> adminRoles = new HashSet<>();
            Collections.addAll(adminRoles, roleService.roleByID(1L), roleService.roleByID(2L));
            admin.setId(1L);
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setName("Vladislav");
            admin.setLastName("Shilov");
            admin.setAge(26);
            admin.setMail("Shilovvlad@mail.ru");
            admin.setRoles(adminRoles);
            userService.addUser(admin);

            User user = new User();
            Set<Role> userRoles = new HashSet<>();
            Collections.addAll(userRoles, roleService.roleByID(2L));
            user.setId(2L);
            user.setUsername("user");
            user.setPassword("user");
            user.setName("Roman");
            user.setLastName("Petrov");
            user.setAge(25);
            user.setMail("PetrovRoman@gmail.ru");
            user.setRoles(userRoles);
            userService.addUser(user);
        }
    }
}